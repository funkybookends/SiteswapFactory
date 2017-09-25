package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class CharsToFourHandedSiteswapThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final char[] chars, final FourHandedSiteswapThro[] expected) throws Exception
    {
        assertThat(CharsToFourHandedSiteswapThrosConverter.get().apply(chars)).isEqualTo(expected);
        assertThat(CharsToFourHandedSiteswapThrosConverter.convert(chars)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new char[]{'0','A'}, new FourHandedSiteswapThro[]{get(0), get('A')}},
                new Object[]{new char[]{}, new FourHandedSiteswapThro[]{}},
        };
        // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> CharsToFourHandedSiteswapThrosConverter.convert(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}