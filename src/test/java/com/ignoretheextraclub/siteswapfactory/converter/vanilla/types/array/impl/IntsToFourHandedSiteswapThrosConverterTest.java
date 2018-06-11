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

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
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
                new Object[]{new int[]{0,2,4,5,6,7,8,9,10,11,12}, new FourHandedSiteswapThro[]{get(0), get(2), get(4), get(5), get(6), get(7), get(8), get(9), get(10), get(11), get(12)}},
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