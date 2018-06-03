package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.ignoretheextraclub.siteswapfactory.generator.sequence.GeneralPathFinder;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

public class GeneralPathFinderImpl implements GeneralPathFinder
{
	private final int startingDepth;
	private final int maxDepth;
	private final State startingState;
	private final Predicate<GeneralPath> predicate;

	public GeneralPathFinderImpl(final int startingDepth,
	                             final int maxDepth,
	                             final State startingState,
	                             final Predicate<GeneralPath> predicate)
	{
		this.predicate = predicate;
		this.maxDepth = maxDepth;
		this.startingState = startingState;
		this.startingDepth = startingDepth;
	}

	/**
	 * Create a new stream with the contents of the path
	 * @return
	 */
	@Override
	public Stream<GeneralPath> stream()
	{
		return StreamSupport.stream(spliterator(), true);
	}

	@Override
	public Iterator<GeneralPath> iterator()
	{
		return new GeneralPathIterator(startingDepth, maxDepth, startingState, predicate);
	}

	@Override
	public Spliterator<GeneralPath> spliterator()
	{
		return new GeneralPathSpliterator(startingDepth, maxDepth, startingState, predicate);
	}
}
