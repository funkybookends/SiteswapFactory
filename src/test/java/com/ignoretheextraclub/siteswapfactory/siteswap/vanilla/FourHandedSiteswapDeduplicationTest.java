//package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;
//
//import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoStartingStrategy;
//import com.ignoretheextraclub.siteswapfactory.testutils.Utils;
//import org.assertj.core.api.JUnitSoftAssertions;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.io.FileNotFoundException;
//import java.util.Collection;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
///**
// Created by caspar on 25/06/17.
// */
//@RunWith(Parameterized.class)
//public class FourHandedSiteswapDeduplicationTest
//{
//    private static final String VALID_FOUR_HANDED_SITESWAPS_LIST = "siteswapLists/FHS-unsorted-valid-dups.list";
//    private final String stringSiteswap;
//
//    public FourHandedSiteswapDeduplicationTest(final String stringSiteswap)
//    {
//        this.stringSiteswap = stringSiteswap;
//    }
//
//    @Rule
//    public JUnitSoftAssertions softly = new JUnitSoftAssertions();
//
//    @Parameterized.Parameters(name = "{0}")
//    public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
//    {
//        return Utils.load(VALID_FOUR_HANDED_SITESWAPS_LIST);
//    }
//
//    @Test
//    public void testCreateWithNoSortingStrategy() throws Exception
//    {
//        final FourHandedSiteswap fhs = SiteswapFactory.createFHS(stringSiteswap, NoStartingStrategy.get(), true);
//        assertThat(fhs.toString()).isNotEqualTo(stringSiteswap.toUpperCase());
//    }
//
//    @Test
//    public void testCreateWithHighestThrowFirstStrategy() throws Exception
//    {
//        final FourHandedSiteswap fhs = SiteswapFactory.createFHS(stringSiteswap, HighestThrowFirstStrategy.get(), true);
//    }
//
//    @Test
//    public void testCreateWithNoSortingStrategyNoReduce() throws Exception
//    {
//        final FourHandedSiteswap fhs = SiteswapFactory.createFHS(stringSiteswap, NoStartingStrategy.get(), false);
//        assertThat(fhs.toString()).isEqualTo(stringSiteswap.toUpperCase());
//    }
//
//    @Test
//    public void testCreateWithHighestThrowFirstStrategyNoReduce() throws Exception
//    {
//        final FourHandedSiteswap fhs = SiteswapFactory.createFHS(stringSiteswap, HighestThrowFirstStrategy.get(), false);
//    }
//
//    @Test
//    public void testCreateWithFourHandedPassingStrategy() throws Exception
//    {
//        SiteswapFactory.createFHS(stringSiteswap, FourHandedPassingStrategy.get(), true);
//    }
//}