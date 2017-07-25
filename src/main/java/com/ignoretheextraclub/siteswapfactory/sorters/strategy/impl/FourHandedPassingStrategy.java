package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NoTransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

/**
 Created by caspar on 10/12/16.
 */
public class FourHandedPassingStrategy implements SortingStrategy<VanillaState>
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
    public boolean takeFirst(final VanillaState[] first, final VanillaState[] second) throws InvalidSiteswapException
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
    }

    private int scoreRotation(final VanillaState[] states) throws InvalidSiteswapException
    {
        try
        {
            int score = 0;
            for (int i = 0; i < states.length; i++)
            {
                final int thro = ((VanillaThro) states[i].getThrow(states[(i + 1) % states.length])).getNumBeats();
                score += (states.length - i) * (thro + (thro % 2));
            }
            return score;
        }
        catch (final NoTransitionException cause)
        {
            throw new InvalidSiteswapException("Expected to score a valid siteswap, could not transition", cause);
        }
    }
}
