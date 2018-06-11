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

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class ThrosToFourHandedSiteswapThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final Thro[] thros, final FourHandedSiteswapThro[] expected) throws Exception
    {
        assertThat(ThrosToFourHandedSiteswapThrosConverter.get().apply(thros)).isEqualTo(expected);
        assertThat(ThrosToFourHandedSiteswapThrosConverter.convert(thros)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new Thro[]{}, new FourHandedSiteswapThro[]{}},
                new Object[]{new Thro[]{get(0), get(2), get(4), get(6), get(9)}, new FourHandedSiteswapThro[]{get(0), get(2), get(4), get(6), get(9)}},
        }; // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> ThrosToFourHandedSiteswapThrosConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("thros");
    }
}