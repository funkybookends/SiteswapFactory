package com.ignoretheextraclub.siteswapfactory.thros;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 15/01/17.
 */
public class FourHandedSiteswapThrowTest
{
    private static final FourHandedSiteswapThrow ZERO = FourHandedSiteswapThrow.getOrNull(0);
    private static final FourHandedSiteswapThrow ONE = FourHandedSiteswapThrow.getOrNull(1);
    private static final FourHandedSiteswapThrow TWO = FourHandedSiteswapThrow.getOrNull(2);
    private static final FourHandedSiteswapThrow THREE = FourHandedSiteswapThrow.getOrNull(3);
    private static final FourHandedSiteswapThrow FOUR = FourHandedSiteswapThrow.getOrNull(4);
    private static final FourHandedSiteswapThrow FIVE = FourHandedSiteswapThrow.getOrNull(5);
    private static final FourHandedSiteswapThrow SIX = FourHandedSiteswapThrow.getOrNull(6);
    private static final FourHandedSiteswapThrow SEVEN = FourHandedSiteswapThrow.getOrNull(7);
    private static final FourHandedSiteswapThrow EIGHT = FourHandedSiteswapThrow.getOrNull(8);
    private static final FourHandedSiteswapThrow NINE = FourHandedSiteswapThrow.getOrNull(9);
    private static final FourHandedSiteswapThrow TEN = FourHandedSiteswapThrow.getOrNull(10);
    private static final FourHandedSiteswapThrow ELEVEN = FourHandedSiteswapThrow.getOrNull(11);
    private static final FourHandedSiteswapThrow TWELVE = FourHandedSiteswapThrow.getOrNull(12);

    private static final FourHandedSiteswapThrow[] NINE_SEVEN_FIVE = new FourHandedSiteswapThrow[]{NINE, SEVEN, FIVE};

    @Test
    public void get() throws Exception
    {
        Assert.assertSame(ZERO, FourHandedSiteswapThrow.get(0));
        Assert.assertSame(TWO, FourHandedSiteswapThrow.get(2));
        Assert.assertSame(FOUR, FourHandedSiteswapThrow.get(4));
        Assert.assertSame(EIGHT, FourHandedSiteswapThrow.get(8));
        Assert.assertSame(TEN, FourHandedSiteswapThrow.get(10));

        Assert.assertSame(ZERO, FourHandedSiteswapThrow.get('0'));
        Assert.assertSame(TWO, FourHandedSiteswapThrow.get('2'));
        Assert.assertSame(FOUR, FourHandedSiteswapThrow.get('4'));
        Assert.assertSame(EIGHT, FourHandedSiteswapThrow.get('8'));
        Assert.assertSame(ELEVEN, FourHandedSiteswapThrow.get('B'));
        Assert.assertSame(TWELVE, FourHandedSiteswapThrow.get('C'));

        Assert.assertNull(ONE);
        Assert.assertNull(THREE);
        Assert.assertNull(FourHandedSiteswapThrow.getOrNull('D'));
        Assert.assertNull(FourHandedSiteswapThrow.getOrNull(-1));
        Assert.assertNull(FourHandedSiteswapThrow.getOrNull(-50));
        Assert.assertNull(FourHandedSiteswapThrow.getOrNull('?'));
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
        Assert.assertArrayEquals(NINE_SEVEN_FIVE, FourHandedSiteswapThrow.intArrayToFourHandedSiteswapThrowArray(new int[]{9,7,5}));
    }

    @Test
    public void intToHefflish() throws Exception
    {
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(-1));
        Assert.assertEquals("gap", FourHandedSiteswapThrow.intToHefflish(0));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(1));
        Assert.assertEquals("zip", FourHandedSiteswapThrow.intToHefflish(2));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(3));
        Assert.assertEquals("quad", FourHandedSiteswapThrow.intToHefflish(12));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(13));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(15));
    }

    @Test
    public void fourHandedIntToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p", FourHandedSiteswapThrow.fourHandedIntToPrechac(7));
        Assert.assertEquals("3", FourHandedSiteswapThrow.fourHandedIntToPrechac(6));
        Assert.assertEquals("-1.5p", FourHandedSiteswapThrow.fourHandedIntToPrechac(-3));
    }

    @Test
    public void fourHandedIntsToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p 3 4", FourHandedSiteswapThrow.fourHandedIntsToPrechac(new int[] {7, 6, 8}));
        Assert.assertEquals("3.5p 4.5p 1", FourHandedSiteswapThrow.fourHandedIntsToPrechac(new int[] {7,9,2}));
    }

}