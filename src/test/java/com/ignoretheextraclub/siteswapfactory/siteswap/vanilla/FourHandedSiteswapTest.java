package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
 Created by caspar on 06/08/17.
 */
public class FourHandedSiteswapTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void test534() throws Exception
    {
        final FourHandedSiteswap vanillaSiteswap = SiteswapFactory.createFHS("786");
        final FourHandedSiteswap same = SiteswapFactory.createFHS("867", NoSortingStrategy.get(), true);
        final FourHandedSiteswap different = SiteswapFactory.createFHS("975");
        softly.assertThat(vanillaSiteswap.getNumJugglers()).isEqualTo(2);
        softly.assertThat(vanillaSiteswap.getNumHands()).isEqualTo(4);
        softly.assertThat(vanillaSiteswap.getNumObjects()).isEqualTo(7);
        softly.assertThat(vanillaSiteswap.getPeriod()).isEqualTo(3);
        softly.assertThat(vanillaSiteswap.getThrows()).isEqualTo(thros(get(7), get(8), get(6)));
        softly.assertThat(vanillaSiteswap.getThrowsForJuggler(0)).isEqualTo(thros(get(7), get(6), get(8)));
        softly.assertThat(vanillaSiteswap.getThrowsForJuggler(1)).isEqualTo(thros(get(8), get(7), get(6)));
        softly.assertThatThrownBy(() -> vanillaSiteswap.getThrowsForJuggler(2)).isInstanceOf(IndexOutOfBoundsException.class);
        softly.assertThat(vanillaSiteswap.isGrounded()).isEqualTo(true);
        softly.assertThat(vanillaSiteswap.isPrime()).isEqualTo(false);
        softly.assertThat(vanillaSiteswap.getHighestThro()).isEqualTo(get(8));
        softly.assertThat(vanillaSiteswap.getSortingStrategy()).isEqualTo(FourHandedPassingStrategy.get());
        softly.assertThat(vanillaSiteswap.getStartingNumberOfObjects(0)).isEqualTo(2);
        softly.assertThat(vanillaSiteswap.getStartingNumberOfObjects(1)).isEqualTo(2);
        softly.assertThat(vanillaSiteswap.getStartingNumberOfObjects(2)).isEqualTo(2);
        softly.assertThat(vanillaSiteswap.getStartingNumberOfObjects(3)).isEqualTo(1);
        softly.assertThatThrownBy(() -> vanillaSiteswap.getStartingNumberOfObjects(4)).isInstanceOf(IndexOutOfBoundsException.class);
        softly.assertThat(vanillaSiteswap.getType()).isEqualTo("Four Handed Siteswap");
        softly.assertThat(vanillaSiteswap.toString()).isEqualTo("786");
        softly.assertThat(vanillaSiteswap.equals(vanillaSiteswap)).isEqualTo(true);
        softly.assertThat(vanillaSiteswap.same(vanillaSiteswap)).isEqualTo(true);
        softly.assertThat(vanillaSiteswap.equals(same)).isEqualTo(false);
        softly.assertThat(vanillaSiteswap.same(same)).isEqualTo(true);
        softly.assertThat(vanillaSiteswap.equals(different)).isEqualTo(false);
        softly.assertThat(vanillaSiteswap.same(different)).isEqualTo(false);
        //        softly.assertThat(vanillaSiteswap.getAnagrams()).isEqualTo();
        //        softly.assertThat(vanillaSiteswap.getRelated()).isEqualTo();
        //        softly.assertThat(vanillaSiteswap.append(Siteswap other)).isEqualTo(); softly.assertThat(vanillaSiteswap.resort(
        //            softly.assertThat(vanillaSiteswap.resort()).isEqualTo();
    }
}