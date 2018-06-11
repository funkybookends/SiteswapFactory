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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToVanillaThrosConverter;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.numObjects;

/**
 * Created by caspar on 29/07/17.
 */
public class VanillaThroUtilsTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testNumObjects() throws Exception
    {
        final IntsToVanillaThrosConverter converter = IntsToVanillaThrosConverter.get();
        softly.assertThat(numObjects(converter.apply(thros(5,6,7)))).isEqualTo(6);
        softly.assertThat(numObjects(converter.apply(thros(1)))).isEqualTo(1);
        softly.assertThat(numObjects(converter.apply(thros(9,7,2)))).isEqualTo(6);
        softly.assertThat(numObjects(converter.apply(thros(8,9,10)))).isEqualTo(9);
        softly.assertThat(numObjects(converter.apply(thros(3,4,5)))).isEqualTo(4);
        softly.assertThat(numObjects(converter.apply(thros(5,6,7)))).isEqualTo(6);
        softly.assertThat(numObjects(converter.apply(thros(5,5,5,0,0)))).isEqualTo(3);
    }
}