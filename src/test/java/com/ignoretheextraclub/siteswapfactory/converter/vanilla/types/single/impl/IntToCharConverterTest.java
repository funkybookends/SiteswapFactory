package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Fail.fail;

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
    }

    @Test
    public void testExceptions() throws Exception
    {
        fail("Test not yet implemented");
    }
}