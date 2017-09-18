package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.lang.reflect.Array;
import java.util.function.Function;

/**
 * Converts a set of states to the throws that join them, enforcing that the last throw returns to the first state.
 * @see StatesToSequenceConverter for a version that does not ensure the looping.
 * @author Caspar Nonclercq
 */
public class StatesToThrosConverter implements Function<State[], Thro[]>
{
    public static StatesToThrosConverter INSTANCE;

    private StatesToThrosConverter()
    {
        // Singleton
    }

    public static StatesToThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StatesToThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Convert an array of states to the throws that join them
     * @param states The set of states
     * @return The throws that join them
     * @throws TransitionException if a transition is not available.
     */
    @Override
    public Thro[] apply(final State[] states)
    {
        try
        {
            if (states.length == 0)
            {
                throw new IllegalArgumentException("No states");
            }

            final Thro first = states[0].getThrow(states[1 % states.length]);

            final Thro[] thros = (Thro[]) Array.newInstance(first.getClass(), states.length);

            thros[0] = first;

            for (int i = 1; i < states.length - 1; i++)
            {
                thros[i] = states[i].getThrow(states[i + 1]);
            }

            thros[thros.length - 1] = states[states.length - 1].getThrow(states[0]);

            return thros;
        }
        catch (TransitionException cause)
        {
            throw new InvalidSiteswapException(cause);
        }
    }
}
