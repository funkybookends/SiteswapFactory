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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

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
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class StringToFourHandedSiteswapThrosConverterTest
{
    @Test
    @Parameters
    public void testApply(final String siteswap, final FourHandedSiteswapThro[] expected) throws Exception
    {
        assertThat(StringToFourHandedSiteswapThrosConverter.get().apply(siteswap)).isEqualTo(expected);
        assertThat(StringToFourHandedSiteswapThrosConverter.convert(siteswap)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{"456", new FourHandedSiteswapThro[]{get(4), get(5), get(6)}},
            new Object[]{"975", new FourHandedSiteswapThro[]{get(9), get(7), get(5)}},
            new Object[]{"02456789ABC", new FourHandedSiteswapThro[]{get('0'), get('2'), get('4'), get('5'), get('6'), get('7'), get('8'), get('9'), get('A'), get('B'), get('C')}},
                // TODO add more
            };
    }

        @Test
        public void testException () throws Exception
        {
            assertThatThrownBy(() -> StringToFourHandedSiteswapThrosConverter.convert("345"))
                .isInstanceOf(BadThrowException.class)
                .hasMessageContaining("345");

            assertThatThrownBy(() -> StringToFourHandedSiteswapThrosConverter.convert("Funky Bookends"))
                .isInstanceOf(BadThrowException.class)
                .hasMessageContaining("Funky Bookends");

            assertThatThrownBy(() -> StringToFourHandedSiteswapThrosConverter.convert(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empty");

            assertThatThrownBy(() -> StringToFourHandedSiteswapThrosConverter.convert(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("siteswap");
        }
    }