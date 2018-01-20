package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Determines all the states, given a starting state and a sequence of thros.
 *
 * @author Caspar Nonclercq
 */
public class StartingStateAndThrosToGeneralPathConverter implements BiFunction<State, Thro[], GeneralPath>
{
    public static StartingStateAndThrosToGeneralPathConverter INSTANCE;

    private StartingStateAndThrosToGeneralPathConverter()
    {
        // Singleton
    }

    public static StartingStateAndThrosToGeneralPathConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StartingStateAndThrosToGeneralPathConverter();
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
    public GeneralPath apply(final State startingState, final Thro[] thros)
    {
        Objects.requireNonNull(startingState, "startingState cannot be null");
        Objects.requireNonNull(thros, "thros cannot be null");

        final GeneralPath generalPath = new GeneralPath(startingState);

        for (final Thro thro : thros)
        {
            generalPath.push(thro);
        }

        return generalPath;
    }

    /**
     * Conveneint static method to get a state sequence.
     *
     * @param startingState The first state
     * @param thros         The sequence of throws
     * @return The sequence of states
     */
    public static GeneralPath getSequence(final State startingState, final Thro... thros)
    {
        return get().apply(startingState, thros);
    }
}
