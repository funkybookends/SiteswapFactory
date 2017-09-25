package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.*;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.states;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class StatesToThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final State[] input, final Thro[] expected) throws Exception
    {
        assertThat(StatesToThrosConverter.get().apply(input)).isEqualTo(expected);
        assertThat(StatesToThrosConverter.getThros(input)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{states(XXX__, XX_X_), thros(get(4), get(2))},
                new Object[]{states(XXX__, XX_X_, XXX__), thros(get(4), get(2), get(3))},
                new Object[]{states(XXX__, XX_X_, XXX__, XXX__), thros(get(4), get(2), get(3), get(3))},
                new Object[]{states(XXXX__, XXXX__), thros(get(4), get(4))},
                new Object[]{states(XXXX__, XXX_X_), thros(get(5), get(3))},
                };
        // TODO add more
    }

    @Test
    @Parameters
    public void testException(final State[] startingState,
                              final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> StatesToThrosConverter.getThros(startingState))
                .isInstanceOf(exception.getClass())
                .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
                new Object[]{new State[]{}, new IllegalArgumentException("No states")},
                new Object[]{states(XXX__, XXXX_), new InvalidSiteswapException("XXX__, XXXX_")},
                new Object[]{null, new NullPointerException("states cannot be null")},
                };
        // TODO add more
    }
}