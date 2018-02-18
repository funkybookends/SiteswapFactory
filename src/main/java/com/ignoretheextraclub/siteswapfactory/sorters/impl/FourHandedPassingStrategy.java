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