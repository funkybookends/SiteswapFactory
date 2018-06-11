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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by caspar on 14/09/17.
 */
public class IntToCharConverterTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testIntToChar() throws Exception
    {
        final IntToCharConverter intToCharConverter = IntToCharConverter.get();
        softly.assertThat(intToCharConverter.apply(0)).isEqualTo('0');
        softly.assertThat(intToCharConverter.apply(1)).isEqualTo('1');
        softly.assertThat(intToCharConverter.apply(2)).isEqualTo('2');
        softly.assertThat(intToCharConverter.apply(3)).isEqualTo('3');
        softly.assertThat(intToCharConverter.apply(4)).isEqualTo('4');
        softly.assertThat(intToCharConverter.apply(5)).isEqualTo('5');
        softly.assertThat(intToCharConverter.apply(6)).isEqualTo('6');
        softly.assertThat(intToCharConverter.apply(7)).isEqualTo('7');
        softly.assertThat(intToCharConverter.apply(8)).isEqualTo('8');
        softly.assertThat(intToCharConverter.apply(9)).isEqualTo('9');
        softly.assertThat(intToCharConverter.apply(10)).isEqualTo('A');
        softly.assertThat(intToCharConverter.apply(11)).isEqualTo('B');
        softly.assertThat(intToCharConverter.apply(12)).isEqualTo('C');
        softly.assertThat(intToCharConverter.apply(13)).isEqualTo('D');
        softly.assertThat(intToCharConverter.apply(14)).isEqualTo('E');
        softly.assertThat(intToCharConverter.apply(15)).isEqualTo('F');
        softly.assertThat(intToCharConverter.apply(16)).isEqualTo('G');
        softly.assertThat(intToCharConverter.apply(17)).isEqualTo('H');
        softly.assertThat(intToCharConverter.apply(null)).isEqualTo('?');
        softly.assertThat(intToCharConverter.apply(46)).isEqualTo('?');
    }
}