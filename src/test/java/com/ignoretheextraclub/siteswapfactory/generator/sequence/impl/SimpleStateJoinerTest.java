package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.VanillaThrosToStringConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.StateJoiner;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_X_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_XX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X____X;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 22/10/17.
 */
public class SimpleStateJoinerTest
{
    private StateJoiner stateJoiner;
    private RouteSearcher routeSearcher;

    @Before
    public void setUp() throws Exception
    {
        routeSearcher = mock(RouteSearcher.class);
        stateJoiner = new SimpleStateJoiner(routeSearcher);
    }

    @Test
    public void WHEN_FirstOrSecondNullOrEmpty_EXPECT_IAE() throws Exception
    {
        assertThatThrownBy(() -> stateJoiner.join(null, new State[]{XXX__})).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> stateJoiner.join(new State[0], new State[]{XXX__})).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> stateJoiner.join(new State[]{XXX__}, null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> stateJoiner.join(new State[]{XXX__}, new State[0])).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void WHEN_noJoinNeeded_EXPECT_arraysAreConcatenated() throws Exception
    {
        when(routeSearcher.findRoute(XXX__, XXX__)).thenReturn(new State[]{XXX__});
        when(routeSearcher.findRoute(XXX__, XXX__)).thenReturn(new State[]{XXX__});

        final State[] result = stateJoiner.join(new State[]{XXX__}, new State[]{XXX__});
        final State[] expected = new State[]{XXX__, XXX__};

        assertThat(result).isEqualTo(expected);

        verify(routeSearcher, times(2)).findRoute(XXX__, XXX__);
    }

    @Test
    public void WHEN_noIntermediateStatesNeeded_EXPECT_arraysAreConcatenated() throws Exception
    {
        //                                3                                    3
        when(routeSearcher.findRoute(XXX__, XXX__)).thenReturn(new State[]{XXX__});
        //                                2                                     3
        when(routeSearcher.findRoute(XXX__, XXX__)).thenReturn(new State[]{XXX__});

        //                                                     3                   42
        final State[] result = stateJoiner.join(new State[]{XXX__}, new State[]{XXX__, XX_X_});
        final State[] expected = new State[]{XXX__, XXX__, XX_X_};
        //                                        3     4       2

        assertThat(result).isEqualTo(expected);

        verify(routeSearcher, times(2)).findRoute(XXX__, XXX__);
    }

    @Test
    public void WHEN_intermediateStatesAreNeeded_EXPECT_theyAreInResult() throws Exception
    {
        final State endFirst = mock(State.class, "endFirst");
        final State endSecond = mock(State.class, "endSecond");
        final State startFirst = mock(State.class, "startFirst");
        final State startSecond = mock(State.class, "startSecond");

        final State[] first = new State[]{startFirst, XXX__, XXX__, endFirst};
        final State[] second = new State[]{startSecond, XXX__, XXX__, endSecond};

        final State[] joinFirstSecond = new State[]{startFirst, XXX__X, XX_X_X_X_, startSecond};
        final State[] joinSecondFirst = new State[]{startSecond, X_XX_, X____X, startFirst};

        when(routeSearcher.findRoute(startFirst, startSecond)).thenReturn(joinFirstSecond);
        when(routeSearcher.findRoute(startSecond, startFirst)).thenReturn(joinSecondFirst);

        final State[] result = stateJoiner.join(first, second);
        final State[] expected = new State[]{startFirst,
            XXX__
            , XXX__,
            endFirst,
            startFirst,
            XXX__X,
            XX_X_X_X_,
            startSecond,
            XXX__,
            XXX__,
            endSecond,
            startSecond,
            X_XX_,
            X____X};

        assertThat(result).isEqualTo(expected);

        verify(routeSearcher).findRoute(startFirst, startSecond);
        verify(routeSearcher).findRoute(startSecond, startFirst);
    }

    @Test
    public void testJoinSiteswap() throws Exception
    {
        this.routeSearcher = new IddfsRouteSearcher();
        this.stateJoiner = new SimpleStateJoiner(routeSearcher);

        final TwoHandedSiteswap fiveThreeFour = SiteswapFactory.getTwoHandedSiteswap("534");
        final TwoHandedSiteswap flash = SiteswapFactory.getTwoHandedSiteswap("55550");

        final State[] result = stateJoiner.join(fiveThreeFour, flash);

        final String resultThrosString = StatesToThrosConverter.get().andThen(ThrosToVanillaThrosConverter.get()).andThen(VanillaThrosToStringConverter.get()).apply(result);
        final String expectedResultString = "53455550";

        assertThat(resultThrosString).isEqualTo(expectedResultString);
    }

    @Test
    public void testJoinSiteswapsWithDifferentMaxThros() throws Exception
    {
        this.routeSearcher = new IddfsRouteSearcher();
        this.stateJoiner = new SimpleStateJoiner(routeSearcher);

        final TwoHandedSiteswap first = SiteswapFactory.getTwoHandedSiteswap("77470"); // 7 77470 4
        final TwoHandedSiteswap second = SiteswapFactory.getTwoHandedSiteswap("91834"); // 666 91834 2

        final State[] result = stateJoiner.join(first, second);

        final String resultThrosString = StatesToThrosConverter.get().andThen(ThrosToVanillaThrosConverter.get()).andThen(VanillaThrosToStringConverter.get()).apply(result);

        assertThat(resultThrosString).contains(first.toString());
        assertThat(resultThrosString).contains(second.toString());

        final String expectedResultString = "77470669183426";
        assertThat(resultThrosString).isEqualTo(expectedResultString);
    }

    @Test
    public void testJoinSiteswapsNoTransitionNeeded() throws Exception
    {
        this.routeSearcher = new IddfsRouteSearcher();
        this.stateJoiner = new SimpleStateJoiner(routeSearcher);

        final TwoHandedSiteswap first = SiteswapFactory.getTwoHandedSiteswap("786");
        final TwoHandedSiteswap second = SiteswapFactory.getTwoHandedSiteswap("966");

        final State[] result = stateJoiner.join(first, second);

        final String resultThrosString = StatesToThrosConverter.get().andThen(ThrosToVanillaThrosConverter.get()).andThen(VanillaThrosToStringConverter.get()).apply(result);

        assertThat(resultThrosString).contains(first.toString());
        assertThat(resultThrosString).contains(second.toString());

        final String expectedResultString = "786966";
        assertThat(resultThrosString).isEqualTo(expectedResultString);
    }

    @Test
    public void testJoinSiteswapsNoTransitionNeeded1() throws Exception
    {
        this.routeSearcher = new IddfsRouteSearcher();
        this.stateJoiner = new SimpleStateJoiner(routeSearcher);

        final TwoHandedSiteswap first = SiteswapFactory.getTwoHandedSiteswap("3");
        final TwoHandedSiteswap second = SiteswapFactory.getTwoHandedSiteswap("42");

        final State[] result = stateJoiner.join(first, second);

        final String resultThrosString = StatesToThrosConverter.get().andThen(ThrosToVanillaThrosConverter.get()).andThen(VanillaThrosToStringConverter.get()).apply(result);

        assertThat(resultThrosString).contains(first.toString());
        assertThat(resultThrosString).contains(second.toString());

        final String expectedResultString = "342";
        assertThat(resultThrosString).isEqualTo(expectedResultString);
    }
}