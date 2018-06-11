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

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;

import static com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToVanillaThrosConverter.intsToVanillaThros;

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
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros(3)))).isTrue();
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros(3, 3)))).isTrue();
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros()))).isFalse();
        softly.assertThat(PeriodPredicate.anyOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros(3, 3, 3)))).isFalse();
    }

    @Test
    public void testNoneOf() throws Exception
    {
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros(3)))).isFalse();
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros(3, 3)))).isFalse();
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros()))).isTrue();
        softly.assertThat(PeriodPredicate.noneOf(1,2).test(new GeneralCircuit(StateTestUtils.XXX, intsToVanillaThros(3, 3, 3)))).isTrue();
    }

    // TODO add more
}