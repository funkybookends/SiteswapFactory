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

import java.util.Iterator;
import java.util.function.Predicate;

import org.apache.commons.collections4.IteratorUtils;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

public class ShortestPathRouteSearcherImpl implements RouteSearcher
{
	private final int maxPathLength;
	private final Predicate<GeneralPath> pathPredicate;

	public ShortestPathRouteSearcherImpl(final int maxPathLength,
	                                     final Predicate<GeneralPath> pathPredicate)
	{
		this.maxPathLength = maxPathLength;
		this.pathPredicate = pathPredicate;
	}

	@Override
	public GeneralPath findRoute(final State source, final State destination) throws TransitionException
	{
		final GeneralPathFinderImpl generalPathFinder = new GeneralPathFinderImpl(1, maxPathLength, source, pathPredicate);

		final Iterator<GeneralPath> iterator = IteratorUtils.filteredIterator(generalPathFinder.iterator(), lastStateEqualTo(destination));

		if (iterator.hasNext())
		{
			return iterator.next();
		}
		else
		{
			throw transitionException(source, destination);
		}
	}

	private TransitionException transitionException(final State source, final State destination)
	{
		return new TransitionException("Could not find a transition from source [" + source + "] to destination [" + destination + "]");
	}

	private org.apache.commons.collections4.Predicate<? super GeneralPath> lastStateEqualTo(final State destination)
	{
		return generalPath -> generalPath.getLastState().equals(destination);
	}
}
