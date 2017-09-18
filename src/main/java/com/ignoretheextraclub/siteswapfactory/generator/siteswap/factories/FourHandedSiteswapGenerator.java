package com.ignoretheextraclub.siteswapfactory.generator.siteswap.factories;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.SiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.StateSearcherBuilder;
import com.ignoretheextraclub.siteswapfactory.generator.state.VanillaStateGenerator;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.LoopCheckingThrowCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Factory for creating generators that generate {@link FourHandedSiteswap}s
 * @author Caspar Nonclercq
 */
public class FourHandedSiteswapGenerator
{
    private static StateSearcherBuilder<FourHandedSiteswap> builder()
    {
        return StateSearcherBuilder.<FourHandedSiteswap>builder()
                .withSiteswapConstructor(SiteswapFactory::createFHS)
                .addIntermediatePredicate(ThroCombinationPredicate.banAllSingleThros(FourHandedSiteswapThro.ILLEGAL_THROS()))
                .andResultPredicate(LoopCheckingThrowCombinationPredicate.requireAnyOneOf(FourHandedSiteswapThro.PASS_THROS()));
    }

    public static StateSearcherBuilder<FourHandedSiteswap> groundBuilder(final int numObjects,
            final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return builder().addStartingState(VanillaStateGenerator.getGroundState(numObjects, maxPeriod))
                .setMaxPeriod(maxPeriod);
    }

    public static SiteswapGenerator<FourHandedSiteswap> ground(final int numObjects, final int maxPeriod) throws InvalidSiteswapException
    {
        return groundBuilder(numObjects, maxPeriod).create();
    }

    public static StateSearcherBuilder<FourHandedSiteswap> allBuilder(final int numObjects, final int maxPeriod)
    {
        return builder().setStartingStates(VanillaStateGenerator.getAllStates(numObjects, maxPeriod)
                .map((VanillaState state) -> (State) state)
                .collect(Collectors.toSet())).setMaxPeriod(maxPeriod);
    }

    public static SiteswapGenerator<FourHandedSiteswap> all(final int numObjects, final int maxPeriod)
    {
        return allBuilder(numObjects, maxPeriod).create();
    }

    public static StateSearcherBuilder<FourHandedSiteswap> excitedBuilder(final int numObjects, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        final State groundState = VanillaStateGenerator.getGroundState(numObjects, maxPeriod);

        final Predicate<State[]> banGroundState = new StatePredicate(groundState).negate();

        final Set<State> excitedStates = VanillaStateGenerator.getAllStates(numObjects, maxPeriod)
                .map(vanillaState -> (State) vanillaState)
                .filter((state) -> !state.equals(banGroundState))
                .collect(Collectors.toSet());

        return builder()
                .setStartingStates(excitedStates)
                .addIntermediatePredicate(banGroundState)
                .setMaxPeriod(maxPeriod);
    }

    public static SiteswapGenerator<FourHandedSiteswap> excited(final int numObjects, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return excitedBuilder(numObjects, maxPeriod).create();
    }
}
