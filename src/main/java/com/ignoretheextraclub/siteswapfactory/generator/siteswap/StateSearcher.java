package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import static java.util.Collections.emptyIterator;

/**
 * The iterator underlying the siteswap generator.
 *
 * @author Caspar Nonclercq
 */
public class StateSearcher<T extends Siteswap> implements Iterator<T>, SiteswapGenerator<T>
{
	private static final Logger LOG = LoggerFactory.getLogger(StateSearcher.class);

	/**
	 * Configuration
	 */
	private final Iterator<State> startingStates;
	private final int maxPeriod;
	private final Predicate<GeneralPath> intermediatePredicate;
	private final Predicate<GeneralCircuit> resultPredicate;
	private final SiteswapRequestBuilder siteswapRequestBuilder;
	private final SiteswapConstructor<T> siteswapConstructor;

	/**
	 * Internal state
	 */
	private GeneralPathBuilder builder;
	private T next;

	/**
	 * Creates a {@link StateSearcher} which will find successive {@link Siteswap}s based on the provided configuration.
	 *
	 * @param startingStates        The list of starting states which are acceptable.
	 * @param maxPeriod             The maximum period of {@link Siteswap}. To restrict to a specific period, include an {@code resultPredicate} requiring this period. This should be set accurately to ensure good performance.
	 * @param intermediatePredicate A predicate that will be tested after each addition of a new {@link State} to the internal State[]. If the intermediate state fails, then the last state will be removed, and the next state will be found.
	 * @param resultPredicate       A predicate that will be tested prior to attempting to construct a {@link Siteswap}. If it fails then the {@code siteswapConstructor} will not be invoked.
	 * @param siteswapConstructor   A function which takes a {@link State}[] and returns a {@link Siteswap}.
	 */
	public StateSearcher(final Set<State> startingStates,
	                     final int maxPeriod,
	                     final Predicate<GeneralPath> intermediatePredicate,
	                     final Predicate<GeneralCircuit> resultPredicate,
	                     final SiteswapConstructor<T> siteswapConstructor,
	                     final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		this.startingStates = startingStates != null ? startingStates.iterator() : emptyIterator();
		this.siteswapConstructor = Objects.requireNonNull(siteswapConstructor);
		this.maxPeriod = maxPeriod;
		this.intermediatePredicate = intermediatePredicate == null ? acceptAll() : intermediatePredicate;
		this.resultPredicate = resultPredicate == null ? acceptAll() : resultPredicate;
		this.siteswapRequestBuilder = siteswapRequestBuilder == null ? new SiteswapRequestBuilder() : siteswapRequestBuilder;
	}

	@Override
	public boolean hasNext()
	{
		try
		{
			this.next = get();
			return true;
		}
		catch (final NoMoreSiteswapsException noMoreSiteswapsException)
		{
			LOG.trace("No more elements");
			this.next = null;
			return false;
		}
	}

	@Override
	public T next()
	{
		if (next == null)
		{
			throw new NoSuchElementException("No call to next(), or call to next() after hasNext() returned false.");
		}
		return next;
	}

	public T get() throws NoMoreSiteswapsException
	{
		while (true)
		{
			LOG.trace("get(): Getting next element");

			do
			{
				getNextState();
			}
			while (!canBeReturned());

			try
			{
				return siteswapConstructor.apply(siteswapRequestBuilder.createSiteswapRequest(builder.path.toGeneralCircuit()));
			}
			catch (final Throwable ignored)
			{
				// Probably an illegal siteswap. Just return the next one we find.
				LOG.debug("SiteswapConstructor {} rejected. {}. See stack trace at trace level.", siteswapConstructor, ignored);
				LOG.trace("Stack Trace: ", ignored);
			}
		}
	}

	private boolean canBeReturned()
	{
		return builder.path.isGeneralCircuit() && resultPredicate.test(builder.path.toGeneralCircuit());
	}

	private boolean isLegalSequence()
	{
		final boolean isLegalIntermediateState = intermediatePredicate.test(builder.path);
		if (!isLegalIntermediateState)
		{
			LOG.trace("Intermediate State was rejected: {}", Arrays.toString(builder.path.getStates()));
		}
		return isLegalIntermediateState;
	}

	private void getNextState() throws NoMoreSiteswapsException
	{
		LOG.trace("getNextState(): Getting next State");

		do
		{
			if (builder == null)
			{
				LOG.trace("getNextState(): Not at max period, so building stack");
				builder = new GeneralPathBuilder(startingStates.next());
			}

			if (builder.path.size() < maxPeriod)
			{
				try
				{
					builder.buildStack();
				}
				catch (NoMoreStatesException e)
				{
					builder = null;

				}
			}
			else
			{
				LOG.trace("getNextState(): Reached max period, so moving on last iterator");
				try
				{
					builder.nextState();
				}
				catch (NoMoreStatesException e)
				{
					builder = null;
				}
			}
		}
		while (!isLegalSequence());
	}

	public Stream<T> generate()
	{
		final Spliterator<T> siteswapSpliterator = Spliterators.spliteratorUnknownSize(this,
			Spliterator.ORDERED | Spliterator.NONNULL);

		return StreamSupport.stream(siteswapSpliterator, false).unordered();
	}

	static <T> Predicate<T> acceptAll()
	{
		return (any) -> true;
	}

	private static class NoMoreSiteswapsException extends Exception
	{
	}

	private static class GeneralPathBuilder
	{
		private final GeneralPath path;
		private final Stack<Iterator<Thro>> iterators = new Stack<>();

		private GeneralPathBuilder(final State startingState)
		{
			this.path = new GeneralPath(startingState);
			this.iterators.push(startingState.getAvailableThrows().iterator());
		}

		private void buildStack() throws NoMoreStatesException
		{
			if (iterators.isEmpty())
			{
				throw new NoMoreStatesException();
			}

			if (!iterators.lastElement().hasNext())
			{
				path.pop();
				iterators.pop();
			}
			else
			{
				path.push(iterators.lastElement().next());
				iterators.push(path.getLastState().getAvailableThrows().iterator());
			}
		}

		private void nextState() throws NoMoreStatesException
		{
			if (iterators.isEmpty())
			{
				throw new NoMoreStatesException();
			}

			if (iterators.lastElement().hasNext())
			{
				path.pop();
				path.push(iterators.lastElement().next());
			}
			else
			{
				path.pop();
				iterators.pop();
				nextState();
			}
		}
	}

	private static class NoMoreStatesException extends Exception
	{
	}
}
