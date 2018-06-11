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

package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.sorters.StartingStrategy;

/**
 Created by caspar on 25/06/17.
 */
public class NoStartingStrategy implements StartingStrategy
{
    private static final NoStartingStrategy INSTANCE = new NoStartingStrategy();

    public static NoStartingStrategy get()
    {
        return INSTANCE;
    }

    private NoStartingStrategy(){};

    @Override
    public boolean test(final GeneralCircuit first, final GeneralCircuit second)
    {
        return true;
    }

    @Override
    public String getName()
    {
        return "No Sorting strategy";
    }

    @Override
    public String getDescription(final Locale locale)
    {
        return "The siteswap as given.";
    }

    @Override
    public GeneralCircuit apply(final GeneralCircuit generalCircuit)
    {
        return generalCircuit;
    }
}
