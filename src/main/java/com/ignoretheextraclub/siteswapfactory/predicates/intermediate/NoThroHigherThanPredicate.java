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

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Ensures that the state array contains no {@link com.ignoretheextraclub.siteswapfactory.siteswap.Thro} larger
 * than the provided thro.
 *
 * @author Caspar Nonclercq
 */
public class NoThroHigherThanPredicate implements Predicate<GeneralPath>
{
    private final Thro maxThro;

    public NoThroHigherThanPredicate(final Thro maxThro)
    {
        this.maxThro = maxThro;
    }

    @Override
    public boolean test(final GeneralPath generalPath)
    {
        for (final Thro thro : generalPath)
        {
            if (thro.compareTo(maxThro) > 0)
            {
                return false;
            }
        }

        return true;
    }
}
