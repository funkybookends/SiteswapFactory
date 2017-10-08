package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Determines all the states, given a starting state and a set of throws. Ensures that the last state transitions to the
 * first. Although it should be noted that this does not mean that this ensures it is a valid siteswap.
 *
 * @author Caspar Nonclercq
 * @see StatesToSequenceConverter A similar converter that does not enforce the looping constraint.
 */
public class StartingStateAndThrosToAllStatesConverter implements BiFunction<State, Thro[], State[]>
{
    public static StartingStateAndThrosToAllStatesConverter INSTANCE;

    private StartingStateAndThrosToAllStatesConverter()
    {
        // Singleton
    }

    public static StartingStateAndThrosToAllStatesConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StartingStateAndThrosToAllStatesConverter();
        }
        return INSTANCE;
    }

    /**
     * Gets the all states given the starting state and an array of throws.
     * <p>
     * Note: This does not guarantee that the state array is a valid siteswap.
     *
     * @param startingState The starting state.
     * @param thros         The array of Thros
     *
     * @return An array of states.
     *
     * @throws InvalidSiteswapException If the throws do not make a valid siteswap
     * @see #getAllStates(State, Thro[]) A convenient static method
     */
    @Override
    public State[] apply(final State startingState, final Thro[] thros)
    {
        Objects.requireNonNull(startingState, "startingState cannot be null");
        Objects.requireNonNull(thros, "thros cannot be null");

        if (thros.length < 1)
        {
            throw new IllegalArgumentException("thros must have at least one throw");
        }

        try
        {
            final State[] states = new State[thros.length];

            states[0] = startingState;

            for (int i = 0; i < thros.length - 1; i++)
            {
                states[i + 1] = states[i].thro(thros[i]);
            }

            if (!states[states.length - 1].canTransition(states[0]))
            {
                throw new TransitionException("No transition from final state [" + states[states.length - 1] + "] to first state [" + states[0] + "]");
            }

            return states;
        }
        catch (BadThrowException | TransitionException cause)
        {
            throw new InvalidSiteswapException("Could not create valid siteswap using starting state [" + startingState.toString() + "] " +
                    "and throws" + Arrays.toString(thros), cause);
        }
    }

    /**
     * Convenient static method to get all the states given the starting state and an array of throws.
     *
     * @param startingState The starting state.
     * @param thros         The array of Thros
     *
     * @return An array of states.
     *
     * @throws InvalidSiteswapException If the throws do not make a valid siteswap
     */
    public static State[] getAllStates(final State startingState, final Thro[] thros)
    {
        return get().apply(startingState, thros);
    }
}
