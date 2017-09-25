package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.IntToCharConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 Created by caspar on 29/07/17.
 */
public class VanillaThroTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void when_getting_expect_sameObjectEachTime_AND_testToStringMethod() throws Exception
    {
        for (int asInt = 0; asInt < VanillaThro.MAX_THROW; asInt++)
        {
            final IntToCharConverter intToCharConverter = IntToCharConverter.get();
            final char asChar = intToCharConverter.apply(asInt);
            softly.assertThat(VanillaThro.get(asInt)).as(asInt + " as int - int").isSameAs(VanillaThro.get(asInt));
            softly.assertThat(VanillaThro.get(asChar)).as(asInt + " as char - char").isSameAs(VanillaThro.get(asChar));
            softly.assertThat(VanillaThro.get(asInt)).as(asInt + " as int - char").isSameAs(VanillaThro.get(asChar));
            softly.assertThat(VanillaThro.get(asInt).toString()).isEqualTo(String.valueOf(intToCharConverter.apply(asInt)));
        }
    }

    @Test
    public void testIllegalThrows() throws Exception
    {
        softly.assertThatThrownBy(() -> VanillaThro.get(-1)).isInstanceOf(BadThrowException.class).hasMessageContaining("-1");
        softly.assertThatThrownBy(() -> VanillaThro.get(-20)).isInstanceOf(BadThrowException.class).hasMessageContaining("-20");
        softly.assertThatThrownBy(() -> VanillaThro.get(500)).isInstanceOf(BadThrowException.class).hasMessageContaining("500");
        softly.assertThatThrownBy(() -> VanillaThro.get(VanillaThro.MAX_THROW+1)).isInstanceOf(BadThrowException.class).hasMessageContaining("36");

        softly.assertThatThrownBy(() -> VanillaThro.get(-1)).isInstanceOf(BadThrowException.class).hasMessageContaining("-1");
        softly.assertThatThrownBy(() -> VanillaThro.get(-20)).isInstanceOf(BadThrowException.class).hasMessageContaining("-20");
        softly.assertThatThrownBy(() -> VanillaThro.get(500)).isInstanceOf(BadThrowException.class).hasMessageContaining("500");
        softly.assertThatThrownBy(() -> VanillaThro.get(VanillaThro.MAX_THROW+1)).isInstanceOf(BadThrowException.class).hasMessageContaining("36");
    }

    @Test
    public void testGetNumBeats() throws Exception
    {
        for (int asInt = 0; asInt < VanillaThro.MAX_THROW; asInt++)
        {
            softly.assertThat(VanillaThro.get(asInt).getNumBeats()).isEqualTo(asInt);
        }
    }

    @Test
    public void testCompareToAndEquals() throws Exception
    {
        for (int lowerInt = 0; lowerInt < VanillaThro.MAX_THROW; lowerInt++)
        {
            final VanillaThro lower = VanillaThro.get(lowerInt);
            for (int higherInt = lowerInt + 1; higherInt < VanillaThro.MAX_THROW; higherInt++)
            {
                final VanillaThro higher = VanillaThro.get(higherInt);
                softly.assertThat(lower.compareTo(higher)).isLessThan(0);
                softly.assertThat(higher.compareTo(lower)).isGreaterThan(0);
            }
            softly.assertThat(lower.compareTo(lower)).isEqualTo(0);
            softly.assertThat(lower.hashCode()).isEqualTo(lower.hashCode());
            softly.assertThat(lower).isEqualTo(lower);
        }
    }
}