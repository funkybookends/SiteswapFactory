package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.factories.FourHandedSiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.generator.siteswap.factories.TwoHandedSiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
Created by caspar on 12/08/17.
*/
public class StartingStrategyStabilityTest
{
   private static final Logger LOG = LoggerFactory.getLogger(StartingStrategyStabilityTest.class);

   @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

   @Test
   @Ignore("Needs new implementation to fix issues.")
   public void testFHSStability() throws Exception
   {
       final int numObjects = 3;
       final int maxThrow = 8;

       final long count = FourHandedSiteswapGenerator.all(numObjects, maxThrow).generate()
               //                .peek(s -> LOG.info("{}", s.toString()))
               .peek(siteswap ->
               {
                   final String strSiteswap = siteswap.toString();

                   for (final String constructor : SortingTestUtils.getRotations(strSiteswap))
                   {
                       try
                       {
                           final FourHandedSiteswap fhs = SiteswapFactory.getFourHandedSiteswap(constructor);
                           assertThat(fhs.toString()).as("Constructed with " + constructor).isEqualTo(strSiteswap);
                       }
                       catch (InvalidSiteswapException e)
                       {
                           fail("Could not construct siteswap with rotation " + constructor + " of " + strSiteswap);
                       }
                   }
               }).count();

       LOG.info("{} stability tests passed", count);
   }

   @Test
   public void testHTFStability() throws Exception
   {
       final int numObjects = 3;
       final int maxThrow = 8;

       final long count = TwoHandedSiteswapGenerator.all(numObjects, maxThrow).generate()
               //                .peek(s -> LOG.info("{}", s.toString()))
               .peek(siteswap ->
               {
                   final String strSiteswap = siteswap.toString();

                   for (final String constructor : SortingTestUtils.getRotations(strSiteswap))
                   {
                       try
                       {
                           final TwoHandedSiteswap ths = SiteswapFactory.getTwoHandedSiteswap(constructor);
                           assertThat(ths.toString()).as("Constructed with " + constructor).isEqualTo(strSiteswap);
                       }
                       catch (InvalidSiteswapException e)
                       {
                           fail("Could not construct siteswap with rotation " + constructor + " of " + strSiteswap);
                       }
                   }
               }).count();

       LOG.info("{} stability tests passed", count);
   }
}
