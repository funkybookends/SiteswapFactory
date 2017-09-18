package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.lang.reflect.Array;
import java.util.function.Function;

/**
 * Converts an array of states to an array of throws that are used to transition between them. The resulting array will
 * be one element shorter, and therefore at least two states are needed.
 * @author Caspar Nonclercq
 * @see StatesToThrosConverter if the states should loop.
 */
public class StatesToSequenceConverter implements Function<State[], Thro[]>
{
    public static StatesToSequenceConverter INSTANCE;

    private StatesToSequenceConverter()
    {
        // Singleton
    }

    public static StatesToSequenceConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StatesToSequenceConverter();
        }
        return INSTANCE;
    }

    /**
     * Convert the states into the Thros that transition them.
     * @param states The array of states
     * @return The array of thros.
     */
    @Override
    public Thro[] apply(final State[] states)
    {
        try
        {
            if (states.length < 2)
            {
                throw new IllegalArgumentException("Need at least 2 states");
            }

            final Thro first = states[0].getThrow(states[1 % states.length]);

            final Thro[] thros = (Thro[]) Array.newInstance(first.getClass(), states.length - 1);

            thros[0] = first;

            for (int i = 1; i < states.length - 1; i++)
            {
                thros[i] = states[i].getThrow(states[i + 1]);
            }

            return thros;
        }
        catch (final TransitionException cause)
        {
            throw new InvalidSiteswapException(cause);
        }
    }
}
