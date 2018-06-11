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
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.sorters.StartingStrategy;

/**
 Created by caspar on 10/12/16.
 */
public class HighestThrowFirstStrategy implements StartingStrategy
{
    private static final String NAME = "HighestThrowFirst";

    private static StartingStrategy instance;

    private HighestThrowFirstStrategy()
    {
    }

    public static StartingStrategy get()
    {
        if (instance == null)
        {
            instance = new HighestThrowFirstStrategy();
        }
        return instance;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean test(GeneralCircuit firstCircuit, GeneralCircuit secondCircuit) throws InvalidSiteswapException
    {
        try
        {
            for (int i = 0; i < firstCircuit.size(); i++)
            {
                Thro ftran = firstCircuit.getThros()[i];
                Thro stran = secondCircuit.getThros()[i];

                if (ftran.compareTo(stran) < 0)
                {
                    return false;
                }
                else if (ftran.compareTo(stran) > 0)
                {
                    return true;
                }
            }

            return true; //they are equivalent
        }
        catch (TransitionException e)
        {
            throw new InvalidSiteswapException("Could not determine transition", e);
        }
    }

    @Override
    public String getDescription(final Locale locale)
    {
        return "The default representation of siteswaps.";
    }
}
