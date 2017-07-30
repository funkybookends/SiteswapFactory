//package com.ignoretheextraclub.siteswapfactory.generators;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
//import com.ignoretheextraclub.siteswapfactory.siteswap.State;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
//import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// Created by caspar on 15/01/17.
// */
//@Ignore("Need to create actual tests")
//public class StateSearcherTest
//{
//    private static final ObjectMapper om = new ObjectMapper();
//
//    private static final int FIRST_FINAL_PERIOD = 9;
//    private static final int SECOND_FINAL_PERIOD = 6;
//
//    @Test
//    public void name() throws Exception
//    {
//        Set<TwoHandedSiteswap> firstList = new HashSet<>();
//        StateSearcher<Thro, State> gen = new StateSearcher<Thro, State>(
//                50000,
//                200000,
//                FIRST_FINAL_PERIOD,
//                null,
//                VanillaState.getGroundState(5, 3, VanillaThro::get),
//                (result) ->
//                {
//                    try
//                    {
//                        firstList.add(new TwoHandedSiteswap(result));
//                    }
//                    catch (InvalidSiteswapException ise)
//                    {
//                        throw new RuntimeException(ise);
//                    }
//                },
//                HighestThrowFirstStrategy.get(),
//                false);
//
//        gen.start();
//
//        Thread.sleep(0);
//
//        Set<TwoHandedSiteswap> secondList = new HashSet<>();
//        StateSearcher<Thro, State> gen1 = new StateSearcher<Thro, State>(
//                50000,
//                200000,
//                SECOND_FINAL_PERIOD,
//                null,
//                VanillaState.getGroundState(6, 4, VanillaThro::get),
//                (result) ->
//                {
//                    try
//                    {
//                        secondList.add(new TwoHandedSiteswap(result));
//                    }
//                    catch (InvalidSiteswapException ise)
//                    {
//                        throw new RuntimeException(ise);
//                    }
//                },
//                HighestThrowFirstStrategy.get(),
//                false);
//
//        gen1.start();
//
//        int i = 0;
//        while (!gen.isCompleted() || !gen1.isCompleted())
//        {
//            Thread.sleep(10);
//            System.out.println("" + i + ":0:" + firstList.size());
//            System.out.println("" + i + ":1:" + secondList.size());
//            i++;
//        }
//
//        for (TwoHandedSiteswap vanillaState : firstList)
//        {
//            Assert.assertTrue(om.writeValueAsString(vanillaState), FIRST_FINAL_PERIOD >= vanillaState.getPeriod());
//        }
//        for (TwoHandedSiteswap vanillaState : secondList)
//        {
//            Assert.assertTrue(om.writeValueAsString(vanillaState), SECOND_FINAL_PERIOD >= vanillaState.getPeriod());
//        }
//    }
//
//    @Test
//    public void threadPool() throws Exception
//    {
//
//        Set<TwoHandedSiteswap> secondList = new HashSet<>();
//        StateSearcher<Thro, State> gen1 = new StateSearcher<Thro, State>(
//                50000,
//                2000,
//                9,
//                null,
//                VanillaState.getGroundState(15, 7, VanillaThro::get),
//                (result) ->
//                {
//                    try
//                    {
//                        secondList.add(new TwoHandedSiteswap(result));
//                    }
//                    catch (InvalidSiteswapException ise)
//                    {
//                        throw new RuntimeException(ise);
//                    }
//                },
//                HighestThrowFirstStrategy.get(),
//                false);
//
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        executorService.submit(gen1);
//        while (!gen1.isCompleted())
//        {
//            Thread.sleep(100);
//            System.out.println("waiting " + gen1.getNumResults() + " - " + secondList.size() + " = " + (gen1.getNumResults() - secondList
//                    .size()));
//        }
//        for (TwoHandedSiteswap twoHandedSiteswap : secondList)
//        {
//            System.out.println(twoHandedSiteswap.toString());
//        }
//    }
//}