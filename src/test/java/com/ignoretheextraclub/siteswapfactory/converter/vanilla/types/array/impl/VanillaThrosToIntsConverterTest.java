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
public class VanillaThrosToIntsConverterTest
{
    @Test
    @Parameters
    public void testApply(final VanillaThro[] thros, final int[] expected) throws Exception
    {
        assertThat(VanillaThrosToIntsConverter.get().apply(thros)).isEqualTo(expected);
        assertThat(VanillaThrosToIntsConverter.convert(thros)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{new VanillaThro[]{}, new int[]{}},
            new Object[]{new VanillaThro[]{get(0), get(2), get(4), get(6), get(9)}, new int[]{0, 2, 4, 6, 9}},
        }; // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> VanillaThrosToIntsConverter.get().apply(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("thros");
    }
}