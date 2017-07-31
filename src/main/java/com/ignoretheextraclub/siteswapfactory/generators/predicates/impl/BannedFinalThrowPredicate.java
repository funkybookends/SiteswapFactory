package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 Created by caspar on 31/07/17.
 */
public class BannedFinalThrowPredicate implements ReturnStatePredicate
{
    private final Thro bannedThro;

    public BannedFinalThrowPredicate(final Thro bannedThro)
    {
        this.bannedThro = bannedThro;
    }

    @Override
    public boolean test(final State[] states)
    {
        try
        {
            if (states.length < 2)
            {
                return !states[0].getThrow(states[0]).equals(bannedThro);
            }
            return !states[states.length - 2].getThrow(states[states.length - 1])
                                                                  .equals(bannedThro);
        }
        catch (TransitionException e)
        {
            return false;
        }
    }
}
