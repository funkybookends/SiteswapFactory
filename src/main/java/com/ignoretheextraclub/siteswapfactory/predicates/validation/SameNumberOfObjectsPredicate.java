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

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Tests if all states have the same number of objects
 * @author Caspar Nonclercq
 */
public class SameNumberOfObjectsPredicate implements Predicate<State[]>
{
    private static SameNumberOfObjectsPredicate instance;

    private SameNumberOfObjectsPredicate()
    {
        // Singleton
    }

    public static SameNumberOfObjectsPredicate get()
    {
        if (instance == null)
        {
            instance = new SameNumberOfObjectsPredicate();
        }
        return instance;
    }

    @Override
    public boolean test(final State[] states)
    {
        for (final State state : states)
        {
            if (state.getNumObjects() != states[0].getNumObjects())
            {
                return false;
            }
        }

        return true;
    }

    public static boolean hasSameNumberOfObjects(final State[] states)
    {
        return get().test(states);
    }
}
