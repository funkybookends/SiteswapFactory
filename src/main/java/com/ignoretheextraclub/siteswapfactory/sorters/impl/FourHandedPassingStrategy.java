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

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.StartingStrategy;

/**
 Created by caspar on 10/12/16.
 */
public class FourHandedPassingStrategy implements StartingStrategy
{
    private static final String NAME = "FourHandedPassing";

    private static FourHandedPassingStrategy instance;

    private FourHandedPassingStrategy()
    {
    }

    public static StartingStrategy get()
    {
        if (instance == null)
        {
            instance = new FourHandedPassingStrategy();
        }
        return instance;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public String getDescription(final Locale locale)
    {
        return "The best starting position for club passing.";
    }

    @Override
    public boolean test(final GeneralCircuit first, final GeneralCircuit second) throws InvalidSiteswapException
    {
        final int firstScore = scoreRotation(first.getAllStates());
        final int secondScore = scoreRotation(second.getAllStates());

        if (firstScore == secondScore)
        {
            return HighestThrowFirstStrategy.get().test(first, second);
        }
        return firstScore < secondScore;
    }

    private int scoreRotation(final State[] states) throws InvalidSiteswapException
    {
        int score = 0; // Higher is better
        for (int i = 0; i < states.length; i++)
        {
            score -= states[i].excitedness() * i*i;
            score -= states[i].getThrow(states[(i+1)%states.length]).getNumBeats() * i;
        }
        return score;
    }
}
