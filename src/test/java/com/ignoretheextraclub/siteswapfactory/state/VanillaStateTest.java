package com.ignoretheextraclub.siteswapfactory.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.StateSizeException;
import com.ignoretheextraclub.siteswapfactory.thros.VanillaThrow;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

/**
 Created by caspar on 15/01/17.
 */
public class VanillaStateTest
{
    private static final VanillaState<VanillaThrow> tttff = get(true, true, true, false, false);
    private static final VanillaState<VanillaThrow> ftttf = get(false, true, true, true, false);

    private static final VanillaThrow ZERO = VanillaThrow.getOrNull(0);
    private static final VanillaThrow ONE = VanillaThrow.getOrNull(1);
    private static final VanillaThrow TWO = VanillaThrow.getOrNull(2);
    private static final VanillaThrow THREE = VanillaThrow.getOrNull(3);
    private static final VanillaThrow FOUR = VanillaThrow.getOrNull(4);
    private static final VanillaThrow FIVE = VanillaThrow.getOrNull(5);

    @Test
    public void canThrow() throws Exception
    {
        Assert.assertTrue(tttff.canThrow());
        Assert.assertFalse(ftttf.canThrow());
    }

    @Test
    public void getAvailableThrows() throws Exception
    {
        Set<VanillaThrow> availableThrows = tttff.getAvailableThrows();
        Assert.assertEquals(3, availableThrows.size());
        Assert.assertTrue(availableThrows.contains(THREE));
        Assert.assertTrue(availableThrows.contains(FOUR));
        Assert.assertTrue(availableThrows.contains(FIVE));

        Set<VanillaThrow> availableThrows1 = ftttf.getAvailableThrows();
        Assert.assertEquals(1, availableThrows1.size());
        Assert.assertTrue(availableThrows1.contains(ZERO));
    }

    @Test
    public void getMaxThrow() throws Exception
    {
        Assert.assertEquals(5, tttff.getMaxThrow());
        Assert.assertEquals(5, ftttf.getMaxThrow());
    }

    @Test
    public void getNumObjects() throws Exception
    {
        Assert.assertEquals(3, tttff.getNumObjects());
        Assert.assertEquals(3, ftttf.getNumObjects());
    }

    @Test
    public void isGroundState() throws Exception
    {
        Assert.assertTrue(tttff.isGroundState());
        Assert.assertFalse(ftttf.isGroundState());
    }

    @Test
    public void getThrow() throws Exception
    {
        Assert.assertEquals(ZERO, ftttf.getThrow(tttff));
        Assert.assertEquals(THREE, tttff.getThrow(tttff));
    }

    @Test
    public void excitedness() throws Exception
    {
        Assert.assertEquals(7, tttff.excitedness());
        Assert.assertEquals(14, ftttf.excitedness());
    }

    @Test
    public void getGroundState() throws Exception
    {
        Assert.assertEquals(tttff, VanillaState.getGroundState(5, 3, VanillaThrow::get));
    }

    @Test
    public void testToString() throws Exception
    {
        Assert.assertEquals("XXX__", tttff.toString());
        Assert.assertEquals("_XXX_", ftttf.toString());
    }

    @Test
    public void equals() throws Exception
    {
        Assert.assertEquals(tttff, tttff);
        Assert.assertEquals(ftttf, ftttf);
        Assert.assertEquals(tttff, get(true, true, true, false, false));
        Assert.assertEquals(ftttf, get(false, true, true, true, false));

        Assert.assertNotEquals(tttff, ftttf);
        Assert.assertNotEquals(ftttf, tttff);
    }

    private static VanillaState<VanillaThrow> get(final boolean... occupied)
    {
        try
        {
            return new VanillaState<>(occupied, VanillaThrow::get);
        }
        catch (final StateSizeException | NumObjectsException cause)
        {
            throw new RuntimeException("Could not construct state", cause);
        }
    }

    @Test
    public void thro() throws Exception
    {
        Assert.assertEquals(tttff, ftttf.thro(ZERO));
        Assert.assertEquals(tttff, tttff.thro(THREE));
    }

    @Test
    public void getNextStates() throws Exception
    {
        Collection<AbstractState> nextStates = tttff.getNextStates();
        Assert.assertEquals(3, nextStates.size());
        Assert.assertTrue(nextStates.contains(tttff));
        Assert.assertTrue(nextStates.contains(get(true, true, false, true, false)));
        Assert.assertTrue(nextStates.contains(get(true, true, false, false, true)));

        Collection<AbstractState> nextStates1 = ftttf.getNextStates();
        Assert.assertEquals(1, nextStates1.size());
        Assert.assertTrue(nextStates1.contains(tttff));

        Collection<AbstractState> nextStates2 = get(false, true, true, true, false, false).getNextStates();
        Assert.assertEquals(1, nextStates2.size());
        Assert.assertTrue(nextStates2.contains(get(true, true, true, false, false, false)));
    }

    @Test
    public void canTransition() throws Exception
    {
        Assert.assertTrue(ftttf.canTransition(tttff));
        Assert.assertFalse(tttff.canTransition(ftttf));
    }

    @Test
    public void getFirstStateTest() throws Exception
    {
        VanillaState<VanillaThrow> firstState = VanillaState.getFirstState(new VanillaThrow[]{THREE},
                                                                           VanillaThrow::get);
        Assert.assertEquals(get(true, true, true), firstState);

        VanillaState<VanillaThrow> firstState1 = VanillaState.getFirstState(new VanillaThrow[]{FIVE, THREE, ONE},
                                                                            VanillaThrow::get);
        Assert.assertEquals(tttff, firstState1);
    }

    @Test
    public void testEqualsMethod() throws Exception
    {
        Assert.assertTrue(get(true, false, false, true).equals(get(true, false, false, true)));
    }
}