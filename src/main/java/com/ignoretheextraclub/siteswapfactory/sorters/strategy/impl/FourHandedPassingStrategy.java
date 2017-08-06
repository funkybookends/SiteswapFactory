package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
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
        final int scoreFirst = scoreRotation(first);
        final int scoreSecond = scoreRotation(second);
        if (scoreFirst > scoreSecond)
        {
            return true;
        }
        if (scoreFirst < scoreSecond)
        {
            return false;
        }
        return first[0].excitedness() < second[0].excitedness();
//        for (int i = 0; i < first.length; i++)
//        {
//            if (first[i].excitedness() < second[i].excitedness())
//            {
//                return true;
//            }
//            else if (second[i].excitedness() < first[i].excitedness())
//            {
//                return false;
//            }
//        }
//        for (int i = 0; i < first.length; i++)
//        {
//            final Thro firstThro = first[i].getThrow(first[(i + 1) % first.length]);
//            final Thro seconThro = second[i].getThrow(first[(i + 1) % second.length]);
//
//            if (firstThro.getNumBeats() > seconThro.getNumBeats())
//            {
//                return true;
//            }
//            if (seconThro.getNumBeats() > firstThro.getNumBeats())
//            {
//                return false;
//            }
//        }
//        return true;
    }

    private int scoreRotation(final State[] states) throws InvalidSiteswapException
    {
        try
        {
            int score = 0;
            for (int i = 0; i < states.length; i++)
            {
                final int thro = states[i].getThrow(states[(i + 1) % states.length]).getNumBeats();
                score += (states.length - i) * (thro + (thro % 2));
            }
            return score;
        }
        catch (final TransitionException cause)
        {
            throw new InvalidSiteswapException("Expected to score a valid siteswap, could not transition", cause);
        }
    }
}
