package com.ignoretheextraclub.siteswapfactory.generator.siteswap.premade;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.SiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.StateSearcherBuilder;
import com.ignoretheextraclub.siteswapfactory.generator.state.VanillaStateGenerator;
import com.ignoretheextraclub.siteswapfactory.predicates.result.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StatesToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

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
     */
    private static StateSearcherBuilder<TwoHandedSiteswap> builder()
    {
        return StateSearcherBuilder.<TwoHandedSiteswap>builder()
            .withSiteswapConstructor(StatesToTwoHandedSiteswapConstructor.get());
    }

    // region Builders

    public static StateSearcherBuilder<TwoHandedSiteswap> groundBuilder(final int numObjects,
                                                                        final int maxThro,
                                                                        final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return builder().addStartingState(VanillaStateGenerator.getGroundState(numObjects, maxThro))
            .setMaxPeriod(maxPeriod);
    }

    public static StateSearcherBuilder<TwoHandedSiteswap> allBuilder(final int numObjects,
                                                                     final int maxThro,
                                                                     final int maxPeriod)
    {
        return builder()
            .setStartingStates(VanillaStateGenerator.getAllStates(numObjects, maxThro)
                .map((VanillaState state) -> (State) state)
                .collect(Collectors.toSet()))
            .setMaxPeriod(maxPeriod);
    }

    public static StateSearcherBuilder<TwoHandedSiteswap> excitedBuilder(final int numObjects,
                                                                         final int maxThro,
                                                                         final int maxPeriod) throws NumObjectsException, PeriodException
    {
        final State groundState = VanillaStateGenerator.getGroundState(numObjects, maxThro);

        final Predicate<State[]> banGroundState = new StatePredicate(groundState).negate();

        final Set<State> excitedStates = VanillaStateGenerator.getAllStates(numObjects, maxThro)
            .map(vanillaState -> (State) vanillaState)
            .filter((state) -> !state.equals(banGroundState))
            .collect(Collectors.toSet());

        return builder()
            .setStartingStates(excitedStates)
            .andIntermediatePredicate(banGroundState)
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
}
