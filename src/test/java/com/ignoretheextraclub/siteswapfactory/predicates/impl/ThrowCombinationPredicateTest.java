package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils.getLoop;
import static com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils.getSequence;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils.stringToVanillaThrowArray;

/**
 Created by caspar on 02/08/17.
 */
public class ThrowCombinationPredicateTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testThatFirstAndLastElementsMustNotBeNull() throws Exception
    {
        softly.assertThatThrownBy(() -> new ThrowCombinationPredicate(null, getUnchecked(3)))
              .isInstanceOf(NullPointerException.class);

        softly.assertThatThrownBy(() -> new ThrowCombinationPredicate(getUnchecked(3), null))
              .isInstanceOf(NullPointerException.class);

        softly.assertThatThrownBy(() -> new ThrowCombinationPredicate((VanillaThro) null))
              .isInstanceOf(NullPointerException.class);

        softly.assertThatThrownBy(() -> new ThrowCombinationPredicate((VanillaThro) null,
                getUnchecked(3),
                getUnchecked(5),
                getUnchecked(1))).isInstanceOf(NullPointerException.class);

        softly.assertThatThrownBy(() -> new ThrowCombinationPredicate(getUnchecked(3),
                getUnchecked(5),
                getUnchecked(1),
                null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testSingleThro() throws Exception
    {
        final ThrowCombinationPredicate predicate = new ThrowCombinationPredicate(getUnchecked(3));
        final SequencePredicate negate = predicate.negate();

        softly.assertThat(predicate.testSequence(seq("3"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("53"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("441355500"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("4413"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("531"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("3"))).isTrue();

        softly.assertThat(negate.testSequence(seq("3"))).isFalse();
        softly.assertThat(negate.testSequence(seq("53"))).isFalse();
        softly.assertThat(negate.testSequence(seq("441355500"))).isFalse();
        softly.assertThat(negate.testSequence(seq("4413"))).isFalse();
        softly.assertThat(negate.testSequence(seq("531"))).isFalse();
        softly.assertThat(negate.testSequence(seq("3"))).isFalse();

        softly.assertThat(predicate.testSequence(seq("441"))).isFalse();
        softly.assertThat(predicate.testSequence(seq("552"))).isFalse();
        softly.assertThat(predicate.testSequence(seq("51"))).isFalse();

        softly.assertThat(negate.testSequence(seq("441"))).isTrue();
        softly.assertThat(negate.testSequence(seq("552"))).isTrue();
        softly.assertThat(negate.testSequence(seq("51"))).isTrue();

        softly.assertThat(predicate.testLoop(loop("3"))).isTrue();
        softly.assertThat(predicate.testLoop(loop("441355500"))).isTrue();
        softly.assertThat(predicate.testLoop(loop("4413"))).isTrue();
        softly.assertThat(predicate.testLoop(loop("531"))).isTrue();
        softly.assertThat(predicate.testLoop(loop("3"))).isTrue();

        softly.assertThat(negate.testLoop(loop("3"))).isFalse();
        softly.assertThat(negate.testLoop(loop("441355500"))).isFalse();
        softly.assertThat(negate.testLoop(loop("4413"))).isFalse();
        softly.assertThat(negate.testLoop(loop("531"))).isFalse();
        softly.assertThat(negate.testLoop(loop("3"))).isFalse();

        softly.assertThat(predicate.testLoop(loop("441"))).isFalse();
        softly.assertThat(predicate.testLoop(loop("51"))).isFalse();

        softly.assertThat(negate.testLoop(loop("441"))).isTrue();
        softly.assertThat(negate.testLoop(loop("51"))).isTrue();
    }

    @Test
    public void testPairOfThrows() throws Exception
    {
        final ThrowCombinationPredicate predicate = new ThrowCombinationPredicate(stringToVanillaThrowArray("42"));
        final SequencePredicate negate = predicate.negate();

        softly.assertThat(predicate.testSequence(seq("342"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("42"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("5314242531"))).isTrue();

        softly.assertThat(negate.testSequence(seq("342"))).isFalse();
        softly.assertThat(negate.testSequence(seq("42"))).isFalse();
        softly.assertThat(negate.testSequence(seq("5314242531"))).isFalse();

        softly.assertThat(predicate.testLoop(loop("342"))).isTrue();
        softly.assertThat(predicate.testLoop(loop("24"))).isTrue();
    }

    @Test
    public void testSequenceWithNulls() throws Exception
    {
        final ThrowCombinationPredicate predicate = new ThrowCombinationPredicate(getUnchecked(5),
                null,
                getUnchecked(1));
        final SequencePredicate negate = predicate.negate();

        softly.assertThat(predicate.testSequence(seq("531"))).isTrue();
        softly.assertThat(predicate.testSequence(seq("333531"))).isTrue();

        softly.assertThat(negate.testSequence(seq("531"))).isFalse();
        softly.assertThat(negate.testSequence(seq("333531"))).isFalse();

        softly.assertThat(predicate.testLoop(loop("315"))).isTrue();
        softly.assertThat(predicate.testLoop(loop("333531"))).isTrue();

        softly.assertThat(negate.testLoop(loop("315"))).isFalse();
        softly.assertThat(negate.testLoop(loop("333531"))).isFalse();
    }

    private State[] seq(final String seq) throws InvalidSiteswapException
    {
        final VanillaThro[] vanillaThros = VanillaStateUtils.castAllToVanillaThro(stringToVanillaThrowArray(seq));
        final VanillaState firstState = VanillaStateUtils.getFirstState(vanillaThros);
        return getSequence(firstState, vanillaThros);
    }

    private State[] loop(final String seq) throws InvalidSiteswapException
    {
        final VanillaThro[] vanillaThros = VanillaStateUtils.castAllToVanillaThro(stringToVanillaThrowArray(seq));
        final VanillaState firstState = VanillaStateUtils.getFirstState(vanillaThros);
        return getLoop(firstState, vanillaThros);
    }
}