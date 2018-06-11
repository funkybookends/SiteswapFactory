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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;

/**
 * Created by caspar on 26/07/17.
 */
public class TwoHandedVanillaSiteswap extends VanillaSiteswap
{
    private static final String TYPE = "Two Handed Siteswap";

    private static final int NUM_JUGGLERS = 1;
    private static final int NUM_HANDS = 2;

    public TwoHandedVanillaSiteswap(final GeneralCircuit states)
    {
        super(states);
    }

    @Override
    public int getNumJugglers()
    {
        return NUM_JUGGLERS;
    }

    @Override
    public int getNumHands()
    {
        return NUM_HANDS;
    }

    @Override
    public String getType()
    {
        return TYPE;
    }
}
