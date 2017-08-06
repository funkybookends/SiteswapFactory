package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

/**
 Created by caspar on 10/12/16.
 */
public class HighestThrowFirstStrategy implements SortingStrategy
{
    private static final String NAME = "HighestThrowFirst";

    private static SortingStrategy instance;

    private HighestThrowFirstStrategy()
    {
    }

    public static SortingStrategy get()
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
    public boolean takeFirst(State[] first, State[] second) throws InvalidSiteswapException
    {
        try
        {
            for (int i = 0; i < first.length; i++)
            {
                Thro ftran = first[i].getThrow(first[(i + 1) % first.length]);
                Thro stran = second[i].getThrow(second[(i + 1) % second.length]);
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
    public String getDescription()
    {
        return "The default representation of siteswaps.";
    }
}
