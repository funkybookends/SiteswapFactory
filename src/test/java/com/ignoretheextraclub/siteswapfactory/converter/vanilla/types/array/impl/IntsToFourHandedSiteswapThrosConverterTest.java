package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class IntsToFourHandedSiteswapThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final int[] ints, final FourHandedSiteswapThro[] thros) throws Exception
    {
        assertThat(IntsToFourHandedSiteswapThrosConverter.get().apply(ints)).isEqualTo(thros);
        assertThat(IntsToFourHandedSiteswapThrosConverter.convert(ints)).isEqualTo(thros);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new int[]{0,2,4,5,6,7,8,9,10,11,12},
                             new FourHandedSiteswapThro[]{get(0), get(2), get(4), get(5), get(6), get(7), get(8), get(9), get(10), get(11), get(12)}},
                new Object[]{new int[]{}, new FourHandedSiteswapThro[]{}},
        };
        // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> IntsToFourHandedSiteswapThrosConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");

        assertThatThrownBy(() -> IntsToFourHandedSiteswapThrosConverter.get().apply(new int[]{3}))
                .isInstanceOf(BadThrowException.class)
                .hasMessageContaining("3");

        assertThatThrownBy(() -> IntsToFourHandedSiteswapThrosConverter.get().apply(new int[]{1}))
                .isInstanceOf(BadThrowException.class)
                .hasMessageContaining("1");
    }
}