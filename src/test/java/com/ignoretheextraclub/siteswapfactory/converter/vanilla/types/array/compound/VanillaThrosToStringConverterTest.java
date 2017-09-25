package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class VanillaThrosToStringConverterTest
{
    @Test
    @Parameters
    public void testApply(final VanillaThro[] input, final String expected) throws Exception
    {
        assertThat(VanillaThrosToStringConverter.get().apply(input)).isEqualTo(expected);
        assertThat(VanillaThrosToStringConverter.toString(input)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new VanillaThro[]{get(1), get(2), get(3)}, "123"},
                new Object[]{new VanillaThro[]{get(0), get(2), get(9)}, "029"},
                new Object[]{new VanillaThro[]{get(12), get(2), get(3)}, "C23"},
                new Object[]{new VanillaThro[]{get(1), get(30), get(3)}, "1U3"},
                new Object[]{new VanillaThro[]{}, ""},
                // TODO add more
                };
    }

    @Test
    public void testExceptions() throws Exception
    {
        assertThatThrownBy(() -> VanillaThrosToStringConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}