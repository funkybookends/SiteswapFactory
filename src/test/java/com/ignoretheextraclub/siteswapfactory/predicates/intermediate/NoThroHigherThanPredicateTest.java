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

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToGeneralPathConverter;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X;

@RunWith(JUnitParamsRunner.class)
public class NoThroHigherThanPredicateTest
{
    @Test
    @Parameters
    public void testApply(final Thro maxThro, final GeneralPath states, final boolean expected) throws Exception
    {
        final NoThroHigherThanPredicate noThroHigherThanPredicate = new NoThroHigherThanPredicate(maxThro);

        Assertions.assertThat(noThroHigherThanPredicate.test(states)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{VanillaThro.get(3), StartingStateAndThrosToGeneralPathConverter.getSequence(XXX__, VanillaThro.get(3)), true},
            new Object[]{VanillaThro.get(3), StartingStateAndThrosToGeneralPathConverter.getSequence(XXX__, VanillaThro.get(4)), false},
            new Object[]{VanillaThro.get(3), StartingStateAndThrosToGeneralPathConverter.getSequence(XX_X, VanillaThro.get(2)), true},
            // TODO add more
        };
    }
}