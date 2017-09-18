package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
 * Created by caspar on 14/09/17.
 */
public class VanillaThrosToStringConverterTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testVanillaThrowArrayToString() throws Exception
    {
        final VanillaThrosToStringConverter vanillaThrosToStringConverter = VanillaThrosToStringConverter.get();
        softly.assertThat(vanillaThrosToStringConverter.apply(new VanillaThro[]{get(1), get(2), get(3)}))
              .isEqualTo("123");

        softly.assertThat(vanillaThrosToStringConverter.apply(new VanillaThro[]{get(0), get(2), get(9)}))
              .isEqualTo("029");

        softly.assertThat(vanillaThrosToStringConverter.apply(new VanillaThro[]{get(12), get(2), get(3)}))
              .isEqualTo("C23");

        softly.assertThat(vanillaThrosToStringConverter.apply(new VanillaThro[]{get(1), get(30), get(3)}))
              .isEqualTo("1U3");
    }
}