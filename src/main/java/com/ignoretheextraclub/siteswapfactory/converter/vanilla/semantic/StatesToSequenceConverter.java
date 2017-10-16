package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Converts an array of states to an array of throws that are used to transition between them. The resulting array will
 * be one element shorter, and therefore at least two states are needed.
 *
 * @author Caspar Nonclercq
 * @see StatesToThrosConverter A version that ensures the state array loops
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
     *
     * @param states The array of states, at least two in length
     *
     * @return The array of thros.
     */
    @Override
    public Thro[] apply(final State[] states)
    {
        Objects.requireNonNull(states, "states cannot be null");

        if (states.length < 2)
        {
            throw new IllegalArgumentException("Need at least 2 states");
        }

        try
        {
            final Thro[] thros = new Thro[states.length - 1];

            for (int i = 0; i < states.length - 1; i++)
            {

                thros[i] = states[i].getThrow(states[i + 1]);
            }

            return thros;
        }
        catch (final TransitionException cause)
        {
            throw new InvalidSiteswapException("States [" + Arrays.toString(states) + "] cannot transition", cause);
        }
    }

    /**
     * Convenient static method to get the sequence.
     *
     * @param states The states, at least 2 in length.
     *
     * @return The throws between the states.
     */
    public static Thro[] getSequence(final State[] states)
    {
        return get().apply(states);
    }
}