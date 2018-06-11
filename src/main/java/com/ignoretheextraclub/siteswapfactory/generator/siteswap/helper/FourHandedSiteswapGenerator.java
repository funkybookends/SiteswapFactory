/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.generator.siteswap.helper;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.SiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.StateSearcherBuilder;
import com.ignoretheextraclub.siteswapfactory.generator.state.VanillaStateGenerator;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.NoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.LoopCheckingNoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.LoopCheckingThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Factory for creating generators that generate {@link FourHandedSiteswap}s
 *
 * @author Caspar Nonclercq
 * @see #groundBuilder(int, int, int)
 * @see #allBuilder(int, int, int)
 * @see #excited(int, int, int)
 * @see #ground(int, int, int)
 * @see #all(int, int, int)
 * @see #excited(int, int, int)
 */
public final class FourHandedSiteswapGenerator
{
    private FourHandedSiteswapGenerator()
    {
        // All methods are static
    }

    /**
     * The base builder
     * <p>
     * Injects:
     * <ul>
     * <li>An intermediate predicate banning all non FHS throws</li>
     * <li>A result predicate banning all non FHS throws</li>
     * <li>A result predicate requiring a pass</li>
     * </ul>
     *
     * @return A builder with the minmum requirements to be an FHS builder.
     * @param maxThro
     */
    private static StateSearcherBuilder<FourHandedSiteswap> builder(final int maxThro)
    {
        return StateSearcherBuilder.<FourHandedSiteswap>builder()
            .withSiteswapConstructor(GeneralCircuitToFourHandedSiteswapConstructor.get())
            .andIntermediatePredicate(ThroCombinationPredicate.banAllSingleThros(FourHandedSiteswapThro.getIllegalThrows()))
            .andResultPredicate(LoopCheckingThroCombinationPredicate.requireAnyOneOf(FourHandedSiteswapThro.getPassThrows()))
            .andResultPredicate(LoopCheckingThroCombinationPredicate.banAllSingleThros(FourHandedSiteswapThro.getIllegalThrows()))
            .andIntermediatePredicate(new NoThroHigherThanPredicate(VanillaThro.get(maxThro)))
            .andResultPredicate(new LoopCheckingNoThroHigherThanPredicate(VanillaThro.get(maxThro)));
    }


    // region Builders

    /**
     * Ground Builder.
     * <p>
     * Injects:
     * <ul>
     *     <li>The ground state as a starting state</li>
     * </ul>
     * @param numObjects
     * @param maxThro
     * @param maxPeriod
     * @return
     * @throws NumObjectsException
     * @throws PeriodException
     */
    public static StateSearcherBuilder<FourHandedSiteswap> groundBuilder(final int numObjects, final int maxThro, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        validateMaxThroIsNotLessThanNumObjects(numObjects, maxThro);
        return builder(maxThro)
            .addStartingState(VanillaStateGenerator.getGroundState(numObjects))
            .setMaxPeriod(maxPeriod);
    }

    public static StateSearcherBuilder<FourHandedSiteswap> excitedBuilder(final int numObjects, final int maxThro, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        validateMaxThroIsNotLessThanNumObjects(numObjects, maxThro);
        final State groundState = VanillaStateGenerator.getGroundState(numObjects);

        final Predicate<State[]> banGroundState = new StatePredicate(groundState).negate();

        final Set<State> excitedStates = VanillaStateGenerator.getAllStates(numObjects, maxThro)
            .map(vanillaState -> (State) vanillaState)
            .filter((state) -> !state.equals(banGroundState))
            .collect(Collectors.toSet());

        return builder(maxThro)
            .setStartingStates(excitedStates)
            .andIntermediatePredicate(generalPath -> banGroundState.test(generalPath.getStates()))
            .andResultPredicate(generalCircuit -> banGroundState.test(generalCircuit.getAllStates()))
            .setMaxPeriod(maxPeriod);
    }

    public static StateSearcherBuilder<FourHandedSiteswap> allBuilder(final int numObjects, final int maxThro, final int maxPeriod)
    {
        validateMaxThroIsNotLessThanNumObjects(numObjects, maxThro);
        return builder(maxThro)
            .setStartingStates(VanillaStateGenerator.getAllStates(numObjects, maxThro).collect(Collectors.toSet()))
            .setMaxPeriod(maxPeriod);
    }


    // endregion

    // region Generators

    public static SiteswapGenerator<FourHandedSiteswap> ground(final int numObjects, final int maxThro, final int maxPeriod) throws InvalidSiteswapException
    {
        return groundBuilder(numObjects, maxThro, maxPeriod).create();
    }


    public static SiteswapGenerator<FourHandedSiteswap> excited(final int numObjects, final int maxThro, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return excitedBuilder(numObjects, maxThro, maxPeriod).create();
    }

    public static SiteswapGenerator<FourHandedSiteswap> all(final int numObjects, final int maxThro, final int maxPeriod)
    {
        return allBuilder(numObjects, maxThro, maxPeriod).create();
    }

    // endregion

    private static void validateMaxThroIsNotLessThanNumObjects(final int numObjects, final int maxThro)
    {
        if (maxThro < numObjects)
        {
            throw new IllegalArgumentException("maxThro cannot be less than numObjects");
        }
    }
}
