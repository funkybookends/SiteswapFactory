package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

/**
 Created by caspar on 15/01/17.
 */
public class VanillaStateTest
{
    private static final VanillaState tttff = get(true, true, true, false, false);
    private static final VanillaState ftttf = get(false, true, true, true, false);

    private static final VanillaThro ZERO = VanillaThro.getUnchecked(0);
    private static final VanillaThro ONE = VanillaThro.getUnchecked(1);
    private static final VanillaThro TWO = VanillaThro.getUnchecked(2);
    private static final VanillaThro THREE = VanillaThro.getUnchecked(3);
    private static final VanillaThro FOUR = VanillaThro.getUnchecked(4);
    private static final VanillaThro FIVE = VanillaThro.getUnchecked(5);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void canThrow() throws Exception
    {
        softly.assertThat(tttff.canThrow()).isTrue();
        softly.assertThat(ftttf.canThrow()).isFalse();
    }

    @Test
    public void getAvailableThrows() throws Exception
    {
        Set<Thro> availableThrows = tttff.getAvailableThrows();
        softly.assertThat(availableThrows.size()).isEqualTo(3);
        softly.assertThat(availableThrows.contains(THREE)).isTrue();
        softly.assertThat(availableThrows.contains(FOUR)).isTrue();
        softly.assertThat(availableThrows.contains(FIVE)).isTrue();

        Set<Thro> availableThrows1 = ftttf.getAvailableThrows();
        softly.assertThat(availableThrows1.size()).isEqualTo(1);
        softly.assertThat(availableThrows1.contains(ZERO)).isTrue();
    }

    @Test
    public void getMaxThrow() throws Exception
    {
        softly.assertThat(tttff.getMaxThrow()).isSameAs(VanillaThro.get(5));
        softly.assertThat(ftttf.getMaxThrow()).isSameAs(VanillaThro.get(5));
    }

    @Test
    public void getNumObjects() throws Exception
    {
        softly.assertThat(tttff.getNumObjects()).isEqualTo(3);
        softly.assertThat(ftttf.getNumObjects()).isEqualTo(3);
    }

    @Test
    public void isGroundState() throws Exception
    {
        softly.assertThat(tttff.isGroundState()).isTrue();
        softly.assertThat(ftttf.isGroundState()).isFalse();
    }

    @Test
    public void getThrow() throws Exception
    {
        softly.assertThat(ftttf.getThrow(tttff)).isEqualTo(ZERO);
        softly.assertThat(tttff.getThrow(tttff)).isEqualTo(THREE);
    }

    @Test
    public void excitedness() throws Exception
    {
        softly.assertThat(tttff.excitedness()).isEqualTo(7);
        softly.assertThat(ftttf.excitedness()).isEqualTo(14);
    }

    @Test
    public void getGroundState() throws Exception
    {
        softly.assertThat(VanillaStateUtils.getGroundState(5, 3)).isEqualTo(tttff);
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

    private static VanillaState get(final boolean... occupied)
    {
        try
        {
            return new VanillaState(occupied);
        }
        catch (final PeriodException | NumObjectsException cause)
        {
            throw new RuntimeException("Could not construct state", cause);
        }
    }

    @Test
    public void thro() throws Exception
    {
        softly.assertThat(ftttf.thro(ZERO)).isEqualTo(tttff);
        softly.assertThat(tttff.thro(THREE)).isEqualTo(tttff);
    }

    @Test
    public void getNextStates() throws Exception
    {
        final Set<State> nextStates = tttff.getNextStates();
        Assert.assertEquals(3, nextStates.size());
        Assert.assertTrue(nextStates.contains(tttff));
        Assert.assertTrue(nextStates.contains(get(true, true, false, true, false)));
        Assert.assertTrue(nextStates.contains(get(true, true, false, false, true)));

        final Set<State> nextStates1 = ftttf.getNextStates();
        Assert.assertEquals(1, nextStates1.size());
        Assert.assertTrue(nextStates1.contains(tttff));

        final Set<State> nextStates2 = get(false, true, true, true, false, false).getNextStates();
        Assert.assertEquals(1, nextStates2.size());
        Assert.assertTrue(nextStates2.contains(get(true, true, true, false, false, false)));
    }

    @Test
    public void canTransition() throws Exception
    {
        softly.assertThat(ftttf.canTransition(tttff)).isTrue();
        softly.assertThat(tttff.canTransition(ftttf)).isFalse();
    }

    @Test
    public void getFirstStateTest() throws Exception
    {
        VanillaState firstState = VanillaStateUtils.getFirstState(new VanillaThro[]{THREE});
        Assert.assertEquals(get(true, true, true), firstState);

        VanillaState firstState1 = VanillaStateUtils.getFirstState(new VanillaThro[]{FIVE, THREE, ONE});
        Assert.assertEquals(tttff, firstState1);
    }

    @Test
    public void testEqualsMethod() throws Exception
    {
        Assert.assertTrue(get(true, false, false, true).equals(get(true, false, false, true)));
    }
}