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

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Created by caspar on 23/10/17.
 */
@RunWith(JUnitParamsRunner.class)
public class LoopCheckingNoThroHigherThanPredicateTest
{
    @Test
    @Parameters
    public void testApply(final Thro maxThro, final String siteswapConstructor, final boolean expected) throws Exception
    {
        final LoopCheckingNoThroHigherThanPredicate noThroHigherThanPredicate = new LoopCheckingNoThroHigherThanPredicate(maxThro);
        final GeneralCircuit states = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap(siteswapConstructor).getGeneralCircuit();
        Assertions.assertThat(noThroHigherThanPredicate.test(states)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{VanillaThro.get(3), "4", false},
            new Object[]{VanillaThro.get(3), "3", true},
            new Object[]{VanillaThro.get(3), "2", true},
            new Object[]{VanillaThro.get(7), "456", true},
            new Object[]{VanillaThro.get(5), "456", false},
            // TODO add more
        };
    }
}