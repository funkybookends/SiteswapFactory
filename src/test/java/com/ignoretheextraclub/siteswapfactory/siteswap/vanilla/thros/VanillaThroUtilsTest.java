package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 Created by caspar on 29/07/17.
 */
public class VanillaThroUtilsTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();



    @Test
    public void testNumObjects() throws Exception
    {
        final IntsToVanillaThrosConverter converter = IntsToVanillaThrosConverter.get();
        softly.assertThat(VanillaThroUtils.numObjects(converter.apply(StateTestUtils.thros(
                5,
                6,
                7)))).isEqualTo(6);

        softly.assertThat(VanillaThroUtils.numObjects(converter.apply(StateTestUtils.thros(
                1)))).isEqualTo(1);

        softly.assertThat(VanillaThroUtils.numObjects(converter.apply(StateTestUtils.thros(
                9,
                7,
                2)))).isEqualTo(6);

        softly.assertThat(VanillaThroUtils.numObjects(converter.apply(StateTestUtils.thros(
                8,
                9,
                10)))).isEqualTo(9);

        softly.assertThat(VanillaThroUtils.numObjects(converter.apply(StateTestUtils.thros(
                3,
                4,
                5)))).isEqualTo(4);

        softly.assertThat(VanillaThroUtils.numObjects(converter.apply(StateTestUtils.thros(
                5,
                6,
                7)))).isEqualTo(6);

        softly.assertThat(VanillaThroUtils.numObjects(converter.apply(StateTestUtils.thros(
                5,
                5,
                5,
                0,
                0)))).isEqualTo(3);
    }
}