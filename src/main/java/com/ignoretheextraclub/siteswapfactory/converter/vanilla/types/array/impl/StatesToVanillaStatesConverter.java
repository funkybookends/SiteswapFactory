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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

/**
 * Downcasts a State[] to a VanillaState[]
 *
 * @author Caspar Nonclercq
 */
public class StatesToVanillaStatesConverter implements Function<State[], VanillaState[]>
{
    private static StatesToVanillaStatesConverter INSTANCE;

    private StatesToVanillaStatesConverter()
    {
        // Singleton
    }

    public static StatesToVanillaStatesConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StatesToVanillaStatesConverter();
        }
        return INSTANCE;
    }

    /**
     * Downcasts a State[] to a VanillaState[]
     *
     * @param states A State array
     *
     * @return A VanillaState array
     */
    @Override
    public VanillaState[] apply(final State[] states)
    {
        Objects.requireNonNull(states, "states cannot be null");

        final VanillaState[] result = new VanillaState[states.length];

        for (int i = 0; i < states.length; i++)
        {
            result[i] = (VanillaState) states[i];
        }

        return result;
    }

    /**
     * Convenient static method to cast a state[] down to a VanillaState[]
     *
     * @param states A state array
     *
     * @return A VanillaState array
     */
    public static VanillaState[] convert(final State[] states)
    {
        return get().apply(states);
    }
}
