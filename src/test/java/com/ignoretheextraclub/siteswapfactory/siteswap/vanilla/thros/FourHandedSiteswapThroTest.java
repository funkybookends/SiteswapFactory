package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.IntToCharConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;

/**
 Created by caspar on 29/07/17.
 */
public class FourHandedSiteswapThroTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void when_getting_expect_sameObjectEachTime_AND_testToStringMethod() throws Exception
    {
        final IntToCharConverter intToCharConverter = IntToCharConverter.get();

        for (int asInt = 0; asInt < FourHandedSiteswapThro.MAX_THROW; asInt++)
        {
            if (asInt != 1 && asInt != 3)
            {
                final char asChar = intToCharConverter.apply(asInt);

                softly.assertThat(FourHandedSiteswapThro.get(asInt))
                      .as(asInt + " as int - int")
                      .isSameAs(FourHandedSiteswapThro.get(asInt));

                softly.assertThat(FourHandedSiteswapThro.get(asChar))
                      .as(asInt + " as char - char")
                      .isSameAs(FourHandedSiteswapThro.get(asChar));

                softly.assertThat(FourHandedSiteswapThro.get(asInt))
                      .as(asInt + " as int - char")
                      .isSameAs(FourHandedSiteswapThro.get(asChar));

                softly.assertThat(FourHandedSiteswapThro.get(asInt).toString())
                      .isEqualTo(String.valueOf(intToCharConverter.apply(asInt)));
            }
        }
    }

    @Test
    public void testIllegalThrows() throws Exception
    {
        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get(-1))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("-1");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get(-20))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("-20");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get(500))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("500");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get(1))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("1");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get(3))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("3");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get('1'))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("1");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get('3'))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("3");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get(FourHandedSiteswapThro.MAX_THROW + 1))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("13");

        softly.assertThatThrownBy(() -> FourHandedSiteswapThro.get('D'))
              .isInstanceOf(BadThrowException.class)
              .hasMessageContaining("13");
    }

    @Test
    public void testGetNumBeats() throws Exception
    {
        for (int asInt = 0; asInt < FourHandedSiteswapThro.MAX_THROW; asInt++)
        {
            if (asInt != 1 && asInt != 3)
            {
                softly.assertThat(FourHandedSiteswapThro.get(asInt).getNumBeats()).isEqualTo(asInt);
            }
        }
    }

    @Test
    public void testCompareToAndEquals() throws Exception
    {
        for (int lowerInt = 0; lowerInt < FourHandedSiteswapThro.MAX_THROW; lowerInt++)
        {
            if (lowerInt != 1 && lowerInt != 3)
            {
                final VanillaThro lower = FourHandedSiteswapThro.get(lowerInt);
                for (int higherInt = lowerInt + 1; higherInt < FourHandedSiteswapThro.MAX_THROW; higherInt++)
                {
                    if (higherInt != 1 && higherInt != 3)
                    {
                        final VanillaThro higher = FourHandedSiteswapThro.get(higherInt);
                        softly.assertThat(lower.compareTo(higher)).isLessThan(0);
                        softly.assertThat(higher.compareTo(lower)).isGreaterThan(0);
                    }
                }
                softly.assertThat(lower.compareTo(lower)).isEqualTo(0);
                softly.assertThat(lower.hashCode()).isEqualTo(lower.hashCode());
                softly.assertThat(lower).isEqualTo(lower);
            }
        }
    }

    @Test
    public void testFHST_equal_VT() throws Exception
    {
        softly.assertThat(VanillaThro.get(0)).isEqualTo(FourHandedSiteswapThro.get(0));
        softly.assertThat(VanillaThro.get(2)).isEqualTo(FourHandedSiteswapThro.get(2));
        softly.assertThat(VanillaThro.get(4)).isEqualTo(FourHandedSiteswapThro.get(4));
        softly.assertThat(VanillaThro.get(5)).isEqualTo(FourHandedSiteswapThro.get(5));
        softly.assertThat(VanillaThro.get(6)).isEqualTo(FourHandedSiteswapThro.get(6));
        softly.assertThat(VanillaThro.get(7)).isEqualTo(FourHandedSiteswapThro.get(7));
        softly.assertThat(VanillaThro.get(8)).isEqualTo(FourHandedSiteswapThro.get(8));
        softly.assertThat(VanillaThro.get(9)).isEqualTo(FourHandedSiteswapThro.get(9));
        softly.assertThat(VanillaThro.get(10)).isEqualTo(FourHandedSiteswapThro.get(10));
        softly.assertThat(VanillaThro.get(11)).isEqualTo(FourHandedSiteswapThro.get(11));
        softly.assertThat(VanillaThro.get(12)).isEqualTo(FourHandedSiteswapThro.get(12));
    }
}