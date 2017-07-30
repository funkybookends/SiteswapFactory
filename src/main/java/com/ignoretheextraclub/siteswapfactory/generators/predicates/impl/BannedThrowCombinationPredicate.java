package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.IntermediateStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.util.Objects;

/**
 Created by caspar on 30/07/17.
 */
public class BannedThrowCombinationPredicate implements IntermediateStatePredicate
{
    private final Thro[] bannedThrowCombination;

    public BannedThrowCombinationPredicate(final Thro[] bannedThrowCombination)
    {
        Objects.requireNonNull(bannedThrowCombination[0], "The first throw must not be null");
        Objects.requireNonNull(bannedThrowCombination[bannedThrowCombination.length - 1],
                "The last throw must not be null");
        this.bannedThrowCombination = bannedThrowCombination;
    }

    @Override
    public boolean test(final State[] states)
    {
        if (states.length < bannedThrowCombination.length - 1)
        {
            return true;
        }

        try
        {
            for (int i = 0; i < bannedThrowCombination.length; i++)
            {
                final Thro bannedThrowFromEnd = bannedThrowCombination[bannedThrowCombination.length - i - 1];
                if (bannedThrowFromEnd != null && bannedThrowFromEnd.equals(getThrowFromEnd(i, states)))
                {
                    return false;
                }
            }
        }
        catch (final TransitionException cause)
        {
            throw new IllegalStateException("Could not check throw because transition is illegal", cause);
        }

        return true;
    }

    private Thro getThrowFromEnd(final int i, final State[] states) throws TransitionException
    {
        final int from = states.length - i - 1;
        return states[from].getThrow(states[from + 1]);
    }
}
