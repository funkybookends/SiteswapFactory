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