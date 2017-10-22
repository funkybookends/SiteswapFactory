package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.VanillaThrosToStringConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.StateJoiner;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X;
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
        when(routeSearcher.findRoute(XXX__, XX_X)).thenReturn(new State[]{XXX__, XX_X_});
        when(routeSearcher.findRoute(XXX__, XXX__)).thenReturn(new State[]{XXX__});

        final State[] result = stateJoiner.join(new State[]{XXX__}, new State[]{XX_X_, XXX__});
        final State[] expected = new State[]{XXX__, XX_X_, XXX__};

        assertThat(result).isEqualTo(expected);

        verify(routeSearcher).findRoute(XXX__, XX_X_);
        verify(routeSearcher).findRoute(XXX__, XXX__);
    }

    @Test
    public void WHEN_intermediateStatesAreNeeded_EXPECT_theyAreInResult() throws Exception
    {
        final State endFirst = mock(State.class);
        final State endSecond = mock(State.class);
        final State startFirst = mock(State.class);
        final State startSecond = mock(State.class);

        final State[] first = new State[]{startFirst, XXX__, XXX__, endFirst};
        final State[] second = new State[]{startSecond, XXX__, XXX__, endSecond};

        final State[] joinFirstSecond = new State[]{endFirst, XXX__X, XX_X_X_X_, startSecond};
        final State[] joinSecondFirst = new State[]{endSecond, X_XX_, X____X, startFirst};

        when(routeSearcher.findRoute(endFirst, startSecond)).thenReturn(joinFirstSecond);
        when(routeSearcher.findRoute(endSecond, startFirst)).thenReturn(joinSecondFirst);

        final State[] result = stateJoiner.join(first, second);
        final State[] expected = new State[]{startFirst,
            XXX__
            , XXX__,
            endFirst,
            XXX__X,
            XX_X_X_X_,
            startSecond,
            XXX__,
            XXX__,
            endSecond,
            X_XX_,
            X____X};

        assertThat(result).isEqualTo(expected);

        verify(routeSearcher).findRoute(endFirst, startSecond);
        verify(routeSearcher).findRoute(endSecond, startFirst);
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
}