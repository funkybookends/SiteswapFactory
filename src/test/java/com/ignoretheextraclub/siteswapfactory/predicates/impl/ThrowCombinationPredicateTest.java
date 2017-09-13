package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.LoopCheckingThrowCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import java.util.function.Predicate;

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
        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(null, getUnchecked(3)))
              .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(getUnchecked(3), null))
              .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate((VanillaThro) null))
              .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate((VanillaThro) null,
                getUnchecked(3),
                getUnchecked(5),
                getUnchecked(1))).isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(getUnchecked(3),
                getUnchecked(5),
                getUnchecked(1),
                null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSingleThro() throws Exception
    {
        final Predicate<State[]> sequence = new ThroCombinationPredicate(getUnchecked(3));
        final Predicate<State[]> sequenceNegated = sequence.negate();
        final Predicate<State[]> loop = new LoopCheckingThrowCombinationPredicate(getUnchecked(3));
        final Predicate<State[]> loopNegated = loop.negate();

        softly.assertThat(sequence.test(seq("3"))).isTrue();
        softly.assertThat(sequence.test(seq("53"))).isTrue();
        softly.assertThat(sequence.test(seq("441355500"))).isTrue();
        softly.assertThat(sequence.test(seq("4413"))).isTrue();
        softly.assertThat(sequence.test(seq("531"))).isTrue();
        softly.assertThat(sequence.test(seq("3"))).isTrue();

        softly.assertThat(sequenceNegated.test(seq("3"))).isFalse();
        softly.assertThat(sequenceNegated.test(seq("53"))).isFalse();
        softly.assertThat(sequenceNegated.test(seq("441355500"))).isFalse();
        softly.assertThat(sequenceNegated.test(seq("4413"))).isFalse();
        softly.assertThat(sequenceNegated.test(seq("531"))).isFalse();
        softly.assertThat(sequenceNegated.test(seq("3"))).isFalse();

        softly.assertThat(sequence.test(seq("441"))).isFalse();
        softly.assertThat(sequence.test(seq("552"))).isFalse();
        softly.assertThat(sequence.test(seq("51"))).isFalse();

        softly.assertThat(sequenceNegated.test(seq("441"))).isTrue();
        softly.assertThat(sequenceNegated.test(seq("552"))).isTrue();
        softly.assertThat(sequenceNegated.test(seq("51"))).isTrue();

        softly.assertThat(loop.test(loop("3"))).isTrue();
        softly.assertThat(loop.test(loop("441355500"))).isTrue();
        softly.assertThat(loop.test(loop("4413"))).isTrue();
        softly.assertThat(loop.test(loop("531"))).isTrue();
        softly.assertThat(loop.test(loop("3"))).isTrue();

        softly.assertThat(loopNegated.test(loop("3"))).isFalse();
        softly.assertThat(loopNegated.test(loop("441355500"))).isFalse();
        softly.assertThat(loopNegated.test(loop("4413"))).isFalse();
        softly.assertThat(loopNegated.test(loop("531"))).isFalse();
        softly.assertThat(loopNegated.test(loop("3"))).isFalse();

        softly.assertThat(loop.test(loop("441"))).isFalse();
        softly.assertThat(loop.test(loop("51"))).isFalse();

        softly.assertThat(loopNegated.test(loop("441"))).isTrue();
        softly.assertThat(loopNegated.test(loop("51"))).isTrue();
    }

    @Test
    public void testPairOfThrows() throws Exception
    {
        final Predicate<State[]> sequence = new ThroCombinationPredicate(stringToVanillaThrowArray("42"));
        final Predicate<State[]> sequenceNegated = sequence.negate();
        final Predicate<State[]> loop = new LoopCheckingThrowCombinationPredicate(stringToVanillaThrowArray("42"));
        final Predicate<State[]> loopNegated = loop.negate();

        softly.assertThat(sequence.test(seq("342"))).isTrue();
        softly.assertThat(sequence.test(seq("42"))).isTrue();
        softly.assertThat(sequence.test(seq("5314242531"))).isTrue();

        softly.assertThat(sequenceNegated.test(seq("342"))).isFalse();
        softly.assertThat(sequenceNegated.test(seq("42"))).isFalse();
        softly.assertThat(sequenceNegated.test(seq("5314242531"))).isFalse();

        softly.assertThat(loop.test(loop("342"))).isTrue();
        softly.assertThat(loop.test(loop("24"))).isTrue();
    }

    @Test
    public void testSequenceWithNulls() throws Exception
    {
        final Predicate<State[]> sequence = new ThroCombinationPredicate(getUnchecked(5),
                null,
                getUnchecked(1));

        final Predicate<State[]> sequenceNegated = sequence.negate();
        final Predicate<State[]> loop = new LoopCheckingThrowCombinationPredicate(getUnchecked(5),
                null,
                getUnchecked(1));

        final Predicate<State[]> loopNegated = loop.negate();

        softly.assertThat(sequence.test(seq("531"))).as("531").isTrue();
        softly.assertThat(sequence.test(seq("333531"))).as("333531").isTrue();

        softly.assertThat(sequenceNegated.test(seq("531"))).as("531").isFalse();
        softly.assertThat(sequenceNegated.test(seq("333531"))).as("333531").isFalse();

        softly.assertThat(loop.test(loop("315"))).as("315").isTrue();
        softly.assertThat(loop.test(loop("333531"))).as("333531").isTrue();

        softly.assertThat(loopNegated.test(loop("315"))).as("315").isFalse();
        softly.assertThat(loopNegated.test(loop("333531"))).as("333531").isFalse();
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