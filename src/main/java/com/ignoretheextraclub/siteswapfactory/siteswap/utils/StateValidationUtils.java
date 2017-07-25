package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 Created by caspar on 25/07/17.
 */
public final class StateValidationUtils
{
    private StateValidationUtils(){}

    /**
     Ensures that all vanillaStates have an equal number of objects
     @throws InvalidSiteswapException
     @param states
     */
    public static void validateAllStatesHaveTheSameNumberOfObjects(final State[] states) throws InvalidSiteswapException
    {
        for (final State state : states)
        {
            if (state.getNumObjects() != states[0].getNumObjects())
            {
                throw new InvalidSiteswapException("All vanillaStates in sequence must have the same number of objects");
            }
        }
    }

    /**
     Ensures that a sequence of vanillaStates connect, and loop
     @param states
     @param thros
     @throws InvalidSiteswapException
     */
    public static void validateAllStatesConnect(final State[] states, final Thro[] thros) throws InvalidSiteswapException
    {
        for (int i = 0; i < states.length; i++)
        {
            try
            {
                if (!states[i].thro(thros[i]).equals(states[(i + 1) % states.length]))
                {
                    throw new InvalidSiteswapException("States do not all connect via throws."); // TODO improve description
                }
            }
            catch (final BadThrowException badThrowException)
            {
                throw new InvalidSiteswapException("Could not validate all vanillaStates connected", badThrowException);
            }
        }
    }
}
