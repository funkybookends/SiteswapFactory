package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.IntermediateStatePredicate;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

/**
 Created by caspar on 11/12/16.
 */
public class StateSearcher implements Iterator<Siteswap>
{
    private static final Logger LOG = LoggerFactory.getLogger(StateSearcher.class);

    private final Set<State> startingStates;
    private final int maxPeriod;
    private final StatePredicate aLegalState;
    private final IntermediateStatePredicate aLegalIntermedateState;
    private final ReturnStatePredicate aReturnableState;
    private final Function<State[], Siteswap> siteswapConstructor;

    private Stack<State> stateStack = new Stack<>();
    private Stack<Iterator<State>> iteratorStack = new Stack<>();
    private Siteswap next;

    protected StateSearcher(final Set<State> startingStates,
                            final int maxPeriod,
                            final StatePredicate statePredicates,
                            final IntermediateStatePredicate intermediateStatePredicates,
                            final ReturnStatePredicate returnStatePredicates,
                            final Function<State[], Siteswap> siteswapConstructor)
    {
        this.startingStates = Objects.requireNonNull(startingStates, "Starting States must not be null");
        this.siteswapConstructor = Objects.requireNonNull(siteswapConstructor);
        if (startingStates.size() < 1)
        {
            throw new IllegalArgumentException("Must have at least 1 state");
        }
        this.maxPeriod = maxPeriod;
        this.aLegalState = statePredicates == null ? state -> true : statePredicates;
        this.aLegalIntermedateState = intermediateStatePredicates == null ? states -> true : intermediateStatePredicates;
        this.aReturnableState = returnStatePredicates == null ? states -> true : returnStatePredicates;
    }

    public Siteswap get() throws NoMoreElementsException
    {
        LOG.trace("get(): Getting next element");
        do
        {
            getNextState();
        }
        while (!aReturnableState.test(getCurrentState()));

        try
        {
            return siteswapConstructor.apply(getCurrentState());
        }
        catch (final Exception ignored)
        {
            if (ignored instanceof ClassCastException)
            {
                throw new IllegalStateException("Function provided cannot handle State interface!", ignored);
            }
            // Probably an illegal siteswap. Just return the next one we find.
            LOG.trace("get(): state {} illegal, {}", stateStack, ignored);
        }

        return get();
    }

    private void getNextState() throws NoMoreElementsException
    {
        LOG.trace("getNextState(): Getting next State");

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

        if (!aLegalIntermedateState.test(getCurrentState()))
        {
            LOG.trace("getNextState(): Current state not legal...");
            getNextState();
        }
    }

    private void buildStack() throws NoMoreElementsException
    {
        assert iteratorStack.size() == stateStack.size();

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

    private void moveIteratorOnToNextState() throws NoMoreElementsException
    {
        assert iteratorStack.size() == stateStack.size();
        LOG.trace("moveIteratorOnToNextState(): Moving last stack iterator onto next element.");
        if (iteratorStack.size() > 0)
        {
            if (iteratorStack.lastElement().hasNext())
            {
                LOG.trace("moveIteratorOnToNextState(): getting next for iterator at {}", iteratorStack.size());
                final State nextState = iteratorStack.lastElement().next();
                if (aLegalState.test(nextState))
                {
                    LOG.trace("moveIteratorOnToNextState(): Found next legal state, pushing onto the state stack.");
                    stateStack.pop();
                    stateStack.push(nextState);
                }
                else
                {
                    LOG.trace("moveIteratorOnToNextState(): Next state was not legal, retrying...");
                    moveIteratorOnToNextState();
                }
            }
            else
            {
                iteratorStack.pop();
                if (iteratorStack.isEmpty())
                {
                    throw new NoMoreElementsException();
                }
                stateStack.pop();
                moveIteratorOnToNextState();
            }
        }
        else
        {
            LOG.trace("moveIteratorOnToNextState(): No iterators in stack");
        }
    }

    private State[] getCurrentState()
    {
        final State[] states = stateStack.toArray(new State[stateStack.size()]);
        LOG.trace("Current State {}: {}", states.length, states);
        return states;
    }

    @Override
    public boolean hasNext()
    {
        try
        {
            this.next = get();
            return true;
        }
        catch (NoMoreElementsException e)
        {
            return false;
        }
    }

    @Override
    public Siteswap next()
    {
        if (next == null)
        {
            throw new IllegalStateException("Expected a call to hasNext before a call to next.");
        }
        return next;
    }

    private static class NoMoreElementsException extends Exception
    {

    }
}
