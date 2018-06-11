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
 * Created by caspar on 14/09/17.
 */
public class LoopsPredicate implements Predicate<State[]>
{
    private static LoopsPredicate instance;

    private LoopsPredicate()
    {
        // Singleton
    }

    public static LoopsPredicate get()
    {
        if (instance == null)
        {
            instance = new LoopsPredicate();
        }
        return instance;
    }

    @Override
    public boolean test(final State[] states)
    {
        for (int i = 0; i < states.length; i++)
        {
            if (!states[i].canTransition(states[(i + 1) % states.length]))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean loops(final State[] states)
    {
        return get().test(states);
    }
}
