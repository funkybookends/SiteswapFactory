package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.util.function.BiFunction;

/**
 * Determines all the states, given a starting state and a set of throws. Ensures that the last state transitions to the
 * first.
 *
 * @author Caspar Nonclercq
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
     * Gets the all the states given the starting state and an array of throws.
     * @param startingState The starting state.
     * @param thros The array of Thros
     * @return An array of states.
     * @throws TransitionException If no transition is available from the last state to the first.
     * @throws BadThrowException If a thro cannot be thrown.
     */
    @Override
    public State[] apply(final State startingState, final Thro[] thros)
    {
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
            throw new InvalidSiteswapException(cause);
        }
    }
}
