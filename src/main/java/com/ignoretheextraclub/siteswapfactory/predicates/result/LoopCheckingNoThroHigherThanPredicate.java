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

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.NoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Uses the {@link StatesToThrosConverter} to get the throws and is therefore a loop checking
 * version of {@link NoThroHigherThanPredicate}.
 *
 * @author Caspar Nonclercq
 */
public class LoopCheckingNoThroHigherThanPredicate implements Predicate<GeneralCircuit>
{
    private final Thro maxThro;

    public LoopCheckingNoThroHigherThanPredicate(final Thro maxThro)
    {
        this.maxThro = maxThro;
    }

    @Override
    public boolean test(final GeneralCircuit generalPath)
    {
        for (final Thro thro : generalPath.getThros())
        {
            if (thro.compareTo(maxThro) > 0)
            {
                return false;
            }
        }

        return true;
    }
}
