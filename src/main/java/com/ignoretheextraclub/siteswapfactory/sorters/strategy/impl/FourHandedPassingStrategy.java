package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

/**
 Created by caspar on 10/12/16.
 */
public class FourHandedPassingStrategy implements SortingStrategy
{
    private static final String NAME = "FourHandedPassing";

    private static FourHandedPassingStrategy instance;

    private FourHandedPassingStrategy()
    {
    }

    public static SortingStrategy get()
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
    public String getDescription()
    {
        return "The best starting position for club passing.";
    }

    @Override
    public boolean takeFirst(final State[] first, final State[] second) throws InvalidSiteswapException
    {
        return scoreRotation(first) < scoreRotation(second);
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
