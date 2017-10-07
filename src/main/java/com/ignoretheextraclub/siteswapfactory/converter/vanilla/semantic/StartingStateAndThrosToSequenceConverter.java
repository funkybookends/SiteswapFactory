package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.function.BiFunction;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Determines all the states, given a starting state and a sequence of thros.
 *
 * @author Caspar Nonclercq
 */
public class StartingStateAndThrosToSequenceConverter implements BiFunction<State, Thro[], State[]>
{
    public static StartingStateAndThrosToSequenceConverter INSTANCE;

    private StartingStateAndThrosToSequenceConverter()
    {
        // Singleton
    }

    public static StartingStateAndThrosToSequenceConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StartingStateAndThrosToSequenceConverter();
        }
        return INSTANCE;
    }

    /**
     * Creates an array of states throwing the throws in order from the starting state.
     *
     * @param startingState The first state to throw from
     * @param thros         The thro sequnce
     * @return The array of states
     */
    @Override
    public State[] apply(final State startingState, final Thro[] thros)
    {
        final State[] states = new State[thros.length + 1];

        states[0] = startingState;

        try
        {
            for (int i = 0; i < thros.length; i++)
            {
                states[i + 1] = states[i].thro(thros[i]);
            }
        }
        catch (final BadThrowException cause)
        {
            throw new BadThrowException("Sequence " + Arrays.toString(thros) + " was not valid from " + startingState, cause);
        }

        return states;
    }

    /**
     * Conveneint static method to get a state sequence.
     *
     * @param startingState The first state
     * @param thros         The sequence of throws
     * @return The sequence of states
     */
    public static State[] getSequence(final State startingState, final Thro[] thros)
    {
        return get().apply(startingState, thros);
    }
}
