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
import com.ignoretheextraclub.siteswapfactory.predicates.result.LoopCheckingNoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Factory for creating generators that generate {@link TwoHandedSiteswap}s
 *
 * @author Caspar Nonclercq
 */
public class TwoHandedSiteswapGenerator
{
    /**
     * The base builder which injects the constructor
     * @return
     * @param maxThro
     */
    private static StateSearcherBuilder<TwoHandedSiteswap> builder(final int maxThro)
    {
        return StateSearcherBuilder.<TwoHandedSiteswap>builder()
            .withSiteswapConstructor(GeneralCircuitToTwoHandedSiteswapConstructor.get())
            .andIntermediatePredicate(new NoThroHigherThanPredicate(VanillaThro.get(maxThro)))
            .andResultPredicate(new LoopCheckingNoThroHigherThanPredicate(VanillaThro.get(maxThro)));
    }

    // region Builders

    public static StateSearcherBuilder<TwoHandedSiteswap> groundBuilder(final int numObjects, final int maxThro, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        validateMaxThroIsNotLessThanNumObjects(numObjects, maxThro);
        return builder(maxThro).addStartingState(VanillaStateGenerator.getGroundState(numObjects))
            .setMaxPeriod(maxPeriod);
    }

    public static StateSearcherBuilder<TwoHandedSiteswap> allBuilder(final int numObjects,
                                                                     final int maxThro,
                                                                     final int maxPeriod)
    {
        validateMaxThroIsNotLessThanNumObjects(numObjects, maxThro);

        return builder(maxThro)
            .setStartingStates(VanillaStateGenerator.getAllStates(numObjects, maxThro)
                .map((VanillaState state) -> (State) state)
                .collect(Collectors.toSet()))
            .setMaxPeriod(maxPeriod);
    }

    public static StateSearcherBuilder<TwoHandedSiteswap> excitedBuilder(final int numObjects, final int maxThro, final int maxPeriod) throws NumObjectsException, PeriodException
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

    // end region

    // region Generators

    public static SiteswapGenerator<TwoHandedSiteswap> all(final int numObjects, final int maxThro, final int maxPeriod)
    {
        return allBuilder(numObjects, maxThro, maxPeriod).create();
    }

    public static SiteswapGenerator<TwoHandedSiteswap> ground(final int numObjects,
                                                              final int maxThro,
                                                              final int maxPeriod) throws InvalidSiteswapException
    {
        return groundBuilder(numObjects, maxThro, maxPeriod).create();
    }

    public static SiteswapGenerator<TwoHandedSiteswap> excited(final int numObjects,
                                                               final int maxThro,
                                                               final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return excitedBuilder(numObjects, maxThro, maxPeriod).create();
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
