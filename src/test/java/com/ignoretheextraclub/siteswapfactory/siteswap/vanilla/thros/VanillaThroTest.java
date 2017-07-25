package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 Created by caspar on 07/01/17.
 */
public class VanillaThroTest
{
    private static final Thro ZERO = VanillaThro.getOrNull(0);
    private static final Thro ONE = VanillaThro.getOrNull(1);
    private static final Thro TWO = VanillaThro.getOrNull(2);
    private static final VanillaThro THREE = VanillaThro.getOrNull(3);
    private static final VanillaThro FOUR = VanillaThro.getOrNull(4);
    private static final VanillaThro FIVE = VanillaThro.getOrNull(5);
    private static final Thro SIX = VanillaThro.getOrNull(6);
    private static final Thro SEVEN = VanillaThro.getOrNull(7);
    private static final Thro EIGHT = VanillaThro.getOrNull(8);

    private static final VanillaThro[] FIVE_THREE_FOUR = new VanillaThro[]{FIVE, THREE, FOUR};

    @Test
    public void compareTo() throws Exception
    {
        assertTrue(FIVE.compareTo(THREE) > 0);
        assertTrue(FIVE.compareTo(FIVE) == 0);
        assertTrue(FIVE.compareTo(EIGHT) < 0);
    }

    @Test
    public void getThroAsString() throws Exception
    {
        assertEquals("0", ZERO.toString());
        assertEquals("1", ONE.toString());
        assertEquals("6", SIX.toString());
    }

    @Test
    public void getNumObjectsThrown() throws Exception
    {
        assertEquals(1, ONE.getNumObjectsThrown());
        assertEquals(1, SEVEN.getNumObjectsThrown());
        assertEquals(1, FOUR.getNumObjectsThrown());
        assertEquals(1, TWO.getNumObjectsThrown());

        assertEquals(0, ZERO.getNumObjectsThrown());
    }

    @Test
    public void vanillaThrowArrayToString() throws Exception
    {
        assertEquals("534", VanillaThro.vanillaThrowArrayToString(FIVE_THREE_FOUR));
    }

    @Test
    public void vanillaThrowArrayToIntArray() throws Exception
    {
        assertArrayEquals(new int[]{5, 3, 4}, VanillaThro.vanillaThrowArrayToIntArray(FIVE_THREE_FOUR));
    }

    @Test
    public void intArrayToString() throws Exception
    {
        assertEquals("534", VanillaThro.intArrayToString(new int[]{5, 3, 4}));
    }

    @Test
    public void getOrNull() throws Exception
    {
        assertNull(VanillaThro.getOrNull(-1));
        assertNull(VanillaThro.getOrNull('?'));
    }

    @Test(expected = BadThrowException.class)
    public void get() throws Exception
    {
        VanillaThro.get(-1);
    }

    @Test
    public void intArrayToVanillaThrowArray() throws Exception
    {
        assertArrayEquals(FIVE_THREE_FOUR, VanillaThro.intArrayToVanillaThrowArray(new int[]{5, 3, 4}));
    }

    @Test
    public void numObjects() throws Exception
    {
        assertEquals(4, Thro.numObjects(FIVE_THREE_FOUR));
    }

    @Test
    public void getHighestThro() throws Exception
    {
        assertEquals(FIVE, Thro.getHighestThro(FIVE_THREE_FOUR));
    }

    @Test
    public void stringToVanillaThrowArray() throws Exception
    {
        assertArrayEquals(FIVE_THREE_FOUR, VanillaThro.stringToVanillaThrowArray("534"));
    }

    @Test
    public void charToInt() throws Exception
    {
        Assert.assertEquals(0, VanillaThro.charToInt('0'));
        Assert.assertEquals(9, VanillaThro.charToInt('9'));
        Assert.assertEquals(10, VanillaThro.charToInt('A'));
        Assert.assertEquals(12, VanillaThro.charToInt('C'));
        Assert.assertEquals(35, VanillaThro.charToInt('Z'));

        Assert.assertEquals(-1, VanillaThro.charToInt(';'));
        Assert.assertEquals(-1, VanillaThro.charToInt('-'));
        Assert.assertEquals(-1, VanillaThro.charToInt(']'));
        Assert.assertEquals(-1, VanillaThro.charToInt('<'));
        Assert.assertEquals(-1, VanillaThro.charToInt('('));
        Assert.assertEquals(-1, VanillaThro.charToInt('+'));
        Assert.assertEquals(-1, VanillaThro.charToInt('/'));
        Assert.assertEquals(-1, VanillaThro.charToInt('"'));
    }

    @Test
    public void intToChar() throws Exception
    {
        Assert.assertEquals('0', VanillaThro.intToChar(0));
        Assert.assertEquals('9', VanillaThro.intToChar(9));
        Assert.assertEquals('A', VanillaThro.intToChar(10));
        Assert.assertEquals('C', VanillaThro.intToChar(12));
        Assert.assertEquals('Z', VanillaThro.intToChar(35));
        Assert.assertEquals('?', VanillaThro.intToChar(-1));
        Assert.assertEquals('?', VanillaThro.intToChar(36));
    }

    @Test
    public void intArrayToCharArray() throws Exception
    {
        Assert.assertArrayEquals(new char[]{'0', 'A'}, VanillaThro.intArrayToCharArray(new int[]{0, 10}));
    }

    @Test
    public void charArrayToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0, 10}, VanillaThro.charArrayToIntArray(new char[]{'0', 'A'}));
    }

    @Test
    public void stringToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0, 10}, VanillaThro.stringToIntArray("0A"));
    }
}