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
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class StringToVanillaThrosConverterTest
{
    private static final StringToVanillaThrosConverter CONVERTER = StringToVanillaThrosConverter.get();

    @Test
    @Parameters
    public void testApply(final String siteswap, final VanillaThro[] expected) throws Exception
    {
        assertThat(CONVERTER.apply(siteswap)).isEqualTo(expected);
        assertThat(StringToVanillaThrosConverter.convert(siteswap)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{"456", new VanillaThro[]{get(4), get(5), get(6)}},
                new Object[]{"0123456789ABCDE", new VanillaThro[]{get('0'), get('1'), get('2'), get('3'), get('4'), get('5'), get('6'), get('7'), get('8'), get('9'), get('A'), get('B'), get('C'), get('D'), get('E')}},
                // TODO add more
        };
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> StringToVanillaThrosConverter.convert("Funky Bookends"))
                .isInstanceOf(BadThrowException.class)
                .hasMessageContaining("Funky Bookends");

        assertThatThrownBy(() -> StringToVanillaThrosConverter.convert(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("siteswap");

        assertThatThrownBy(() -> StringToVanillaThrosConverter.convert(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("siteswap");
    }
}