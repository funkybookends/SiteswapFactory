package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import java.util.Arrays;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToAllStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.compound.VanillaThrosToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.result.LoopCheckingThrowCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ThrowCombinationPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testThatFirstAndLastElementsMustNotBeNull() throws Exception
    {
        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(null, get(3)))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(get(3), null))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate((VanillaThro) null))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(null, get(3), get(5), get(1)))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new LoopCheckingThrowCombinationPredicate(get(3), get(5), get(1), null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Parameters
    public void testSequenceSimple(final String sequence, final String predicate, final boolean contains)
    {
        final VanillaThro[] thros = StringToVanillaThrosConverter.convert(sequence);
        final State[] states = VanillaThrosToVanillaStatesConverter.convertThrosToStates(thros);

        final ThroCombinationPredicate throCombinationPredicate = new ThroCombinationPredicate(StringToVanillaThrosConverter.convert(predicate));

        final String description = new StringBuilder().append("Sequence: ").append(sequence)
            .append(". Converted to states: ").append(Arrays.toString(states))
            .append(". Predicate: ").append(predicate)
            .toString();

        assertThat(throCombinationPredicate.test(states)).as(description).isEqualTo(contains);
        assertThat(throCombinationPredicate.negate().test(states)).isNotEqualTo(contains);
    }

    public Object parametersForTestSequenceSimple()
    {
        return new Object[]{
            new Object[]{"33", "3", true},
            new Object[]{"33", "5", false},
            new Object[]{"42", "4", true},
            new Object[]{"42", "4", true},
            new Object[]{"33333333342333333", "4", true},
            new Object[]{"42", "5", false},
            new Object[]{"33333333342333333", "2", true},
            new Object[]{"33333333342333333", "42", true},
            new Object[]{"33333333342333333", "423", true},
            new Object[]{"33333333342333333", "3423", true},
            new Object[]{"975975", "5", true},
        };
    }

    @Test
    @Parameters
    public void testSequence(final VanillaState start, final VanillaThro[] thros, final VanillaThro[] predicate, final boolean contains)
    {
        final State[] states = StartingStateAndThrosToAllStatesConverter.getAllStates(start, thros);

        final ThroCombinationPredicate throCombinationPredicate = new ThroCombinationPredicate(predicate);

        final String description = new StringBuilder().append("Sequence: ").append(Arrays.toString(thros))
            .append(". Converted to states: ").append(Arrays.toString(states))
            .append(". Predicate: ").append(Arrays.toString(predicate))
            .toString();

        assertThat(throCombinationPredicate.test(states)).as(description).isEqualTo(contains);
        assertThat(throCombinationPredicate.negate().test(states)).isNotEqualTo(contains);
    }

    public Object parametersForTestSequence()
    {
        return new Object[]{
            new Object[]{XXX__, new VanillaThro[]{get(3)}, new VanillaThro[]{get(3)}, true},
        };
    }


}