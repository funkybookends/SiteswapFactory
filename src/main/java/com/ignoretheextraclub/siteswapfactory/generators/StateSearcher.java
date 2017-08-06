package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
    private final List<SequencePredicate> predicates;
    private final Function<State[], Siteswap> siteswapConstructor;

    private Stack<State> stateStack = new Stack<>();
    private Stack<Iterator<State>> iteratorStack = new Stack<>();
    private Siteswap next;

    protected StateSearcher(final Set<State> startingStates,
                            final int maxPeriod,
                            final List<SequencePredicate> sequencePredicates,
                            final Function<State[], Siteswap> siteswapConstructor)
    {
        this.startingStates = Objects.requireNonNull(startingStates, "Starting States must not be null");
        this.siteswapConstructor = Objects.requireNonNull(siteswapConstructor);
        if (startingStates.size() < 1)
        {
            throw new IllegalArgumentException("Must have at least 1 state");
        }
        this.maxPeriod = maxPeriod;
        this.predicates = sequencePredicates == null ? Collections.emptyList() : sequencePredicates;
    }

    public Siteswap get() throws NoMoreElementsException
    {
        while (true)
        {
            LOG.trace("get(): Getting next element");
            do
            {
                getNextState();
            }
            while (!isLegalLoop());

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
        }
    }

    private boolean isLegalLoop()
    {
        if (!stateStack.lastElement().canTransition(stateStack.firstElement()))
        {
            return false;
        }
        final State[] currentState = getCurrentState();
        return predicates.stream()
                  .filter(SequencePredicate::supportsTestingLoops)
                  .allMatch(p -> p.testLoop(currentState));
    }

    private boolean isLegalSequence()
    {
        final State[] currentState = getCurrentState();
        return predicates.stream()
                         .filter(SequencePredicate::supportsTestingSequences)
                         .allMatch(p -> p.testSequence(currentState));
    }

    private void getNextState() throws NoMoreElementsException
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

    private void buildStack() throws NoMoreElementsException
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

    private void moveIteratorOnToNextState() throws NoMoreElementsException
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
                throw new NoMoreElementsException();
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
