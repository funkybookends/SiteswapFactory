package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class IntsToStringConverterTest
{
    @Test
    @Parameters
    public void testApply(final int[] ints, final String thros) throws Exception
    {
        assertThat(IntsToStringConverter.get().apply(ints)).isEqualTo(thros);
        assertThat(IntsToStringConverter.convert(ints)).isEqualTo(thros);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new int[]{0,2,4,5,6,7,8,9,10,11,12}, "02456789ABC"},
                new Object[]{new int[]{}, ""},
                };
        // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> IntsToStringConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}