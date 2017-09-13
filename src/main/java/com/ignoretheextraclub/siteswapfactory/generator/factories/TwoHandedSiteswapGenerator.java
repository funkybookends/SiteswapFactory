package com.ignoretheextraclub.siteswapfactory.generator.factories;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.generator.SiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.generator.StateSearcherBuilder;
import com.ignoretheextraclub.siteswapfactory.predicates.result.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Factory for creating generators that generate {@link TwoHandedVanillaSiteswap}s
 *
 * @author Caspar Nonclercq
 */
public class TwoHandedSiteswapGenerator
{
    private static StateSearcherBuilder<TwoHandedVanillaSiteswap> builder()
    {
        return StateSearcherBuilder.<TwoHandedVanillaSiteswap>builder()
                .setSiteswapConstructor(SiteswapFactory::createTHS);
    }

    public static StateSearcherBuilder<TwoHandedVanillaSiteswap> groundBuilder(final int numObjects,
                                                                               final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return builder().addStartingState(VanillaStateUtils.getGroundState(numObjects, maxPeriod))
                        .setMaxPeriod(maxPeriod);
    }

    public static SiteswapGenerator<TwoHandedVanillaSiteswap> ground(final int numObjects,
                                                                     final int maxPeriod) throws InvalidSiteswapException
    {
        return groundBuilder(numObjects, maxPeriod).create();
    }

    public static StateSearcherBuilder<TwoHandedVanillaSiteswap> allBuilder(final int numObjects, final int maxPeriod)
    {
        return builder().setStartingStates(VanillaStateUtils.getAllStates(numObjects, maxPeriod)
                                                            .map((VanillaState state) -> (State) state)
                                                            .collect(Collectors.toSet()))
                        .setMaxPeriod(maxPeriod);
    }

    public static SiteswapGenerator<TwoHandedVanillaSiteswap> all(final int numObjects, final int maxPeriod)
    {
        return allBuilder(numObjects, maxPeriod).create();
    }

    public static StateSearcherBuilder<TwoHandedVanillaSiteswap> excitedBuilder(final int numObjects,
                                                                                final int maxPeriod) throws NumObjectsException, PeriodException
    {
        final State groundState = VanillaStateUtils.getGroundState(numObjects, maxPeriod);

        final Predicate<State[]> banGroundState = new StatePredicate(groundState).negate();

        final Set<State> excitedStates = VanillaStateUtils.getAllStates(numObjects, maxPeriod)
                                                          .map(vanillaState -> (State) vanillaState)
                                                          .filter((state) -> !state.equals(banGroundState))
                                                          .collect(Collectors.toSet());

        return builder().setStartingStates(excitedStates)
                        .addIntermediatePredicate(banGroundState)
                        .setMaxPeriod(maxPeriod);
    }

    public static SiteswapGenerator<TwoHandedVanillaSiteswap> excited(final int numObjects,
                                                                      final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return excitedBuilder(numObjects, maxPeriod).create();
    }
}
