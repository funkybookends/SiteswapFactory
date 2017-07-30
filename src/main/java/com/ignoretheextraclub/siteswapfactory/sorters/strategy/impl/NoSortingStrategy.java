package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

/**
 Created by caspar on 25/06/17.
 */
public class NoSortingStrategy<State> implements SortingStrategy<State>
{
    @Override
    public String getName()
    {
        return "No Sorting strategy";
    }

    @Override
    public boolean takeFirst(final State[] first, final State[] second) throws InvalidSiteswapException
    {
        return true;
    }

    @Override
    public String getDescription()
    {
        return "The siteswap as given.";
    }
}
