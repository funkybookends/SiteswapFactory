package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_X_X;

/**
 Created by caspar on 29/07/17.
 */
public class NoStartingStrategyTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void test() throws Exception
    {
        softly.assertThat(NoStartingStrategy.get().test(new State[]{XXX__}, new State[]{X_X_X})).isTrue();
    }

}