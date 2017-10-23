package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXX____;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_X___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_X__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_X_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X__XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class IddfsRouteSearcherTest
{
    private final RouteSearcher routeSearcher = new IddfsRouteSearcher(5);

    @Test
    @Parameters
    public void testRoute(final State source, final State goal, final State[] expected) throws Exception
    {
        final State[] route = routeSearcher.findRoute(source, goal);
        Assertions.assertThat(route).isEqualTo(expected);
    }

    public Object parametersForTestRoute() throws Exception
    {
        return new Object[]{
            new Object[]{XXX__, XXX__, new State[]{XXX__}},
            new Object[]{XXX__, XX_X_, new State[]{XXX__, XX_X_}},
            new Object[]{XXX__, __XXX, new State[]{XXX__, XX__X, X__XX, __XXX}},
            new Object[]{__XXX, XXX__, new State[]{__XXX, _XXX_, XXX__,}},
            new Object[]{__XXX, XXX__, new State[]{__XXX, _XXX_, XXX__,}},
            new Object[]{XX_X_X_X_, XXXXX____, new State[]{XX_X_X_X_, XXX_X_X__, XXXX_X___, XXXXX____}},
        };
    }

    @Test
    public void whenNoRouteThrowsTransitionException() throws Exception
    {
        assertThatThrownBy(() -> routeSearcher.findRoute(XXX__, XXXX_))
            .isInstanceOf(TransitionException.class);
    }

    @Test
    public void whenSourceOrGoalNullThrowsNPE() throws Exception
    {
        assertThatThrownBy(() -> routeSearcher.findRoute(null, XXXX_))
            .isInstanceOf(NullPointerException.class);


        assertThatThrownBy(() -> routeSearcher.findRoute(XXX__, null))
            .isInstanceOf(NullPointerException.class);
    }
}