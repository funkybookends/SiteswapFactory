/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
