package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by caspar on 25/09/17.
 */
public class CharToIntConverterTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testIntToChar() throws Exception
    {
        final CharToIntConverter intToCharConverter = CharToIntConverter.get();
        softly.assertThat(intToCharConverter.apply('0')).isEqualTo(0);
        softly.assertThat(intToCharConverter.apply('1')).isEqualTo(1);
        softly.assertThat(intToCharConverter.apply('2')).isEqualTo(2);
        softly.assertThat(intToCharConverter.apply('3')).isEqualTo(3);
        softly.assertThat(intToCharConverter.apply('4')).isEqualTo(4);
        softly.assertThat(intToCharConverter.apply('5')).isEqualTo(5);
        softly.assertThat(intToCharConverter.apply('6')).isEqualTo(6);
        softly.assertThat(intToCharConverter.apply('7')).isEqualTo(7);
        softly.assertThat(intToCharConverter.apply('8')).isEqualTo(8);
        softly.assertThat(intToCharConverter.apply('9')).isEqualTo(9);
        softly.assertThat(intToCharConverter.apply('A')).isEqualTo(10);
        softly.assertThat(intToCharConverter.apply('B')).isEqualTo(11);
        softly.assertThat(intToCharConverter.apply('C')).isEqualTo(12);
        softly.assertThat(intToCharConverter.apply('D')).isEqualTo(13);
        softly.assertThat(intToCharConverter.apply('E')).isEqualTo(14);
        softly.assertThat(intToCharConverter.apply('F')).isEqualTo(15);
        softly.assertThat(intToCharConverter.apply('G')).isEqualTo(16);
        softly.assertThat(intToCharConverter.apply('H')).isEqualTo(17);
        softly.assertThat(intToCharConverter.apply('?')).isEqualTo(-1);
        softly.assertThat(intToCharConverter.apply('@')).isEqualTo(-1);
    }
}