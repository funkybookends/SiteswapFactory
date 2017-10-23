package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class VanillaThrosToStartingStateConverterTest
{
    @Test
    @Parameters
    public void testApply(final VanillaThro[] input,
                          final VanillaState expected) throws Exception
    {
        assertThat(VanillaThrosToStartingStateConverter.get().apply(input)).isEqualTo(expected);
        assertThat(VanillaThrosToStartingStateConverter.getFirstState(input)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{thros(get(3)), XXX},
            new Object[]{thros(get(4), get(2)), XXX_},
            new Object[]{thros(get(8), get(9), get(10), get(6), get(7)), XXXXXXXX__},
            new Object[]{thros(get(9), get(10), get(6), get(7), get(8)), XXXXXXXX__},
            new Object[]{thros(get(5), get(5), get(0), get(5), get(0)), XX_X_},
        };
        // TODO add more
    }

    @Test
    @Parameters
    public void testException(final VanillaThro[] input,
                              final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> VanillaThrosToStartingStateConverter.get().apply(input))
            .isInstanceOf(exception.getClass())
            .hasMessageContaining(exception.getMessage());

        assertThatThrownBy(() -> VanillaThrosToStartingStateConverter.getFirstState(input))
            .isInstanceOf(exception.getClass())
            .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
            new Object[]{thros(get(3), get(4), get(3)), new InvalidSiteswapException("[3, 4, 3]")},
            new Object[]{thros(get(3), get(4), get(3)), new InvalidSiteswapException("average")},
            new Object[]{null, new NullPointerException("thros cannot be null")},
            new Object[]{new VanillaThro[]{}, new IllegalArgumentException("thros")},
        };
    }
}