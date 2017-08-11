package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
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
        // If one of them starts with the ground state, then that one is preferred
        if (first[0].isGroundState() && !second[0].isGroundState())
        {
            return true;
        }
        else if (!first[0].isGroundState() && second[0].isGroundState())
        {
            return false;
        } // They both begin in the ground state

        int numPasses = 0;

        // Prefer the one with the longest sequence of passes
        for (int i = 0; i < first.length; i++)
        {
            final Thro firstThrow = first[i].getThrow(first[(i + 1) % first.length]);
            final Thro secondThrow = second[i].getThrow(second[(i + 1) % second.length]);

            final boolean firstIsPass = isPass(firstThrow);
            final boolean secondIsPass = isPass(secondThrow);

            if (firstIsPass && !secondIsPass)
            {
                return true;
            }
            if (!firstIsPass && secondIsPass)
            {
                return false;
            }
            else if (!firstIsPass && !secondIsPass)
            {
                break;
            }
            else
            {
                numPasses++;
            }
        } // They both have the same number of passes to begin with

        // Prefer the one with the highest pass first.
        for (int j = 0; j < numPasses; j++)
        {
            final Thro firstThrow = first[j].getThrow(first[(j + 1) % first.length]);
            final Thro secondThrow = second[j].getThrow(second[(j + 1) % second.length]);

            final boolean firstIsPass = isPass(firstThrow);
            final boolean secondIsPass = isPass(secondThrow);

            if (!firstThrow.equals(secondThrow))
            {
                if (firstIsPass && !secondIsPass)
                {
                    return true;
                }
                else if (!firstIsPass && secondIsPass)
                {
                    return false;
                }
                else if (firstIsPass && secondIsPass)
                {
                    return firstThrow.getNumBeats() < secondThrow.getNumBeats();
                }
            }
        }

        // Prefer the one with the highest pass first.
        for (int j = numPasses; j < first.length; j++)
        {
            final Thro firstThrow = first[j].getThrow(first[(j + 1) % first.length]);
            final Thro secondThrow = second[j].getThrow(second[(j + 1) % second.length]);

            if (!firstThrow.equals(secondThrow))
            {
                return firstThrow.getNumBeats() > secondThrow.getNumBeats();
            }
        }
        return true;
    }

    private boolean isPass(final Thro firstThrow)
    {
        return firstThrow.getNumBeats() % 2 == 1;
    }
}
