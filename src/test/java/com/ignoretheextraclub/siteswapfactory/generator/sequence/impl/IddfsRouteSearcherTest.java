package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

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
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class IddfsRouteSearcherTest
{
    private final RouteSearcher routeSearcher = new IddfsRouteSearcher(5);

    @Test
    @Parameters
    public void testRoute(final State source, final State goal, final GeneralPath expected) throws Exception
    {
        final GeneralPath route = routeSearcher.findRoute(source, goal);
        Assertions.assertThat(route).isEqualTo(expected);
    }

    public Object parametersForTestRoute() throws Exception
    {
        return new Object[]{
            new Object[]{XXX__, XX_X_, GeneralPath.from(XXX__, get(4))},
            new Object[]{XXX__, __XXX, GeneralPath.from(XXX__, get(5), get(5), get(5))},
            new Object[]{__XXX, XXX__, GeneralPath.from(__XXX, get(0), get(0))},
            new Object[]{XX_X_X_X_, XXXXX____, GeneralPath.from(XX_X_X_X_, get(2), get(3), get(4))},
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