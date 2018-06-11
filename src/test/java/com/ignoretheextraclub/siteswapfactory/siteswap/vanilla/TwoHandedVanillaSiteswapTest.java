/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;

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
       final TwoHandedVanillaSiteswap vanillaSiteswap = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("534");
       final TwoHandedVanillaSiteswap same = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("453");
       final TwoHandedVanillaSiteswap different = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("441");

       softly.assertThat(vanillaSiteswap.getNumJugglers()).isEqualTo(1);
       softly.assertThat(vanillaSiteswap.getNumHands()).isEqualTo(2);
       softly.assertThat(vanillaSiteswap.getNumObjects()).isEqualTo(4);
       softly.assertThat(vanillaSiteswap.getPeriod()).isEqualTo(3);
       softly.assertThat(vanillaSiteswap.getThrows()).isEqualTo(thros(get(5), get(3), get(4)));
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