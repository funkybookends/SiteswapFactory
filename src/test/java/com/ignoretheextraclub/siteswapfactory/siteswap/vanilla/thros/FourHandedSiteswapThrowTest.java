package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import org.junit.Assert;
import org.junit.Test;

/**
 Created by caspar on 15/01/17.
 */
public class FourHandedSiteswapThrowTest
{
    private static final FourHandedSiteswapThro ZERO = FourHandedSiteswapThro.getOrNull(0);
    private static final FourHandedSiteswapThro ONE = FourHandedSiteswapThro.getOrNull(1);
    private static final FourHandedSiteswapThro TWO = FourHandedSiteswapThro.getOrNull(2);
    private static final FourHandedSiteswapThro THREE = FourHandedSiteswapThro.getOrNull(3);
    private static final FourHandedSiteswapThro FOUR = FourHandedSiteswapThro.getOrNull(4);
    private static final FourHandedSiteswapThro FIVE = FourHandedSiteswapThro.getOrNull(5);
    private static final FourHandedSiteswapThro SIX = FourHandedSiteswapThro.getOrNull(6);
    private static final FourHandedSiteswapThro SEVEN = FourHandedSiteswapThro.getOrNull(7);
    private static final FourHandedSiteswapThro EIGHT = FourHandedSiteswapThro.getOrNull(8);
    private static final FourHandedSiteswapThro NINE = FourHandedSiteswapThro.getOrNull(9);
    private static final FourHandedSiteswapThro TEN = FourHandedSiteswapThro.getOrNull(10);
    private static final FourHandedSiteswapThro ELEVEN = FourHandedSiteswapThro.getOrNull(11);
    private static final FourHandedSiteswapThro TWELVE = FourHandedSiteswapThro.getOrNull(12);

    private static final FourHandedSiteswapThro[] NINE_SEVEN_FIVE = new FourHandedSiteswapThro[]{NINE, SEVEN, FIVE};

    @Test
    public void get() throws Exception
    {
        Assert.assertSame(ZERO, FourHandedSiteswapThro.get(0));
        Assert.assertSame(TWO, FourHandedSiteswapThro.get(2));
        Assert.assertSame(FOUR, FourHandedSiteswapThro.get(4));
        Assert.assertSame(EIGHT, FourHandedSiteswapThro.get(8));
        Assert.assertSame(TEN, FourHandedSiteswapThro.get(10));

        Assert.assertSame(ZERO, FourHandedSiteswapThro.get('0'));
        Assert.assertSame(TWO, FourHandedSiteswapThro.get('2'));
        Assert.assertSame(FOUR, FourHandedSiteswapThro.get('4'));
        Assert.assertSame(EIGHT, FourHandedSiteswapThro.get('8'));
        Assert.assertSame(ELEVEN, FourHandedSiteswapThro.get('B'));
        Assert.assertSame(TWELVE, FourHandedSiteswapThro.get('C'));

        Assert.assertNull(ONE);
        Assert.assertNull(THREE);
        Assert.assertNull(FourHandedSiteswapThro.getOrNull('D'));
        Assert.assertNull(FourHandedSiteswapThro.getOrNull(-1));
        Assert.assertNull(FourHandedSiteswapThro.getOrNull(-50));
        Assert.assertNull(FourHandedSiteswapThro.getOrNull('?'));
    }

    @Test
    public void toHefflish() throws Exception
    {
        Assert.assertEquals("gap", ZERO.toHefflish());
        Assert.assertEquals("zip", TWO.toHefflish());
        Assert.assertEquals("hold", FOUR.toHefflish());
        Assert.assertEquals("self", SIX.toHefflish());
        Assert.assertEquals("heff", EIGHT.toHefflish());
        Assert.assertEquals("trelf", TEN.toHefflish());
        Assert.assertEquals("triple", ELEVEN.toHefflish());
    }

    @Test
    public void intArrayToFourHandedSiteswapThrowArray() throws Exception
    {
        Assert.assertArrayEquals(NINE_SEVEN_FIVE,
                                 FourHandedSiteswapThro.intArrayToFourHandedSiteswapThrowArray(new int[]{9, 7, 5}));
    }

    @Test
    public void intToHefflish() throws Exception
    {
        Assert.assertEquals("", FourHandedSiteswapThro.intToHefflish(-1));
        Assert.assertEquals("gap", FourHandedSiteswapThro.intToHefflish(0));
        Assert.assertEquals("", FourHandedSiteswapThro.intToHefflish(1));
        Assert.assertEquals("zip", FourHandedSiteswapThro.intToHefflish(2));
        Assert.assertEquals("", FourHandedSiteswapThro.intToHefflish(3));
        Assert.assertEquals("quad", FourHandedSiteswapThro.intToHefflish(12));
        Assert.assertEquals("", FourHandedSiteswapThro.intToHefflish(13));
        Assert.assertEquals("", FourHandedSiteswapThro.intToHefflish(15));
    }

    @Test
    public void fourHandedIntToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p", FourHandedSiteswapThro.fourHandedIntToPrechac(7));
        Assert.assertEquals("3", FourHandedSiteswapThro.fourHandedIntToPrechac(6));
        Assert.assertEquals("-1.5p", FourHandedSiteswapThro.fourHandedIntToPrechac(-3));
    }

    @Test
    public void fourHandedIntsToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p 3 4", FourHandedSiteswapThro.fourHandedIntsToPrechac(new int[]{7, 6, 8}));
        Assert.assertEquals("3.5p 4.5p 1", FourHandedSiteswapThro.fourHandedIntsToPrechac(new int[]{7, 9, 2}));
    }
}