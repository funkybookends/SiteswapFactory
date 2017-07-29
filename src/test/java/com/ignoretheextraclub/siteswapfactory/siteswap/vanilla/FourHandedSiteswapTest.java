//package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;
//
//import com.ignoretheextraclub.siteswapfactory.siteswap.State;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Scanner;
//
///**
// Created by caspar on 25/06/17.
// */
//@RunWith(Parameterized.class)
//public class FourHandedSiteswapTest
//{
//    //    private static final String VALID_FOUR_HANDED_SITESWAPS_LIST = "validFHSDups.list";
//    public static final NoSortingStrategy<FourHandedSiteswapThro, State> NO_SORTING_STRATEGY = new NoSortingStrategy<>();
//    private static final String VALID_FOUR_HANDED_SITESWAPS_LIST = "validFourHandedSiteswaps.list";
//    private final String stringSiteswap;
//
//    public FourHandedSiteswapTest(final String stringSiteswap)
//    {
//        this.stringSiteswap = stringSiteswap;
//    }
//
//    @Parameterized.Parameters
//    public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
//    {
//        final ClassLoader classLoader = TwoHandedSiteswap.class.getClassLoader();
//        final File file = new File(classLoader.getResource(VALID_FOUR_HANDED_SITESWAPS_LIST).getFile());
//        final List<String> siteswaps = new ArrayList<>();
//
//        try (final Scanner scanner = new Scanner(file))
//        {
//            while (scanner.hasNextLine())
//            {
//                siteswaps.add(scanner.nextLine());
//            }
//        }
//
//        return siteswaps;
//    }
//
//    @Test
//    public void testCreateWithNoSortingStrategy() throws Exception
//    {
//        FourHandedSiteswap.create(stringSiteswap, NO_SORTING_STRATEGY);
//    }
//
//    @Test
//    public void testCreateWithHighestThrowFirstStrategy() throws Exception
//    {
//        FourHandedSiteswap.create(stringSiteswap, HighestThrowFirstStrategy.get());
//    }
//
//    @Test
//    public void testCreateWithFourHandedPassingStrategy() throws Exception
//    {
//        final FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswap.create(stringSiteswap,
//                                                                                FourHandedPassingStrategy.get());
//
//        System.out.println(this.stringSiteswap + " -> " + fourHandedSiteswap.toString() + " -> " + fourHandedSiteswap.getLeaderHefflish());
//    }
//}