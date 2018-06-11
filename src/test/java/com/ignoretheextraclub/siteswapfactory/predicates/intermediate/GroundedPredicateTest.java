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

package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;

/**
 * Created by caspar on 24/09/17.
 */
public class GroundedPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void test() throws Exception
    {
        softly.assertThat(GroundedPredicate.get().test(new State[]{XXX__})).isTrue();
        softly.assertThat(GroundedPredicate.isGround(TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("3"))).isTrue();
        softly.assertThat(GroundedPredicate.isGround(TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("55500"))).as("55500").isTrue();
        softly.assertThat(GroundedPredicate.isGround(TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("55050"))).as("55050").isFalse();
        // todo add more
    }
}