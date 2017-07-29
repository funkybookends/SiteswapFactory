//package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;
//
//import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
//import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
//import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.Collection;
//import java.util.Set;
//
///**
// Created by caspar on 15/01/17.
// */
//public class VanillaStateTest
//{
//    private static final VanillaState<Thro> tttff = get(true, true, true, false, false);
//    private static final VanillaState<Thro> ftttf = get(false, true, true, true, false);
//
//    private static final VanillaThro ZERO = VanillaThro.getUnchecked(0);
//    private static final Thro ONE = VanillaThro.getUnchecked(1);
//    private static final Thro TWO = VanillaThro.getUnchecked(2);
//    private static final VanillaThro THREE = VanillaThro.getUnchecked(3);
//    private static final Thro FOUR = VanillaThro.getUnchecked(4);
//    private static final Thro FIVE = VanillaThro.getUnchecked(5);
//
//    @Test
//    public void canThrow() throws Exception
//    {
//        Assert.assertTrue(tttff.canThrow());
//        Assert.assertFalse(ftttf.canThrow());
//    }
//
//    @Test
//    public void getAvailableThrows() throws Exception
//    {
//        Set<Thro> availableThrows = tttff.getAvailableThrows();
//        Assert.assertEquals(3, availableThrows.size());
//        Assert.assertTrue(availableThrows.contains(THREE));
//        Assert.assertTrue(availableThrows.contains(FOUR));
//        Assert.assertTrue(availableThrows.contains(FIVE));
//
//        Set<Thro> availableThrows1 = ftttf.getAvailableThrows();
//        Assert.assertEquals(1, availableThrows1.size());
//        Assert.assertTrue(availableThrows1.contains(ZERO));
//    }
//
//    @Test
//    public void getMaxThrow() throws Exception
//    {
//        Assert.assertEquals(5, tttff.getMaxThrow());
//        Assert.assertEquals(5, ftttf.getMaxThrow());
//    }
//
//    @Test
//    public void getNumObjects() throws Exception
//    {
//        Assert.assertEquals(3, tttff.getNumObjects());
//        Assert.assertEquals(3, ftttf.getNumObjects());
//    }
//
//    @Test
//    public void isGroundState() throws Exception
//    {
//        Assert.assertTrue(tttff.isGroundState());
//        Assert.assertFalse(ftttf.isGroundState());
//    }
//
//    @Test
//    public void getThrow() throws Exception
//    {
//        Assert.assertEquals(ZERO, ftttf.getThrow(tttff));
//        Assert.assertEquals(THREE, tttff.getThrow(tttff));
//    }
//
//    @Test
//    public void excitedness() throws Exception
//    {
//        Assert.assertEquals(7, tttff.excitedness());
//        Assert.assertEquals(14, ftttf.excitedness());
//    }
//
//    @Test
//    public void getGroundState() throws Exception
//    {
//        Assert.assertEquals(tttff, VanillaState.getGroundState(5, 3, VanillaThro::get));
//    }
//
//    @Test
//    public void testToString() throws Exception
//    {
//        Assert.assertEquals("XXX__", tttff.toString());
//        Assert.assertEquals("_XXX_", ftttf.toString());
//    }
//
//    @Test
//    public void equals() throws Exception
//    {
//        Assert.assertEquals(tttff, tttff);
//        Assert.assertEquals(ftttf, ftttf);
//        Assert.assertEquals(tttff, get(true, true, true, false, false));
//        Assert.assertEquals(ftttf, get(false, true, true, true, false));
//
//        Assert.assertNotEquals(tttff, ftttf);
//        Assert.assertNotEquals(ftttf, tttff);
//    }
//
//    private static VanillaState<Thro> get(final boolean... occupied)
//    {
//        try
//        {
//            return new VanillaState<>(occupied, VanillaThro::get);
//        }
//        catch (final PeriodException | NumObjectsException cause)
//        {
//            throw new RuntimeException("Could not construct state", cause);
//        }
//    }
//
//    @Test
//    public void thro() throws Exception
//    {
//        Assert.assertEquals(tttff, ftttf.thro(ZERO));
//        Assert.assertEquals(tttff, tttff.thro(THREE));
//    }
//
//    @Test
//    public void getNextStates() throws Exception
//    {
//        Collection<AbstractState> nextStates = tttff.getNextStates();
//        Assert.assertEquals(3, nextStates.size());
//        Assert.assertTrue(nextStates.contains(tttff));
//        Assert.assertTrue(nextStates.contains(get(true, true, false, true, false)));
//        Assert.assertTrue(nextStates.contains(get(true, true, false, false, true)));
//
//        Collection<AbstractState> nextStates1 = ftttf.getNextStates();
//        Assert.assertEquals(1, nextStates1.size());
//        Assert.assertTrue(nextStates1.contains(tttff));
//
//        Collection<AbstractState> nextStates2 = get(false, true, true, true, false, false).getNextStates();
//        Assert.assertEquals(1, nextStates2.size());
//        Assert.assertTrue(nextStates2.contains(get(true, true, true, false, false, false)));
//    }
//
//    @Test
//    public void canTransition() throws Exception
//    {
//        Assert.assertTrue(ftttf.canTransition(tttff));
//        Assert.assertFalse(tttff.canTransition(ftttf));
//    }
//
//    @Test
//    public void getFirstStateTest() throws Exception
//    {
//        VanillaState<Thro> firstState = VanillaState.getFirstState(new Thro[]{THREE},
//                                                                   VanillaThro::get);
//        Assert.assertEquals(get(true, true, true), firstState);
//
//        VanillaState<Thro> firstState1 = VanillaState.getFirstState(new Thro[]{FIVE, THREE, ONE},
//                                                                    VanillaThro::get);
//        Assert.assertEquals(tttff, firstState1);
//    }
//
//    @Test
//    public void testEqualsMethod() throws Exception
//    {
//        Assert.assertTrue(get(true, false, false, true).equals(get(true, false, false, true)));
//    }
//}