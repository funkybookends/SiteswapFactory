package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;

import static org.assertj.core.api.Assertions.assertThat;


/**
Created by caspar on 15/01/17.
*/
public class HighestThrowFirstStrategyTest
{
   private static final Map<String, String[]> correctToRotations = new HashMap<>();

   @Before
   public void setUp() throws Exception
   {
       correctToRotations.put("A6789", new String[]{"9A678", "A6789", "6789A", "789A6", "89A67"});
       correctToRotations.put("975", new String[]{"975", "759", "597"});
       correctToRotations.put("86867", new String[]{"78686", "86867", "68678", "86786", "67868"});
   }

   @Test
   public void test() throws Exception
   {
       for (String expected : correctToRotations.keySet())
       {
           for (String constructor : correctToRotations.get(expected))
           {
               final TwoHandedVanillaSiteswap twoHandedSiteswap = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap(new SiteswapRequest(constructor, StreamingFilteringReducer.get(), HighestThrowFirstStrategy.get()));
               assertThat(twoHandedSiteswap.toString()).isEqualTo(expected);
           }
       }
   }
}