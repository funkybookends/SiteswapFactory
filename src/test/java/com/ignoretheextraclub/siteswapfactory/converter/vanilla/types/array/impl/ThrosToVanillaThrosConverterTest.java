package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
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
public class ThrosToVanillaThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final Thro[] thros, final VanillaThro[] expected) throws Exception
    {
        assertThat(ThrosToVanillaThrosConverter.get().apply(thros)).isEqualTo(expected);
        assertThat(ThrosToVanillaThrosConverter.convert(thros)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new Thro[]{}, new VanillaThro[]{}},
                new Object[]{new Thro[]{get(0), get(2), get(4), get(6), get(9)}, new VanillaThro[]{get(0), get(2), get(4), get(6), get(9)}},
                }; // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> ThrosToVanillaThrosConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}