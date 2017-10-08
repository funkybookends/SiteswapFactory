package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoStartingStrategy;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.states;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
Created by caspar on 12/08/17.
*/
public class TwoHandedVanillaSiteswapTest
{
   @Rule
   public JUnitSoftAssertions softly = new JUnitSoftAssertions();

   @Test
   public void test534() throws Exception
   {
       final TwoHandedSiteswap vanillaSiteswap = SiteswapFactory.getTwoHandedSiteswap("534");
       final TwoHandedSiteswap same = SiteswapFactory.getTwoHandedSiteswap(new SiteswapRequest("453", true, null, null, NoStartingStrategy.get()));
       final TwoHandedSiteswap different = SiteswapFactory.getTwoHandedSiteswap("441");
       softly.assertThat(vanillaSiteswap.getNumJugglers()).isEqualTo(1);
       softly.assertThat(vanillaSiteswap.getNumHands()).isEqualTo(2);
       softly.assertThat(vanillaSiteswap.getNumObjects()).isEqualTo(4);
       softly.assertThat(vanillaSiteswap.getPeriod()).isEqualTo(3);
       softly.assertThat(vanillaSiteswap.getThrows()).isEqualTo(thros(get(5), get(3), get(4)));
       softly.assertThat(vanillaSiteswap.getThrowsForJuggler(0)).isEqualTo(thros(get(5), get(3), get(4)));
       softly.assertThatThrownBy(() -> vanillaSiteswap.getThrowsForJuggler(1)).isInstanceOf(IndexOutOfBoundsException.class);
       softly.assertThat(vanillaSiteswap.getStates()).isEqualTo(states(XXXX_, XXX_X_, XXXX_));
       softly.assertThat(vanillaSiteswap.isGrounded()).isEqualTo(true);
       softly.assertThat(vanillaSiteswap.isPrime()).isEqualTo(false);
       softly.assertThat(vanillaSiteswap.getHighestThro()).isEqualTo(get(5));
       softly.assertThat(vanillaSiteswap.getStartingNumberOfObjects(0)).isEqualTo(2);
       softly.assertThat(vanillaSiteswap.getStartingNumberOfObjects(1)).isEqualTo(2);
       softly.assertThatThrownBy(() -> vanillaSiteswap.getStartingNumberOfObjects(3)).isInstanceOf(IndexOutOfBoundsException.class);
       softly.assertThat(vanillaSiteswap.getType()).isEqualTo("Two Handed Siteswap");
       softly.assertThat(vanillaSiteswap.toString()).isEqualTo("534");
       softly.assertThat(vanillaSiteswap.equals(vanillaSiteswap)).isEqualTo(true);
       softly.assertThat(vanillaSiteswap.same(vanillaSiteswap)).isEqualTo(true);
       softly.assertThat(vanillaSiteswap.equals(same)).isEqualTo(false);
       softly.assertThat(vanillaSiteswap.same(same)).isEqualTo(true);
       softly.assertThat(vanillaSiteswap.equals(different)).isEqualTo(false);
       softly.assertThat(vanillaSiteswap.same(different)).isEqualTo(false);
   }
}