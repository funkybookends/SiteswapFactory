package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class StringToVanillaThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final String siteswap, final VanillaThro[] expected) throws Exception
    {
        assertThat(StringToFourHandedSiteswapThrosConverter.get().apply(siteswap)).isEqualTo(expected);
        assertThat(StringToFourHandedSiteswapThrosConverter.convert(siteswap)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{"456", new VanillaThro[]{get(4), get(5), get(6)}},
                new Object[]{"", new VanillaThro[]{}},
                // TODO add more
        };
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> StringToFourHandedSiteswapThrosConverter.convert("345"))
                .isInstanceOf(BadThrowException.class)
                .hasMessageContaining("345");

        assertThatThrownBy(() -> StringToFourHandedSiteswapThrosConverter.convert("Funky Bookends"))
                .isInstanceOf(BadThrowException.class)
                .hasMessageContaining("Funky Bookends");
    }
}