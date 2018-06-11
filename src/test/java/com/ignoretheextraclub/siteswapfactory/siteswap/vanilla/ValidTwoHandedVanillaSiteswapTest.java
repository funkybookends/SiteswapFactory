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

import java.io.FileNotFoundException;
import java.util.Collection;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.NoStartingStrategy;
import com.ignoretheextraclub.siteswapfactory.testutils.Utils;

/**
Created by caspar on 25/06/17.
*/
@RunWith(Parameterized.class)
public class ValidTwoHandedVanillaSiteswapTest
{
   private static final String VALID_TWO_HANDED_SITESWAPS_LIST = "siteswapLists/THS-unsorted-valid.list";
   private final String stringSiteswap;

   public ValidTwoHandedVanillaSiteswapTest(final String stringSiteswap)
   {
       this.stringSiteswap = stringSiteswap;
   }

   @Rule
   public JUnitSoftAssertions softly = new JUnitSoftAssertions();

   @Parameterized.Parameters(name = "{0}")
   public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
   {
       return Utils.load(VALID_TWO_HANDED_SITESWAPS_LIST);
   }

   @Test
   public void testCreateWithNoSortingStrategy() throws Exception
   {
       final TwoHandedVanillaSiteswap unsorted = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap(new SiteswapRequest(stringSiteswap, StreamingFilteringReducer.get(), NoStartingStrategy.get()));
       softly.assertThat(unsorted.toString()).as("unsorted toString").isEqualTo(stringSiteswap.toUpperCase());
       final TwoHandedVanillaSiteswap hfsSorted = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap(new SiteswapRequest(stringSiteswap, StreamingFilteringReducer.get(), HighestThrowFirstStrategy.get()));
//        softly.assertThat(hfsSorted.toString()).as("hfsSorted toString").isEqualTo(stringSiteswap.toUpperCase()); // TODO sort all siteswaps in list so we can assert.

       softly.assertThat(unsorted.same(hfsSorted)).as("unsorted.same(hfsSorted)").isTrue();
       softly.assertThat(hfsSorted.same(unsorted)).as("hfsSorted.same(unsorted)").isTrue();
//        softly.assertThat(unsorted.equals(hfsSorted)).as("unsorted.equals(hfsSorted)").isTrue(); // TODO sort all siteswaps in list so we can assert.
//        softly.assertThat(hfsSorted.equals(unsorted)).as("hfsSorted.equals(unsorted)").isTrue(); // TODO sort all siteswaps in list so we can assert.

   }
}