package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import java.util.function.Predicate;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToAllStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.compound.VanillaThrosToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.LoopCheckingThrowCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
 Created by caspar on 02/08/17.
 */
public class ThrowCombinationPredicateTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testThatFirstAndLastElementsMustNotBeNull() throws Exception
    {
        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(null, get(3)))
              .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(get(3), null))
              .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate((VanillaThro) null))
              .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate((VanillaThro) null,
                get(3),
                get(5),
                get(1))).isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(get(3),
                get(5),
                get(1),
                null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSingleThro() throws Exception
    {
        final Predicate<State[]> sequence = new ThroCombinationPredicate(get(3));
        final Predicate<State[]> sequenceNegated = sequence.negate();
        final Predicate<State[]> loop = new LoopCheckingThrowCombinationPredicate(get(3));
        final Predicate<State[]> loopNegated = loop.negate();

        softly.assertThat(sequence.test(seq("33"))).isTrue();
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
        final StringToVanillaThrosConverter converter = StringToVanillaThrosConverter.get();
        final Predicate<State[]> sequence = new ThroCombinationPredicate(converter.apply("42"));
        final Predicate<State[]> sequenceNegated = sequence.negate();
        final Predicate<State[]> loop = new LoopCheckingThrowCombinationPredicate(converter.apply("42"));
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
        final Predicate<State[]> sequence = new ThroCombinationPredicate(get(5),
                null,
                get(1));

        final Predicate<State[]> sequenceNegated = sequence.negate();
        final Predicate<State[]> loop = new LoopCheckingThrowCombinationPredicate(get(5),
                null,
                get(1));

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
        final VanillaThro[] vanillaThros = ThrosToVanillaThrosConverter.convert(StringToVanillaThrosConverter.get().apply(seq));
        final VanillaState firstState = VanillaThrosToStartingStateConverter.get().apply(vanillaThros);
        return StartingStateAndThrosToAllStatesConverter.getAllStates(firstState, vanillaThros);
    }

    private State[] loop(final String seq) throws InvalidSiteswapException
    {
        final StringToVanillaThrosConverter converter = StringToVanillaThrosConverter.get();
        final VanillaThro[] vanillaThros = converter.apply(seq);
        return VanillaThrosToVanillaStatesConverter.get().apply(vanillaThros);
    }
}