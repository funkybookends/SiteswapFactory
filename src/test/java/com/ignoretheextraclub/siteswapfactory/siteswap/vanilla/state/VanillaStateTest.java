package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToFirstStateConverter;
import com.ignoretheextraclub.siteswapfactory.generator.state.VanillaStateGenerator;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_XX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_X_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X__XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._X_XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.state;
import static org.assertj.core.api.Assertions.assertThat;

/**
 Created by caspar on 15/01/17.
 */
public class VanillaStateTest
{
    private static final Logger LOG = LoggerFactory.getLogger(VanillaStateTest.class);

    private static final VanillaThro ZERO = VanillaThro.get(0);
    private static final VanillaThro ONE = VanillaThro.get(1);
    private static final VanillaThro TWO = VanillaThro.get(2);
    private static final VanillaThro THREE = VanillaThro.get(3);
    private static final VanillaThro FOUR = VanillaThro.get(4);
    private static final VanillaThro FIVE = VanillaThro.get(5);

    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void canThrow() throws Exception
    {
        softly.assertThat(XXX__.canThrow()).isTrue();
        softly.assertThat(_XXX_.canThrow()).isFalse();
    }

    @Test
    public void getAvailableThrows() throws Exception
    {
        Set<Thro> availableThrows = XXX__.getAvailableThrows();
        softly.assertThat(availableThrows.size()).isEqualTo(3);
        softly.assertThat(availableThrows.contains(THREE)).isTrue();
        softly.assertThat(availableThrows.contains(FOUR)).isTrue();
        softly.assertThat(availableThrows.contains(FIVE)).isTrue();

        Set<Thro> availableThrows1 = _XXX_.getAvailableThrows();
        softly.assertThat(availableThrows1.size()).isEqualTo(1);
        softly.assertThat(availableThrows1.contains(ZERO)).isTrue();
    }

    @Test
    public void getMaxThrow() throws Exception
    {
        softly.assertThat(XXX__.getMaxThrow()).isSameAs(VanillaThro.get(5));
        softly.assertThat(_XXX_.getMaxThrow()).isSameAs(VanillaThro.get(5));
    }

    @Test
    public void getNumObjects() throws Exception
    {
        softly.assertThat(XXX__.getNumObjects()).isEqualTo(3);
        softly.assertThat(_XXX_.getNumObjects()).isEqualTo(3);
    }

    @Test
    public void isGroundState() throws Exception
    {
        softly.assertThat(XXX__.isGroundState()).isTrue();
        softly.assertThat(_XXX_.isGroundState()).isFalse();
    }

    @Test
    public void getThrow() throws Exception
    {
        softly.assertThat(_XXX_.getThrow(XXX__)).isEqualTo(ZERO);
        softly.assertThat(XXX__.getThrow(XXX__)).isEqualTo(THREE);
    }

    @Test
    public void excitedness() throws Exception
    {
        softly.assertThat(XXX__.excitedness()).isEqualTo(3);
        softly.assertThat(_XXX_.excitedness()).isEqualTo(17);
    }

    @Test
    public void getGroundState() throws Exception
    {
        softly.assertThat(VanillaStateGenerator.getGroundState(3, 5)).isEqualTo(XXX__);
    }

    @Test
    public void testToString() throws Exception
    {
        Assert.assertEquals("XXX__", XXX__.toString());
        Assert.assertEquals("_XXX_", _XXX_.toString());
    }

    @Test
    public void equals() throws Exception
    {
        Assert.assertEquals(XXX__, XXX__);
        Assert.assertEquals(_XXX_, _XXX_);
        Assert.assertEquals(XXX__, state(true, true, true, false, false));
        Assert.assertEquals(_XXX_, state(false, true, true, true, false));

        Assert.assertNotEquals(XXX__, _XXX_);
        Assert.assertNotEquals(_XXX_, XXX__);
    }

    @Test
    public void thro() throws Exception
    {
        softly.assertThat(_XXX_.thro(ZERO)).isEqualTo(XXX__);
        softly.assertThat(XXX__.thro(THREE)).isEqualTo(XXX__);
    }

    @Test
    public void getNextStates() throws Exception
    {
        final Set<State> nextStates = XXX__.getNextStates();
        Assert.assertEquals(3, nextStates.size());
        Assert.assertTrue(nextStates.contains(XXX__));
        Assert.assertTrue(nextStates.contains(state(true, true, false, true, false)));
        Assert.assertTrue(nextStates.contains(state(true, true, false, false, true)));

        final Set<State> nextStates1 = _XXX_.getNextStates();
        Assert.assertEquals(1, nextStates1.size());
        Assert.assertTrue(nextStates1.contains(XXX__));

        final Set<State> nextStates2 = state(false, true, true, true, false, false).getNextStates();
        Assert.assertEquals(1, nextStates2.size());
        Assert.assertTrue(nextStates2.contains(state(true, true, true, false, false, false)));
    }

    @Test
    public void canTransition() throws Exception
    {
        softly.assertThat(_XXX_.canTransition(XXX__)).isTrue();
        softly.assertThat(XXX__.canTransition(_XXX_)).isFalse();
    }

    @Test
    public void getFirstStateTest() throws Exception
    {
        VanillaState firstState = VanillaThrosToFirstStateConverter.get().apply(new VanillaThro[]{THREE});
        Assert.assertEquals(state(true, true, true), firstState);

        VanillaState firstState1 = VanillaThrosToFirstStateConverter.get().apply(new VanillaThro[]{FIVE, THREE, ONE});
        Assert.assertEquals(XXX__, firstState1);
    }

    @Test
    public void testEqualsMethod() throws Exception
    {
        Assert.assertTrue(state(true, false, false, true).equals(state(true, false, false, true)));
    }

    @Test
    public void testExcitedness() throws Exception
    {
        final List<VanillaState> collect = VanillaStateGenerator.getAllStates(3, 5)
                                                            .sorted(Comparator.comparing(State::excitedness))
//                                                            .peek(state -> LOG.info("{} : {}",
//                                                                    state.excitedness(),
//                                                                    state.toString()))
                                                            .collect(Collectors.toList());

        assertThat(collect).containsExactly(XXX__, XX_X_, XX__X, X_XX_, X_X_X, X__XX, _XXX_, _XX_X, _X_XX, __XXX);
    }
}