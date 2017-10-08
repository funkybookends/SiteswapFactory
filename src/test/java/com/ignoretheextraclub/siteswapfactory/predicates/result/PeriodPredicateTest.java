package com.ignoretheextraclub.siteswapfactory.predicates.result;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Created by caspar on 24/09/17.
 */
public class PeriodPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testSingle() throws Exception
    {
        softly.assertThat(new PeriodPredicate(1).test(new State[1])).isTrue();
        softly.assertThat(new PeriodPredicate(1).test(new State[2])).isFalse();
        softly.assertThat(new PeriodPredicate(2).test(new State[2])).isTrue();
        softly.assertThat(new PeriodPredicate(2).test(new State[0])).isFalse();
    }

    @Test
    public void testAnyOf() throws Exception
    {
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new State[1])).isTrue();
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new State[2])).isTrue();
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new State[0])).isFalse();
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new State[3])).isFalse();
    }

    @Test
    public void testNoneOf() throws Exception
    {
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new State[1])).isFalse();
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new State[2])).isFalse();
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new State[0])).isTrue();
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new State[3])).isTrue();
    }

    // TODO add more
}