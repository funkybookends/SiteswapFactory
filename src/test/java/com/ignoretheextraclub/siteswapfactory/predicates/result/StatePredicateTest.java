/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.predicates.result;

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
public class StatePredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void test() throws Exception
    {
        softly.assertThat(new StatePredicate(XXX__).test(new State[]{XX_X_})).isFalse();
        softly.assertThat(new StatePredicate(XXX__).test(new State[]{XXX__})).isTrue();
        softly.assertThat(new StatePredicate(XXX__).test(new State[]{XX_X_, XXX__})).isTrue();
        softly.assertThat(new StatePredicate(XXX__).test(new State[]{XX_X_, X_X_X})).isFalse();
    }

    @Test
    public void testAnyOf() throws Exception
    {
        softly.assertThat(StatePredicate.anyOf(XXX__, XX_X_).test(new State[]{XXX__, X_X_X})).isTrue();
        softly.assertThat(StatePredicate.anyOf(XXX__, XX_X_).test(new State[]{XX_X_, X_X_X})).isTrue();
        softly.assertThat(StatePredicate.anyOf(XXX__, XX_X_).test(new State[]{X_X_X, X_X_X})).isFalse();
    }

    @Test
    public void testNoneOf() throws Exception
    {
        softly.assertThat(StatePredicate.noneOf(XXX__, XX_X_).test(new State[]{XXX__, X_X_X})).isFalse();
        softly.assertThat(StatePredicate.noneOf(XXX__, XX_X_).test(new State[]{XX_X_, X_X_X})).isFalse();
        softly.assertThat(StatePredicate.noneOf(XXX__, XX_X_).test(new State[]{X_X_X, X_X_X})).isTrue();
    }
}