package com.ignoretheextraclub.siteswapfactory.generator.sequence;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Finds a path between the source and the destination. It is up to the implementation to define
 * the properties of the route found.
 */
public interface RouteSearcher
{
    /**
     * Find a route between the source and the destination.
     *
     * If {@code source} and {@code destination} are the same then the result will be a {@code new State[]{source}}.
     * If {@code source} can transition directly to {@code destination} then the result will be a {@code new State[]{source, destination}}.
     *
     * @param source      The first state, always {@code State[0]} in the result array.
     * @param destination The last state, always {@code State[result.length - 1]} in the result array.
     * @return A path, whos properties are defined by the implementation.
     * @throws TransitionException If there is no path, or a path could not be found.
     */
    GeneralPath findRoute(State source, State destination) throws TransitionException;
}
