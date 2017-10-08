package com.ignoretheextraclub.siteswapfactory.predicates.validation;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_X_X;

/**
 * Created by caspar on 24/09/17.
 */
public class LoopsPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void name() throws Exception
    {
        softly.assertThat(LoopsPredicate.loops(new State[]{XXX__})).isTrue();
        softly.assertThat(LoopsPredicate.loops(new State[]{X_X_X, XX_X_})).isTrue();
        softly.assertThat(LoopsPredicate.loops(new State[]{X_X_X, XX_X_, XXX__})).isFalse();
    }
}