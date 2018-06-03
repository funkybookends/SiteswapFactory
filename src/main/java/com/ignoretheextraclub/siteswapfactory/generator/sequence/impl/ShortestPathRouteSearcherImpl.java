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
