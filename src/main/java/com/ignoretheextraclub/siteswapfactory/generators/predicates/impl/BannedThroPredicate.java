package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.IntermediateStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 Created by caspar on 30/07/17.
 */
public class BannedThroPredicate implements IntermediateStatePredicate
{
    private final Thro bannedThro;

    public BannedThroPredicate(final Thro bannedThro)
    {
        this.bannedThro = bannedThro;
    }

    @Override
    public boolean test(final State[] states)
    {
        try
        {
            return states.length < 2 || !states[states.length - 1].getThrow(states[states.length])
                                                                  .equals(bannedThro);
        }
        catch (TransitionException e)
        {
            throw new IllegalStateException("Asked to verify illegal transition");
        }
    }
}
