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
