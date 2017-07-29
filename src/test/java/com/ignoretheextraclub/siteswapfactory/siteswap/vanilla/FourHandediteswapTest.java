//package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;
//
//import com.ignoretheextraclub.siteswapfactory.siteswap.State;
//import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateUtils;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
//import org.assertj.core.api.SoftAssertions;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// Created by caspar on 28/07/17.
// */
//public class FourHandediteswapTest
//{
//
//    private SoftAssertions softly;
//
//    @Before
//    public void setUp() throws Exception
//    {
//        this.softly = new SoftAssertions();
//    }
//
//    @Test
//    public void testHands() throws Exception
//    {
//        final FourHandedSiteswapThro[] fourHandedSiteswapThros = FourHandedSiteswapThro.intArrayToFourHandedSiteswapThrowArray(
//                new int[]{7, 5, 6});
//
//        final State firstState = VanillaStateUtils.getFirstState(fourHandedSiteswapThros);
//
//        final VanillaState[] allStates = (VanillaState[]) StateUtils.getAllStates(firstState, fourHandedSiteswapThros);
//
//        final FourHandedSiteswap fhs = new FourHandedSiteswap(allStates,
//                                                              fourHandedSiteswapThros,
//                                                              new NoSortingStrategy<VanillaState>());
//
//        softly.assertThat(fhs.getNumObjects()).isEqualTo(6);
//        softly.assertThat(fhs.getStartingNumberOfObjects(0)).as("Juggler 1 right hand").isEqualTo(3);
//        softly.assertThat(fhs.getStartingNumberOfObjects(1)).as("Juggler 2 right hand").isEqualTo(3);
//        softly.assertThat(fhs.getStartingNumberOfObjects(2)).as("Juggler 1 left hand").isEqualTo(3);
//        softly.assertThat(fhs.getStartingNumberOfObjects(3)).as("Juggler 2 left hand").isEqualTo(3);
//        softly.assertAll();
//    }
//}