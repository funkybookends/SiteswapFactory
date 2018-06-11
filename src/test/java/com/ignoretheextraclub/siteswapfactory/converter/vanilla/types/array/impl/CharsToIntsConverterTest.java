/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

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