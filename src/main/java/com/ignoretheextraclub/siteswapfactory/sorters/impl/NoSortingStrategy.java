package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

/**
 Created by caspar on 25/06/17.
 */
public class NoSortingStrategy<Throw extends AbstractThro, State extends AbstractState<Throw>> implements StateSorter<Throw, State>
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
