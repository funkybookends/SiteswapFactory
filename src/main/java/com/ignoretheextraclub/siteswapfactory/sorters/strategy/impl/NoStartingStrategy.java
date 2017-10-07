package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

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
    public String getName()
    {
        return "No Sorting strategy";
    }

    @Override
    public boolean test(final State[] first, final State[] second) throws InvalidSiteswapException
    {
        return true;
    }

    @Override
    public String getDescription(final Locale locale)
    {
        return "The siteswap as given.";
    }
}
