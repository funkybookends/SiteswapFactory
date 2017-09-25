package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

import java.util.Objects;
import java.util.function.Function;

/**
 * Downcasts a State[] to a VanillaState[]
 *
 * @author Caspar Nonclercq
 */
public class StatesToVanillaStatesConverter implements Function<State[], VanillaState[]>
{
    public static StatesToVanillaStatesConverter INSTANCE;

    private StatesToVanillaStatesConverter()
    {
        // Singleton
    }

    public static StatesToVanillaStatesConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StatesToVanillaStatesConverter();
        }
        return INSTANCE;
    }

    /**
     * Downcasts a State[] to a VanillaState[]
     *
     * @param states A State array
     *
     * @return A VanillaState array
     */
    @Override
    public VanillaState[] apply(final State[] states)
    {
        Objects.requireNonNull(states, "states cannot be null");

        final VanillaState[] result = new VanillaState[states.length];

        for (int i = 0; i < states.length; i++)
        {
            result[i] = (VanillaState) states[i];
        }

        return result;
    }

    /**
     * Convenient static method to cast a state[] down to a VanillaState[]
     *
     * @param states A state array
     *
     * @return A VanillaState array
     */
    public static VanillaState[] convert(final State[] states)
    {
        return get().apply(states);
    }
}
