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
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StatesToFourHandedSiteswapConstructor;
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
     * <li>{@link StatesToFourHandedSiteswapConstructor}</li>
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
            .withSiteswapConstructor(StatesToFourHandedSiteswapConstructor.get())
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
            .andIntermediatePredicate(banGroundState)
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