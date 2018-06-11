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
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.NoStartingStrategy;
import com.ignoretheextraclub.siteswapfactory.testutils.Utils;

import static org.assertj.core.api.Assertions.assertThat;

/**
Created by caspar on 25/06/17.
*/
@RunWith(Parameterized.class)
public class ValidFourHandedSiteswapsTest
{
   private static final String VALID_FOUR_HANDED_SITESWAPS_LIST = "siteswapLists/FHS-HTF-valid.list";
   private final String stringSiteswap;

   public ValidFourHandedSiteswapsTest(final String stringSiteswap)
   {
       this.stringSiteswap = stringSiteswap;
   }

   @Rule
   public JUnitSoftAssertions softly = new JUnitSoftAssertions();

   @Parameterized.Parameters(name = "{0}")
   public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
   {
       return Utils.load(VALID_FOUR_HANDED_SITESWAPS_LIST);
   }

   @Test
   public void testCreateWithNoSortingStrategy() throws Exception
   {
       final FourHandedSiteswap unsorted = FourHandedSiteswapFactory.getFourHandedSiteswap(new SiteswapRequest(stringSiteswap, StreamingFilteringReducer.get(), NoStartingStrategy.get()));
       assertThat(unsorted.toString()).isEqualTo(stringSiteswap.toUpperCase());
       final FourHandedSiteswap hfsSorted = FourHandedSiteswapFactory.getFourHandedSiteswap(new SiteswapRequest(stringSiteswap, StreamingFilteringReducer.get(), HighestThrowFirstStrategy.get()));
       assertThat(hfsSorted.toString()).isEqualTo(stringSiteswap.toUpperCase());
       assertThat(hfsSorted.same(unsorted)).isTrue();
       assertThat(unsorted.same(hfsSorted)).isTrue();
       assertThat(hfsSorted.equals(unsorted)).isTrue();
       assertThat(unsorted.equals(hfsSorted)).isTrue();
   }

   @Test
   public void testCreateWithFourHandedPassingStrategy() throws Exception
   {
       FourHandedSiteswapFactory.getFourHandedSiteswap(new SiteswapRequest(stringSiteswap, StreamingFilteringReducer.get(), FourHandedPassingStrategy.get()));
   }
}