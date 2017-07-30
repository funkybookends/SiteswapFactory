package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 Created by caspar on 25/07/17.
 */
public final class StateValidationUtils
{
    private StateValidationUtils()
    {
    }

    /**
     Ensures that all vanillaStates have an equal number of objects

     @param states
     */
    public static void validateAllStatesHaveTheSameNumberOfObjects(final State[] states) throws NumObjectsException
    {
        for (final State state : states)
        {
            if (state.getNumObjects() != states[0].getNumObjects())
            {
                throw new NumObjectsException("Not all states have the same number of objects");
            }
        }
    }

    /**
     Ensures that a sequence of vanillaStates connect, and loop

     @param states
     @param thros
     */
    public static void validateAllStatesConnect(final State[] states,
                                                final Thro[] thros) throws TransitionException, BadThrowException
    {
        for (int i = 0; i < states.length; i++)
        {
            if (!states[i].thro(thros[i]).equals(states[(i + 1) % states.length]))
            {
                throw new TransitionException("States do not all connect via throws.");
            }
        }
    }
}
