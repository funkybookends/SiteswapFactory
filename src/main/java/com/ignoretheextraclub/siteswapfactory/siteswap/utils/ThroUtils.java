package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Created by caspar on 26/07/17.
 */
public final class ThroUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(ThroUtils.class);

    private ThroUtils()
    {
    }

    ;

    /**
     Will find and return the highest throw in an array of throws.

     @param thros

     @return the highest.
     */
    @SuppressWarnings("unchecked") // Thro extends Comparable
    public static <T extends Thro> T getHighestThro(T[] thros)
    {
        T highest = thros[0];
        for (int i = 1; i < thros.length; i++)
        {
            if (highest.compareTo(thros[i]) < 0)
            {
                highest = thros[i];
            }
        }
        return highest;
    }

    public static State[] getSequence(final State startingState, final Thro... thros) throws BadThrowException
    {
        final State[] states = new State[thros.length + 1];
        states[0] = startingState;

        for (int i = 0; i < thros.length; i++)
        {
            states[i + 1] = states[i].thro(thros[i]);
        }

        return states;
    }

    public static State[] getLoop(final State startingState, final Thro... thros) throws BadThrowException, TransitionException
    {
        final State[] states = new State[thros.length];
        states[0] = startingState;

        for (int i = 0; i < thros.length - 1; i++)
        {
            states[i + 1] = states[i].thro(thros[i]);
        }

        if (!states[states.length - 1].canTransition(states[0]))
        {
            LOG.warn("Cannot create loop for {} and throws {}. Created {}, but last throw does not connect to first", startingState, thros, states);
            throw new TransitionException("No transition from final state [" + states[states.length - 1] + "] to first state [" + states[0] + "]");
        }

        return states;
    }
}
