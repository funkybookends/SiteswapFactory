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

import java.util.Collections;
import java.util.HashSet;
import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Checks if a {@link State[]} is prime.
 *
 * A {@link State}[] is prime if no state is contained twice.
 *
 * This is an Intermediate Predicate or a Result Predicate
 *
 * @author Caspar Nonclercq
 */
public class PrimePredicate implements Predicate<GeneralPath>
{
    private static PrimePredicate instance;

    private PrimePredicate()
    {
        // To enforce singleton instance
    }

    @Override
    public boolean test(final GeneralPath generalPath)
    {
        return test(generalPath.getStates());
    }

    public boolean test(final State[] stateArray)
    {
        final HashSet<Object> states = new HashSet<>();
        Collections.addAll(states, stateArray);
        return states.size() == stateArray.length;
    }

    public static PrimePredicate get()
    {
        if (instance == null)
        {
            instance = new PrimePredicate();
        }
        return instance;
    }

    public static boolean isPrime(final State[] states)
    {
        return get().test(states);
    }

    public static boolean isPrime(final Siteswap siteswap)
    {
        return get().test(siteswap.getStates());
    }
}
