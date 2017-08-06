package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils.stringToVanillaThrowArray;

/**
 Created by caspar on 06/08/17.
 */
public class VanillaSiteswapUtilsTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testGetStartingNumberOfObjects() throws Exception
    {
        softly.assertThat(VanillaSiteswapUtils.getStartingNumberOfObjects(2, 0, stringToVanillaThrowArray("3"), 3)).isEqualTo(2);
        softly.assertThat(VanillaSiteswapUtils.getStartingNumberOfObjects(2, 1, stringToVanillaThrowArray("3"), 3)).isEqualTo(1);
        softly.assertThat(VanillaSiteswapUtils.getStartingNumberOfObjects(4, 0, stringToVanillaThrowArray("975"), 7)).isEqualTo(2);
        softly.assertThat(VanillaSiteswapUtils.getStartingNumberOfObjects(4, 1, stringToVanillaThrowArray("975"), 7)).isEqualTo(2);
        softly.assertThat(VanillaSiteswapUtils.getStartingNumberOfObjects(4, 2, stringToVanillaThrowArray("975"), 7)).isEqualTo(2);
        softly.assertThat(VanillaSiteswapUtils.getStartingNumberOfObjects(4, 3, stringToVanillaThrowArray("975"), 7)).isEqualTo(1);
    }
}