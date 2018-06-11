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
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StringToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
Created by caspar on 06/08/17.
*/
@RunWith(JUnitParamsRunner.class)
public class FourHandedSiteswapTest
{
   @Rule
   public JUnitSoftAssertions softly = new JUnitSoftAssertions();

   @Test
   public void test534() throws Exception
   {
       final FourHandedSiteswap vanillaSiteswap = FourHandedSiteswapFactory.getFourHandedSiteswap("786");
       final FourHandedSiteswap same = FourHandedSiteswapFactory.getFourHandedSiteswap(new SiteswapRequest("867"));
       final FourHandedSiteswap different = FourHandedSiteswapFactory.getFourHandedSiteswap("975");
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
   }

    @Test
    public void testMixedAbilityPattern() throws Exception
    {
        final FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswapFactory.getFourHandedSiteswap("88578686");
        final FourHandedSiteswapThro[] leader = StringToIntsConverter.get().andThen(IntsToFourHandedSiteswapThrosConverter.get()).apply("8588");
        final FourHandedSiteswapThro[] follower = StringToIntsConverter.get().andThen(IntsToFourHandedSiteswapThrosConverter.get()).apply("8766");

        softly.assertThat(fourHandedSiteswap.getThrowsForJuggler(0)).isEqualTo(leader);
        softly.assertThat(fourHandedSiteswap.getThrowsForJuggler(1)).isEqualTo(follower);
    }

    @Test
    @Parameters
    public void testStartingHands(final String siteswap, final int first, final int second, final int third, final int fourth) throws Exception
    {
        final FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswapFactory.getFourHandedSiteswap(siteswap);
        softly.assertThat(fourHandedSiteswap.getNumObjects()).as("Num objects is wrong").isEqualTo(first + second + third + fourth);
        softly.assertThat(fourHandedSiteswap.getStartingNumberOfObjects(0)).as("Leader First Hand:" + siteswap).isEqualTo(first);
        softly.assertThat(fourHandedSiteswap.getStartingNumberOfObjects(1)).as("Follower First Hand:" + siteswap).isEqualTo(second);
        softly.assertThat(fourHandedSiteswap.getStartingNumberOfObjects(2)).as("Leader Second Hand:" + siteswap).isEqualTo(third);
        softly.assertThat(fourHandedSiteswap.getStartingNumberOfObjects(3)).as("Follower Second Hand:" + siteswap).isEqualTo(fourth);
    }

    public Object parametersForTestStartingHands()
    {
        return new Object[]{
            new Object[]{"978", 2, 2, 2, 2},
            new Object[]{"9", 3, 2, 2, 2},
            new Object[]{"88A72", 2, 2, 1, 2},
            new Object[]{"789A6", 3, 2, 2, 1},
            new Object[]{"7592552", 2, 1, 1, 1},
            new Object[]{"89990", 1, 2, 2, 2},
            new Object[]{"69640", 1, 2, 1, 1},
        };
    }
}