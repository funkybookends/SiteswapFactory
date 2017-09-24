package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.compound;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class VanillaThrosToVanillaStatesConverterTest
{
    @Test
    @Parameters
    public void testApply(final VanillaThro[] input, final VanillaState[] expected) throws Exception
    {
        assertThat(VanillaThrosToVanillaStatesConverter.get().apply(input)).isEqualTo(expected);
        assertThat(VanillaThrosToVanillaStatesConverter.convertThrosToStates(input)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{thros(getUnchecked(4), getUnchecked(2), getUnchecked(3)), StateTestUtils.states(XXX__, XX_X_, XXX__)},
                new Object[]{thros(getUnchecked(5), getUnchecked(3), getUnchecked(4)), StateTestUtils.states(XXXX__, XXX_X_, XXXX__)},
                };
        // TODO add more
    }

    @Test
    @Parameters
    public void testException(final VanillaThro[] input, final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> VanillaThrosToVanillaStatesConverter.convertThrosToStates(input))
                .isInstanceOf(exception.getClass())
                .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
                new Object[]{thros(getUnchecked(5), getUnchecked(4), getUnchecked(3)), new InvalidSiteswapException("[5, 4, 3]")},
                new Object[]{null, new NullPointerException("thros")},
                new Object[]{new VanillaThro[]{}, new IllegalArgumentException("thros cannot be empty")},
        };
        // TODO add more
    }
}