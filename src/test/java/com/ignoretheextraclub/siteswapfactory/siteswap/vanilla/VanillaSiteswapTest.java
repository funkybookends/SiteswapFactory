package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.states;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;

/**
 Created by caspar on 12/08/17.
 */
public class VanillaSiteswapTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void test534() throws Exception
    {
        final VanillaSiteswap vanillaSiteswap = getVanillaSiteswap(new int[]{5, 3, 4}, NoSortingStrategy.get());
        final VanillaSiteswap same = getVanillaSiteswap(new int[]{4,5,3}, NoSortingStrategy.get());
        final VanillaSiteswap different = getVanillaSiteswap(new int[]{4,4,1}, NoSortingStrategy.get());

        softly.assertThatThrownBy(vanillaSiteswap::getNumJugglers).isInstanceOf(UnsupportedOperationException.class);
        softly.assertThatThrownBy(vanillaSiteswap::getNumHands).isInstanceOf(UnsupportedOperationException.class);
        softly.assertThat(vanillaSiteswap.getNumObjects()).isEqualTo(4);
        softly.assertThat(vanillaSiteswap.getPeriod()).isEqualTo(3);
        softly.assertThat(vanillaSiteswap.getThrows()).isEqualTo(thros(getUnchecked(5), getUnchecked(3), getUnchecked(4)));
        softly.assertThat(vanillaSiteswap.getThrowsForJuggler(0)).isEqualTo(thros(getUnchecked(5), getUnchecked(3), getUnchecked(4)));
        softly.assertThat(vanillaSiteswap.getStates()).isEqualTo(states(XXXX_, XXX_X, XXXX_));
        softly.assertThat(vanillaSiteswap.isGrounded()).isEqualTo(true);
        softly.assertThat(vanillaSiteswap.isPrime()).isEqualTo(false);
        softly.assertThat(vanillaSiteswap.getHighestThro()).isEqualTo(getUnchecked(5));
        softly.assertThat(vanillaSiteswap.getSortingStrategy()).isEqualTo(NoSortingStrategy.get());
        softly.assertThatThrownBy(() -> vanillaSiteswap.getStartingNumberOfObjects(0)).isInstanceOf(UnsupportedOperationException.class);
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

    private VanillaSiteswap getVanillaSiteswap(final int[] siteswap, final SortingStrategy strategy) throws InvalidSiteswapException
    {
        final VanillaThro[] thros = VanillaThroUtils.intArrayToVanillaThrowArray(siteswap);
        final VanillaState firstState = VanillaStateUtils.getFirstState(thros);
        final VanillaState[] allStates = StateUtils.getAllStates(firstState, thros);
        return new VanillaSiteswap(allStates, thros, strategy);
    }
}