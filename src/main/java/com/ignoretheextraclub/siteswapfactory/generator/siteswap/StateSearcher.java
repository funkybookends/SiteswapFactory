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
    private GeneralPath generalPath;
    private Stack<Iterator<Thro>> iteratorStack = new Stack<>();
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
                return siteswapConstructor.apply(siteswapRequestBuilder.createSiteswapRequest(generalPath.toGeneralCircuit()));
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
        return generalPath.isGeneralCircuit() && resultPredicate.test(generalPath.toGeneralCircuit());
    }

    private boolean isLegalSequence()
    {
        final boolean isLegalIntermediateState = intermediatePredicate.test(generalPath);
        if (!isLegalIntermediateState)
        {
            LOG.trace("Intermediate State was rejected: {}", Arrays.toString(generalPath.getStates()));
        }
        return isLegalIntermediateState;
    }

    private void getNextState() throws NoMoreSiteswapsException
    {
        LOG.trace("getNextState(): Getting next State");

        do
        {
            if (generalPath.size() < maxPeriod)
            {
                LOG.trace("getNextState(): Not at max period, so building stack");
                buildStack();
            }
            else
            {
                LOG.trace("getNextState(): Reached max period, so moving on last iterator");
                moveIteratorOnToNextState();
            }
        }
        while (!isLegalSequence());
    }

    private void buildStack() throws NoMoreSiteswapsException
    {
        if (generalPath.size() < maxPeriod)
        {
            LOG.trace("buildStack(): pushing next iterator onto stack.");
            if (iteratorStack.isEmpty())
            {
                final State nextStartingState = startingStates.next();
                generalPath = new GeneralPath(nextStartingState);
                iteratorStack.push(nextStartingState.getAvailableThrows().iterator());
            }
            else
            {
                iteratorStack.push(generalPath.getLastState().getAvailableThrows().iterator());
            }
            moveIteratorOnToNextState();
        }
        else
        {
            LOG.warn("buildStack(): stack size {} >= {} maxPeriod. Not building.", generalPath.size(), maxPeriod);
        }
    }

    private void moveIteratorOnToNextState() throws NoMoreSiteswapsException
    {
        LOG.trace("moveIteratorOnToNextState(): Moving last stack iterator onto next element.");
        if (iteratorStack.lastElement().hasNext())
        {
            LOG.trace("moveIteratorOnToNextState(): getting next for iterator at {}", iteratorStack.size());
            final Thro nextThro = iteratorStack.lastElement().next();
            generalPath.pop();
            generalPath.push(generalPath.push(nextThro));
        }
        else
        {
            iteratorStack.pop();
            if (iteratorStack.isEmpty())
            {
                throw new NoMoreSiteswapsException();
            }
            generalPath.pop();
            moveIteratorOnToNextState();
        }
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
}
