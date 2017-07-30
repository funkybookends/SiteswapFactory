package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.lang.reflect.Array;

/**
 Created by caspar on 25/07/17.
 */
public final class StateUtils
{
    private StateUtils()
    {
    }

    public static boolean containsARepeatedState(State[] states)
    {
        for (int i = 0; i < states.length; i++)
        {
            for (int j = i + 1; j < states.length; j++)
            {
                if (states[i].equals(states[j]))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsGround(State[] states)
    {
        for (State state : states)
        {
            if (state.isGroundState())
            {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <S extends State, T extends Thro> S[] getAllStates(S startingState,
                                                                     T[] thros) throws InvalidSiteswapException
    {
        try
        {
            final S[] states = (S[]) Array.newInstance(startingState.getClass(), thros.length);
            states[0] = startingState;
            for (int i = 0; i < thros.length - 1; i++)
            {
                states[i + 1] = (S) states[i].thro(thros[i]);
            }
            return states;
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Cannot construct all vanillaStates", cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static Thro[] getAllThrows(final State[] states) throws TransitionException
    {
        final Thro first = states[0].getThrow(states[1 % states.length]);
        final Thro[] thros = (Thro[]) Array.newInstance(first.getClass(), states.length);
        thros[0] = first;
        for (int i = 1; i < states.length; i++)
        {
            thros[i] = states[i].getThrow(states[(i + 1) % states.length]);
        }
        return thros;
    }
}
