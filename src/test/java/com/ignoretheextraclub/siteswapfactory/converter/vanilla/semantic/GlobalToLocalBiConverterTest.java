package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by caspar on 14/09/17.
 */
public class GlobalToLocalBiConverterTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void globalToLocalObject() throws Exception
    {
        final GlobalToLocalBiConverter<Integer> globalToLocalBiConverter = new GlobalToLocalBiConverter<Integer>();
        softly.assertThat(globalToLocalBiConverter.apply(StateTestUtils.integerArray(9, 7, 5), 0))
              .isEqualTo(StateTestUtils.integerArray(9, 5, 7));

        softly.assertThat(globalToLocalBiConverter.apply(StateTestUtils.integerArray(9, 7, 5), 1))
              .isEqualTo(StateTestUtils.integerArray(7, 9, 5));

        softly.assertThat(globalToLocalBiConverter.apply(StateTestUtils.integerArray(7, 8, 9, 10, 6), 0))
              .isEqualTo(StateTestUtils.integerArray(7, 9, 6, 8, 10));

        softly.assertThat(globalToLocalBiConverter.apply(StateTestUtils.integerArray(7, 8, 9, 10, 6), 1))
              .isEqualTo(StateTestUtils.integerArray(8, 10, 7, 9, 6));
    }



    @Test
    public void globalToLocal() throws Exception
    {
        final GlobalToLocalBiConverter.IntConverter intConverter = GlobalToLocalBiConverter.IntConverter.get();

        softly.assertThat(intConverter.apply(StateTestUtils.thros(9, 7, 5), 0))
              .isEqualTo(StateTestUtils.thros(9, 5, 7));

        softly.assertThat(intConverter.apply(StateTestUtils.thros(9, 7, 5), 1))
              .isEqualTo(StateTestUtils.thros(7, 9, 5));

        softly.assertThat(intConverter.apply(StateTestUtils.thros(7, 8, 9, 10, 6), 0))
              .isEqualTo(StateTestUtils.thros(7, 9, 6, 8, 10));

        softly.assertThat(intConverter.apply(StateTestUtils.thros(7, 8, 9, 10, 6), 1))
              .isEqualTo(StateTestUtils.thros(8, 10, 7, 9, 6));

        softly.assertThat(intConverter.apply(StateTestUtils.thros(7, 8, 6, 8, 6, 8, 6), 0))
              .isEqualTo(StateTestUtils.thros(7, 6, 6, 6, 8, 8, 8));

        softly.assertThat(intConverter.apply(StateTestUtils.thros(7, 8, 6, 8, 6, 8, 6), 1))
              .isEqualTo(StateTestUtils.thros(8, 8, 8, 7, 6, 6, 6));
    }

}