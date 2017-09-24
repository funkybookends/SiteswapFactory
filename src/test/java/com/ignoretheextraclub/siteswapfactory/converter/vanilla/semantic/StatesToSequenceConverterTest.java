package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
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
public class StatesToSequenceConverterTest
{
    @Test
    @Parameters
    public void testApply(final State[] input, final Thro[] expected) throws Exception
    {
        assertThat(StatesToSequenceConverter.get().apply(input)).isEqualTo(expected);
        assertThat(StatesToSequenceConverter.getSequence(input)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{states(XXX__, XX_X_), thros(getUnchecked(4))},
                new Object[]{states(XXX__, XX_X_, XXX__), thros(getUnchecked(4), getUnchecked(2))},
                new Object[]{states(XXX__, XX_X_, XXX__, XXX__), thros(getUnchecked(4), getUnchecked(2), getUnchecked(3))},
                new Object[]{states(XXXX__, XXXX__), thros(getUnchecked(4))},
                new Object[]{states(XXXX__, XXX_X_), thros(getUnchecked(5))},
                };
        // TODO add more
    }

    @Test
    @Parameters
    public void testException(final State[] startingState,
                              final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> StatesToSequenceConverter.getSequence(startingState))
                .isInstanceOf(exception.getClass())
                .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
                new Object[]{states(XXX__), new IllegalArgumentException("Need at least 2 states")},
                new Object[]{states(XXX__, XXXX_), new InvalidSiteswapException("XXX__, XXXX_")},
                new Object[]{null, new NullPointerException("states")},
                new Object[]{new State[]{}, new IllegalArgumentException("states")},
        };
        // TODO add more
    }
}