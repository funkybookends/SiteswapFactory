package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumJugglersException;
import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.states;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
Created by caspar on 12/08/17.
*/
public class VanillaSiteswapTest
{
   @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

   @Test
   public void test534() throws Exception
   {
       //todo construct vanilla siteswap
       final VanillaSiteswap vanillaSiteswap = new VanillaSiteswap(TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("534").getGeneralCircuit());
       final VanillaSiteswap same = new VanillaSiteswap(TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("453").getGeneralCircuit());
       final VanillaSiteswap different = new VanillaSiteswap(TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("441").getGeneralCircuit());

       softly.assertThatThrownBy(vanillaSiteswap::getNumJugglers).isInstanceOf(NumJugglersException.class);
       softly.assertThatThrownBy(vanillaSiteswap::getNumHands).isInstanceOf(NumJugglersException.class);
       softly.assertThat(vanillaSiteswap.getNumObjects()).isEqualTo(4);
       softly.assertThat(vanillaSiteswap.getPeriod()).isEqualTo(3);
       softly.assertThat(vanillaSiteswap.getThrows()).isEqualTo(thros(get(5), get(3), get(4)));
       softly.assertThat(vanillaSiteswap.getStates()).isEqualTo(states(XXXX_, XXX_X, XXXX_));
       softly.assertThat(vanillaSiteswap.isGrounded()).isEqualTo(true);
       softly.assertThat(vanillaSiteswap.isPrime()).isEqualTo(false);
       softly.assertThat(vanillaSiteswap.getHighestThro()).isEqualTo(get(5));
       softly.assertThatThrownBy(() -> vanillaSiteswap.getStartingNumberOfObjects(0)).isInstanceOf(NumJugglersException.class);
       softly.assertThat(vanillaSiteswap.getType()).isEqualTo("Vanilla Siteswap");
       softly.assertThat(vanillaSiteswap.toString()).isEqualTo("534");

       softly.assertThat(vanillaSiteswap.equals(vanillaSiteswap)).isEqualTo(true);
       softly.assertThat(vanillaSiteswap.same(vanillaSiteswap)).isEqualTo(true);

       softly.assertThat(vanillaSiteswap.equals(same)).isEqualTo(false);
       softly.assertThat(vanillaSiteswap.same(same)).isEqualTo(true);

       softly.assertThat(vanillaSiteswap.equals(different)).isEqualTo(false);
       softly.assertThat(vanillaSiteswap.same(different)).isEqualTo(false);

//        softly.assertThat(vanillaSiteswap.getAnagrams()).isEqualTo();
//        softly.assertThat(vanillaSiteswap.getRelated()).isEqualTo();
//        softly.assertThat(vanillaSiteswap.append(Siteswap other)).isEqualTo(); softly.assertThat(vanillaSiteswap.resort(
//        softly.assertThat(vanillaSiteswap.resort()).isEqualTo();
   }
}