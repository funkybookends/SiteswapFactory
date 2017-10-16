package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class IntsToVanillaThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final int[] ints, final VanillaThro[] thros) throws Exception
    {
        assertThat(IntsToVanillaThrosConverter.get().apply(ints)).isEqualTo(thros);
        assertThat(IntsToVanillaThrosConverter.convert(ints)).isEqualTo(thros);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new int[]{0,2,4,5,6,7,8,9,10,11,12}, new VanillaThro[]{get(0), get(2), get(4), get(5), get(6), get(7), get(8), get(9), get(10), get(11), get(12)}},
                new Object[]{new int[]{}, new VanillaThro[]{}},
                };
        // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> IntsToVanillaThrosConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}