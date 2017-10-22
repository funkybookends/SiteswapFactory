package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.StateJoiner;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * This implementation uses the injected {@link RouteSearcher} to find the routes between the state arrays.
 *
 * @author Caspar Nonclercq
 */
public class SimpleStateJoiner implements StateJoiner
{
    private final RouteSearcher routeSearcher;

    public SimpleStateJoiner(final RouteSearcher routeSearcher)
    {
        this.routeSearcher = routeSearcher;
    }

    @Override
    public State[] join(final State[] first, final State[] second) throws TransitionException
    {
        if (ArrayUtils.isEmpty(first) || ArrayUtils.isEmpty(second))
        {
            throw new IllegalArgumentException("first and second cannot be empty");
        }

        try
        {
            final State[] firstToSecondTransition = extractTransitionStates(routeSearcher.findRoute(first[first.length - 1], second[0]));
            final State[] secondToFirstTransition = extractTransitionStates(routeSearcher.findRoute(second[second.length - 1], first[0]));
            return (State[]) joinAll(first, firstToSecondTransition, second, secondToFirstTransition);
        }
        catch (final TransitionException cause)
        {
            throw new TransitionException("Could not join " + Arrays.toString(first) + " and " + Arrays.toString(second), cause);
        }

    }

    /**
     * Returns just the required transitional states.
     *
     * @param route of the route
     * @return the transitional states
     */
    private State[] extractTransitionStates(final State[] route)
    {
        if (route.length <= 2) // Then no transitional states are needed.
        {
            return new State[0];
        }
        else
        {
            final State[] states = new State[route.length - 2];
            System.arraycopy(route, 1, states, 0, states.length);
            return states;
        }
    }

    private Object[] joinAll(final Object[]... arrays)
    {
        return Stream.of(arrays).reduce(ArrayUtils::addAll).orElse(new Object[0]);
    }
}
