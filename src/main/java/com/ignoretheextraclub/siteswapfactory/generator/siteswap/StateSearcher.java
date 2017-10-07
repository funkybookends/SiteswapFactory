package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

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
    private final Set<State> startingStates;
    private final int maxPeriod;
    private final Predicate<State[]> intermediatePredicate;
    private final Predicate<State[]> resultPredicate;
    private final SiteswapRequestBuilder siteswapRequestBuilder;
    private final SiteswapConstructor<T> siteswapConstructor;

    /**
     * Internal state
     */
    private Stack<State> stateStack = new Stack<>();
    private Stack<Iterator<State>> iteratorStack = new Stack<>();
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
                         final Predicate<State[]> intermediatePredicate,
                         final Predicate<State[]> resultPredicate,
                         final SiteswapConstructor<T> siteswapConstructor,
                         final SiteswapRequestBuilder siteswapRequestBuilder)
    {
        this.startingStates = startingStates != null ? startingStates : new HashSet<>();
        this.siteswapConstructor = Objects.requireNonNull(siteswapConstructor);
        if (!siteswapConstructor.accepts(new State[0]))
        {
            throw new IllegalArgumentException("siteswapConstructor must accept " + State[].class.getCanonicalName());
        }
        this.maxPeriod = validatePeriod(maxPeriod);
        this.intermediatePredicate = intermediatePredicate == null ? acceptAll() : intermediatePredicate;
        this.resultPredicate = resultPredicate == null ? isLegalLoop() : isLegalLoop().and(resultPredicate);
        this.siteswapRequestBuilder = Objects.requireNonNull(siteswapRequestBuilder);
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
            return false;
        }
    }

    @Override
    public T next()
    {
        if (next == null)
        {
            LOG.warn("Incorrect usage of iterator. Expected a call to hasNext() before a call to next()");
            throw new IllegalStateException("Expected a call to hasNext before a call to next.");
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
                return siteswapConstructor.apply(siteswapRequestBuilder.createSiteswapRequest(getCurrentState()));
            }
            catch (final Exception ignored)
            {
                // Probably an illegal siteswap. Just return the next one we find.
                LOG.trace("get(): state {} illegal, {}", stateStack, ignored);
                LOG.trace("Stack Trace", ignored);
            }
        }
    }

    private boolean canBeReturned()
    {
        return resultPredicate.test(getCurrentState());
    }

    private boolean isLegalSequence()
    {
        final State[] intermediateState = getCurrentState();
        final boolean isLegalIntermediateState = intermediatePredicate.test(intermediateState);
        if (!isLegalIntermediateState)
        {
            LOG.trace("Intermediate State was rejected: {}", Arrays.toString(intermediateState));
        }
        return isLegalIntermediateState;
    }

    private void getNextState() throws NoMoreSiteswapsException
    {
        LOG.trace("getNextState(): Getting next State");

        do
        {
            if (stateStack.size() < maxPeriod)
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
        if (stateStack.size() < maxPeriod)
        {
            LOG.trace("buildStack(): pushing next iterator onto stack.");
            if (iteratorStack.isEmpty())
            {
                iteratorStack.push(startingStates.iterator());
            }
            else
            {
                iteratorStack.push(stateStack.lastElement().getNextStates().iterator());
            }
            stateStack.push(null); // Push a dummy item on the stack since we don't actually have a current yet.
            moveIteratorOnToNextState();
        }
        else
        {
            LOG.warn("buildStack(): stack size {} >= {} maxPeriod. Not building.", stateStack.size(), maxPeriod);
        }
    }

    private void moveIteratorOnToNextState() throws NoMoreSiteswapsException
    {
        LOG.trace("moveIteratorOnToNextState(): Moving last stack iterator onto next element.");
        if (iteratorStack.lastElement().hasNext())
        {
            LOG.trace("moveIteratorOnToNextState(): getting next for iterator at {}", iteratorStack.size());
            final State nextState = iteratorStack.lastElement().next();
            stateStack.pop();
            stateStack.push(nextState);
        }
        else
        {
            iteratorStack.pop();
            if (iteratorStack.isEmpty())
            {
                throw new NoMoreSiteswapsException();
            }
            stateStack.pop();
            moveIteratorOnToNextState();
        }
    }

    private State[] getCurrentState()
    {
        final State[] states = stateStack.toArray(new State[stateStack.size()]);
        LOG.trace("Current State {}: {}", states.length, states);
        return states;
    }

    public Stream<T> generate()
    {
        final Spliterator<T> siteswapSpliterator = Spliterators.spliteratorUnknownSize(this,
            Spliterator.ORDERED | Spliterator.NONNULL);

        return StreamSupport.stream(siteswapSpliterator, false).unordered();
    }

    private static Predicate<State[]> isLegalLoop()
    {
        return (states) -> states.length > 0 && states[states.length - 1].canTransition(states[0]);
    }

    /**
     * Internal exception to indicate that there are no more siteswaps available.
     */
    private static int validatePeriod(final int maxPeriod)
    {
        if (maxPeriod <= 0 || maxPeriod > VanillaState.MAX_SIZE)
        {
            throw new IllegalArgumentException("maxPeriod is out of bounds or was not set.");
        }
        else
        {
            return maxPeriod;
        }
    }

    static Predicate<State[]> acceptAll()
    {
        return (any) -> true;
    }

    private static class NoMoreSiteswapsException extends Exception
    {
    }
}
