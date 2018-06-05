package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

public class GeneralPathSpliterator implements Spliterator<GeneralPath>
{
	private static final Logger LOG = LoggerFactory.getLogger(GeneralPathSpliterator.class);

	private final Predicate<GeneralPath> predicate;
	private final State startingState;
	private final int startingDepth;

	private final GeneralPathIterator generalPathIterator;

	public GeneralPathSpliterator(final int startingDepth,
	                              final int maxDepth,
	                              final State startingState,
	                              final Predicate<GeneralPath> predicate)
	{
		this.predicate = predicate;
		this.startingState = startingState;
		this.startingDepth = startingDepth;

		generalPathIterator = new GeneralPathIterator(startingDepth, maxDepth, startingState, predicate);
	}

	@Override
	public boolean tryAdvance(final Consumer<? super GeneralPath> action)
	{
		if (generalPathIterator.hasNext())
		{
			action.accept(generalPathIterator.next());
			return true;
		}
		return false;
	}

	@Override
	public Spliterator<GeneralPath> trySplit()
	{
		final int maxDepth = generalPathIterator.getMaxDepth();

		if (startingDepth == maxDepth)
		{
			LOG.info("Cannot split startingDepth==maxDepth");
			return null;
		}

		if (generalPathIterator.getCurrentTargetDepth() == maxDepth)
		{
			LOG.info("Cannot split generalPathIterator.getCurrentTargetDepth() == maxDepth");
			return null;
		}

		final int otherStartDepth = generalPathIterator.getCurrentTargetDepth() + 1;

		generalPathIterator.setMaxDepth(generalPathIterator.getCurrentTargetDepth());

		LOG.info("Splitting: otherStartDepth={}, maxDepth={}", otherStartDepth, maxDepth);
		return new GeneralPathSpliterator(otherStartDepth, maxDepth, startingState, predicate);
	}

	@Override
	public long estimateSize()
	{
		final int branchingFactorEstimate = 30;
		final int depths = generalPathIterator.getMaxDepth();
		final long estimate = (long) Math.pow(branchingFactorEstimate, depths);
		LOG.info("Estimating size {} = {}^{} | startingDepth={}, maxdepth={}", estimate, branchingFactorEstimate, depths, startingDepth, generalPathIterator.getMaxDepth());
		return estimate;
	}

	@Override
	public int characteristics()
	{
		return DISTINCT | NONNULL | IMMUTABLE;
	}


}
