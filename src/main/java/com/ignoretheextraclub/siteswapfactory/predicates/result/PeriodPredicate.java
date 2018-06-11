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
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Checks if a siteswap has a specified period, i.e. the length matches
 *
 * This is not suitable as an intermediate predicate.
 *
 * @author Caspar Nonclercq
 */
public class PeriodPredicate implements Predicate<GeneralCircuit>
{
    private final int period;

    public PeriodPredicate(final int period)
    {
        if (period < 0)
        {
            throw new IllegalArgumentException("period cannot be less than 0");
        }
        this.period = period;
    }

    @Override
    public boolean test(final GeneralCircuit generalCircuit)
    {
        return generalCircuit.size() == period;
    }

    public boolean test(final State[] states)
    {
        return states.length == period;
    }

    public boolean test(final Siteswap siteswap)
    {
        return test(siteswap.getStates());
    }

    /**
     * Creates a {@link Predicate<GeneralCircuit>} that will return true if the given {@code State[]} is any of the provided
     * periods.
     * @param periods The allowed periods.
     * @return A predicate that will allow any of the periods.
     */
    public static Predicate<GeneralCircuit> anyOf(final int... periods)
    {
        if (periods.length == 0)
        {
            throw new IllegalArgumentException("No periods provided");
        }

        Predicate<GeneralCircuit> predicate = new PeriodPredicate(periods[0]);

        if (periods.length > 1)
        {
            for (int i = 1; i < periods.length; i++)
            {
                predicate = predicate.or(new PeriodPredicate(periods[i]));
            }
        }

        return predicate;
    }

    public static Predicate<GeneralCircuit> noneOf(final int... periods)
    {
        return anyOf(periods).negate();
    }
}
