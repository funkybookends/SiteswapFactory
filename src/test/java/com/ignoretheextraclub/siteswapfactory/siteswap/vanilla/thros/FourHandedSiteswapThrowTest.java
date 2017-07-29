//package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;
//
//import org.assertj.core.api.JUnitSoftAssertions;
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.Test;
//
///**
// Created by caspar on 15/01/17.
// */
//public class FourHandedSiteswapThrowTest
//{
//    private static final FourHandedSiteswapThro ZERO = FourHandedSiteswapThro.getUnchecked(0);
//    private static final FourHandedSiteswapThro ONE = FourHandedSiteswapThro.getUnchecked(1);
//    private static final FourHandedSiteswapThro TWO = FourHandedSiteswapThro.getUnchecked(2);
//    private static final FourHandedSiteswapThro THREE = FourHandedSiteswapThro.getUnchecked(3);
//    private static final FourHandedSiteswapThro FOUR = FourHandedSiteswapThro.getUnchecked(4);
//    private static final FourHandedSiteswapThro FIVE = FourHandedSiteswapThro.getUnchecked(5);
//    private static final FourHandedSiteswapThro SIX = FourHandedSiteswapThro.getUnchecked(6);
//    private static final FourHandedSiteswapThro SEVEN = FourHandedSiteswapThro.getUnchecked(7);
//    private static final FourHandedSiteswapThro EIGHT = FourHandedSiteswapThro.getUnchecked(8);
//    private static final FourHandedSiteswapThro NINE = FourHandedSiteswapThro.getUnchecked(9);
//    private static final FourHandedSiteswapThro TEN = FourHandedSiteswapThro.getUnchecked(10);
//    private static final FourHandedSiteswapThro ELEVEN = FourHandedSiteswapThro.getUnchecked(11);
//    private static final FourHandedSiteswapThro TWELVE = FourHandedSiteswapThro.getUnchecked(12);
//
//    private static final FourHandedSiteswapThro[] NINE_SEVEN_FIVE = new FourHandedSiteswapThro[]{NINE, SEVEN, FIVE};
//
//    @Rule
//    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
//
//    @Test
//    public void get() throws Exception
//    {
//        softly.assertThat(ZERO).isSameAs(FourHandedSiteswapThro.get(0));
//        softly.assertThat(TWO).isSameAs(FourHandedSiteswapThro.get(2));
//        softly.assertThat(FOUR).isSameAs(FourHandedSiteswapThro.get(4));
//        softly.assertThat(EIGHT).isSameAs(FourHandedSiteswapThro.get(8));
//        softly.assertThat(TEN).isSameAs(FourHandedSiteswapThro.get(10));
//
//        softly.assertThat(ZERO).isSameAs(FourHandedSiteswapThro.get('0'));
//        softly.assertThat(TWO).isSameAs(FourHandedSiteswapThro.get('2'));
//        softly.assertThat(FOUR).isSameAs(FourHandedSiteswapThro.get('4'));
//        softly.assertThat(EIGHT).isSameAs(FourHandedSiteswapThro.get('8'));
//        softly.assertThat(ELEVEN).isSameAs(FourHandedSiteswapThro.get('B'));
//        softly.assertThat(TWELVE).isSameAs(FourHandedSiteswapThro.get('C'));
//
//        softly.assertThat(ONE).isNull();
//        softly.assertThat(THREE).isNull();
//        softly.assertThat(FourHandedSiteswapThro.getUnchecked('D')).isNull();
//        softly.assertThat(FourHandedSiteswapThro.getUnchecked(-1)).isNull();
//        softly.assertThat(FourHandedSiteswapThro.getUnchecked(-50)).isNull();
//        softly.assertThat(FourHandedSiteswapThro.getUnchecked('?')).isNull();
//    }
//
//    @Test
//    public void toHefflish() throws Exception
//    {
//        softly.assertThat(ZERO.toHefflish()).isEqualTo("gap");
//        softly.assertThat(TWO.toHefflish()).isEqualTo("zip");
//        softly.assertThat(FOUR.toHefflish()).isEqualTo("hold");
//        softly.assertThat(SIX.toHefflish()).isEqualTo("self");
//        softly.assertThat(EIGHT.toHefflish()).isEqualTo("heff");
//        softly.assertThat(TEN.toHefflish()).isEqualTo("trelf");
//        softly.assertThat(ELEVEN.toHefflish()).isEqualTo("triple");
//    }
//
//    @Test
//    public void intArrayToFourHandedSiteswapThrowArray() throws Exception
//    {
//        Assert.assertArrayEquals(NINE_SEVEN_FIVE,
//                                 FourHandedSiteswapThro.intArrayToFourHandedSiteswapThrowArray(new int[]{9, 7, 5}));
//    }
//
//    @Test
//    public void intToHefflish() throws Exception
//    {
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(-1)).isEqualTo("");
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(0)).isEqualTo("gap");
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(1)).isEqualTo("");
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(2)).isEqualTo("zip");
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(3)).isEqualTo("");
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(12)).isEqualTo("quad");
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(13)).isEqualTo("");
//        softly.assertThat(FourHandedSiteswapThro.intToHefflish(15)).isEqualTo("");
//    }
//
//    @Test
//    public void fourHandedIntToPrechac() throws Exception
//    {
//        Assert.assertEquals("3.5p", FourHandedSiteswapThro.fourHandedIntToPrechac(7));
//        Assert.assertEquals("3", FourHandedSiteswapThro.fourHandedIntToPrechac(6));
//        Assert.assertEquals("-1.5p", FourHandedSiteswapThro.fourHandedIntToPrechac(-3));
//    }
//
//    @Test
//    public void fourHandedIntsToPrechac() throws Exception
//    {
//        Assert.assertEquals("3.5p 3 4", FourHandedSiteswapThro.fourHandedIntsToPrechac(new int[]{7, 6, 8}));
//        Assert.assertEquals("3.5p 4.5p 1", FourHandedSiteswapThro.fourHandedIntsToPrechac(new int[]{7, 9, 2}));
//    }
//}