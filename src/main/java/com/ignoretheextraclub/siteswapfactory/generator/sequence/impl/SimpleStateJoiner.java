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

package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.StateJoiner;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * This implementation uses the injected {@link RouteSearcher} to find the routes between the state arrays.
 *
 * @author Caspar Nonclercq
 */
public class SimpleStateJoiner implements StateJoiner
{
	private static final Logger LOG = LoggerFactory.getLogger(SimpleStateJoiner.class);

	private final RouteSearcher routeSearcher;

	public SimpleStateJoiner(final RouteSearcher routeSearcher)
	{
		this.routeSearcher = routeSearcher;
	}

	@Override
	public GeneralPath connectPath(final GeneralPath first, final GeneralPath second) throws TransitionException
	{
		Objects.requireNonNull(first, "first cannot be null");
		Objects.requireNonNull(second, "second cannot be null");

		final GeneralPath result = copy(first);
		addJoin(first, second, result);
		second.forEach(result::push);

		return result;
	}

	@Override
	public GeneralCircuit joinToCircuit(final GeneralPath first, final GeneralPath second) throws TransitionException
	{
		Objects.requireNonNull(first, "first cannot be null");
		Objects.requireNonNull(second, "second cannot be null");

		final GeneralPath result = copy(first);
		addJoin(first, second, result);
		second.forEach(result::push);
		addJoin(second, first, result);

		return result.toGeneralCircuit();
	}

	@Override
	public GeneralCircuit joinShortest(final GeneralCircuit first, final GeneralCircuit second)
	{
		Objects.requireNonNull(first, "first cannot be null");
		Objects.requireNonNull(second, "second cannot be null");

		GeneralCircuit best = null;
		TransitionException transitionException = null;

		for (final GeneralCircuit firstRotation : first.getRotationsArray())
		{
			for (final GeneralCircuit secondRotation : second.getRotationsArray())
			{
				try
				{

					final GeneralCircuit current = joinExactly(firstRotation, secondRotation);
					if (best == null || current.size() < best.size())
					{
						best = current;
					}
				}
				catch (final TransitionException nextException)
				{
					if (transitionException == null)
					{
						transitionException = nextException;
					}
					else
					{
						transitionException.addSuppressed(nextException);
					}
				}
			}
		}

		if (best == null)
		{
			if (transitionException != null)
			{
				throw transitionException;
			}
			else
			{
				throw new TransitionException("Could not find any routes");
			}
		}

		return best;
	}

	private void addJoin(final GeneralPath from, final GeneralPath to, final GeneralPath result)
	{
		if (!from.getLastState().equals(to.getStartingState()))
		{
			try
			{
				routeSearcher.findRoute(from.getLastState(), to.getStartingState()).forEach(result::push);
			}
			catch (final TransitionException cause)
			{
				throw new TransitionException("Could not join " + from.getLastState() + " to " + to.getStartingState(), cause);
			}
		}
	}

	private GeneralPath copy(final GeneralPath path)
	{
		return GeneralPath.from(path.getStartingState(), path.toArray(new Thro[path.size()]));
	}
}
