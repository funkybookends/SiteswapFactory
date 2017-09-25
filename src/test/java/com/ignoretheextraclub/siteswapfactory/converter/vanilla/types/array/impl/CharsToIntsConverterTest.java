package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class CharsToIntsConverterTest
{
    @Test
    @Parameters
    public void testApply(final int[] ints, final char[] chars) throws Exception
    {
        assertThat(CharsToIntsConverter.get().apply(chars)).isEqualTo(ints);
        assertThat(CharsToIntsConverter.convert(chars)).isEqualTo(ints);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14}, new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E'}},
                new Object[]{new int[]{}, new char[]{}},
        };
        // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> CharsToIntsConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}