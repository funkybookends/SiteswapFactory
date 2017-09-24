package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.states;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class StartingStateAndThrosToAllStatesConverterTest
{
    @Test
    @Parameters
    public void testApply(final VanillaState startingState, final Thro[] thros, final State[] expected) throws Exception
    {
        assertThat(StartingStateAndThrosToAllStatesConverter.get().apply(startingState, thros)).isEqualTo(expected);
        final State[] allStates = StartingStateAndThrosToAllStatesConverter.getAllStates(startingState, thros);
        assertThat(allStates).isEqualTo(expected);
        assertThat(allStates).hasSize(thros.length);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{XXX_, thros(getUnchecked(4), getUnchecked(2), getUnchecked(3)), states(XXX_, XX_X, XXX_)},
                new Object[]{XXX__, thros(getUnchecked(4), getUnchecked(2), getUnchecked(3)), states(XXX__, XX_X_, XXX__)},
                new Object[]{XXXX_, thros(getUnchecked(5), getUnchecked(3), getUnchecked(4)), states(XXXX_, XXX_X, XXXX_)},
                new Object[]{XXXX_, thros(getUnchecked(5), getUnchecked(3), getUnchecked(5)), states(XXXX_, XXX_X, XXXX_)},
                new Object[]{XXX__, thros(getUnchecked(3)), states(XXX__)},
                new Object[]{XXX_, thros(getUnchecked(3)), states(XXX_)},
                new Object[]{XXX, thros(getUnchecked(3)), states(XXX)},
                };
        // TODO add more
    }

    @Test
    @Parameters
    public void testException(final VanillaState startingState,
                              final Thro[] thros,
                              final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> StartingStateAndThrosToAllStatesConverter.getAllStates(startingState, thros))
                .isInstanceOf(exception.getClass())
                .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
                //@formatter:off
            new Object[]{XXX__, thros(getUnchecked(5), getUnchecked(4), getUnchecked(3)), new InvalidSiteswapException("[5, 4, 3]")},
            new Object[]{XXX__, thros(getUnchecked(5), getUnchecked(4), getUnchecked(3)), new InvalidSiteswapException("XXX__")},
            new Object[]{XXX__, null, new NullPointerException("thros cannot be null")},
            new Object[]{null, new VanillaThro[]{}, new NullPointerException("startingState cannot be null")},
            new Object[]{XXX__, new VanillaThro[]{}, new IllegalArgumentException("thros must have at least one throw")}
            //@formatter:on
        };
        // TODO add more
    }

    @Test
    @Parameters
    public void testEnforcesLoop(final VanillaState startingState,
                                 final Thro[] thros,
                                 final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> StartingStateAndThrosToAllStatesConverter.getAllStates(startingState, thros))
                .isInstanceOf(exception.getClass())
                .hasMessageContaining(exception.getMessage())
                .hasCauseInstanceOf(TransitionException.class);
    }

    private Object parametersForTestEnforcesLoop()
    {
        return new Object[]{
                //@formatter:off
            new Object[]{XXX__, thros(getUnchecked(5), getUnchecked(4)), new InvalidSiteswapException("XXX__")},
            new Object[]{XXX__, thros(getUnchecked(5), getUnchecked(5)), new InvalidSiteswapException("XXX__")},
            //@formatter:on
        };
        // TODO add more
    }
}