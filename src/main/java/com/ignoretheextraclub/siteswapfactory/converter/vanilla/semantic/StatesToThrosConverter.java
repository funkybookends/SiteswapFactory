package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Converts a set of states to the throws that join them, enforcing that the last throw returns to the first state.
 *
 * @author Caspar Nonclercq
 * @see StatesToSequenceConverter for a version that does not ensure the looping.
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
     *
     * @param states The set of states
     *
     * @return The throws that join them
     *
     * @throws InvalidSiteswapException if a transition is not possible.
     */
    @Override
    public Thro[] apply(final State[] states)
    {
        Objects.requireNonNull(states, "states cannot be null");

        if (states.length == 0)
        {
            throw new IllegalArgumentException("No states");
        }

        try
        {
            final Thro[] thros = new Thro[states.length];

            for (int i = 0; i < states.length; i++)
            {
                thros[i] = states[i].getThrow(states[(i + 1) % states.length]);
            }

            return thros;
        }
        catch (TransitionException cause)
        {
            throw new InvalidSiteswapException("Cannot transition between all states " + Arrays.toString(states),
                    cause);
        }
    }

    /**
     * Convenient static method to convert states to thros.
     *
     * @param states The set of states.
     *
     * @return the throws that are used to transition between all the states.
     */
    public static Thro[] getThros(final State[] states)
    {
        return get().apply(states);
    }
}
