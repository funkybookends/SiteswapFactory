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
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class VanillaThrosAndNumObjectsToFirstStateBiConverterTest
{
    @Test
    @Parameters
    public void testApply(final VanillaThro[] input,
                          final Integer numObjects,
                          final VanillaState expected) throws Exception
    {
        assertThat(VanillaThrosAndNumObjectsToFirstStateBiConverter.get().apply(input, numObjects)).isEqualTo(expected);
        assertThat(VanillaThrosAndNumObjectsToFirstStateBiConverter.getFirstState(input, numObjects)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{thros(get(3)), 3, XXX},
            new Object[]{thros(get(4), get(2)), 3, XXX_},
            new Object[]{thros(get(8), get(9), get(10), get(6), get(7)), 8, XXXXXXXX__},
            new Object[]{thros(get(9), get(10), get(6), get(7), get(8)), 8, XXXXXXXX__},
            new Object[]{thros(get(5), get(5), get(0), get(5), get(0)), 3, XX_X_},
        };
        // TODO add more
    }

    @Test
    @Parameters
    public void testException(final VanillaThro[] input,
                              final Integer numObjects,
                              final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> VanillaThrosAndNumObjectsToFirstStateBiConverter.get().apply(input, numObjects))
            .isInstanceOf(exception.getClass())
            .hasMessageContaining(exception.getMessage());

        assertThatThrownBy(() -> VanillaThrosAndNumObjectsToFirstStateBiConverter.getFirstState(input, numObjects))
            .isInstanceOf(exception.getClass())
            .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
            new Object[]{thros(get(3), get(4), get(2)), 4, new InvalidSiteswapException("[3, 4, 2]")},
            new Object[]{thros(get(3), get(4), get(2)), 4, new InvalidSiteswapException("4 objects")},
            new Object[]{thros(get(3), get(4), get(3)), 3, new InvalidSiteswapException("[3, 4, 3]")},
            new Object[]{thros(get(3), get(4), get(3)), 3, new InvalidSiteswapException("[3] objects")},
            new Object[]{thros(get(3), get(4), get(3)), -1, new IllegalArgumentException("numObjects can't be less than one")},
            new Object[]{null, 3, new IllegalArgumentException("thros must have at least one throw")},
            new Object[]{new VanillaThro[]{}, 3, new IllegalArgumentException("thros must have at least one throw")},
        };
    }
}