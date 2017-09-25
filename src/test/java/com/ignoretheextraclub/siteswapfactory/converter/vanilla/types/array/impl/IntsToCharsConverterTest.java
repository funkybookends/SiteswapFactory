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
public class IntsToCharsConverterTest
{
    @Test
    @Parameters
    public void testApply(final int[] ints, final char[] chars) throws Exception
    {
        assertThat(IntsToCharsConverter.get().apply(ints)).isEqualTo(chars);
        assertThat(IntsToCharsConverter.convert(ints)).isEqualTo(chars);
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
        assertThatThrownBy(() -> IntsToCharsConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}