package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.states;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;

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
        final TwoHandedVanillaSiteswap vanillaSiteswap = SiteswapFactory.createTHS("534");
        final TwoHandedVanillaSiteswap same = SiteswapFactory.createTHS("453", NoSortingStrategy.get(), true);
        final TwoHandedVanillaSiteswap different = SiteswapFactory.createTHS("441");
        softly.assertThat(vanillaSiteswap.getNumJugglers()).isEqualTo(1);
        softly.assertThat(vanillaSiteswap.getNumHands()).isEqualTo(2);
        softly.assertThat(vanillaSiteswap.getNumObjects()).isEqualTo(4);
        softly.assertThat(vanillaSiteswap.getPeriod()).isEqualTo(3);
        softly.assertThat(vanillaSiteswap.getThrows()).isEqualTo(thros(getUnchecked(5), getUnchecked(3), getUnchecked(4)));
        softly.assertThat(vanillaSiteswap.getThrowsForJuggler(0)).isEqualTo(thros(getUnchecked(5), getUnchecked(3), getUnchecked(4)));
        softly.assertThatThrownBy(() -> vanillaSiteswap.getThrowsForJuggler(1)).isInstanceOf(IndexOutOfBoundsException.class);
        softly.assertThat(vanillaSiteswap.getStates()).isEqualTo(states(XXXX_, XXX_X_, XXXX_));
        softly.assertThat(vanillaSiteswap.isGrounded()).isEqualTo(true);
        softly.assertThat(vanillaSiteswap.isPrime()).isEqualTo(false);
        softly.assertThat(vanillaSiteswap.getHighestThro()).isEqualTo(getUnchecked(5));
        softly.assertThat(vanillaSiteswap.getSortingStrategy()).isEqualTo(HighestThrowFirstStrategy.get());
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
//        softly.assertThat(vanillaSiteswap.getAnagrams()).isEqualTo();
//        softly.assertThat(vanillaSiteswap.getRelated()).isEqualTo();
//        softly.assertThat(vanillaSiteswap.append(Siteswap other)).isEqualTo(); softly.assertThat(vanillaSiteswap.resort(
//            softly.assertThat(vanillaSiteswap.resort()).isEqualTo();
    }
}