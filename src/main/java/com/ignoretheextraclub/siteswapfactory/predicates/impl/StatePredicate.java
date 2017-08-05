package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.stream.Stream;

/**
 Created by caspar on 30/07/17.
 */
public class StatePredicate implements SequencePredicate
{
    private final State requiredState;

    public StatePredicate(final State requiredState)
    {
        this.requiredState = requiredState;
    }

    public boolean test(final State[] states)
    {
        return Stream.of(states).anyMatch(requiredState::equals);
    }

    @Override
    public boolean testLoop(final State[] looping)
    {
        return test(looping);
    }

    @Override
    public boolean testSequence(final State[] sequence)
    {
        return test(sequence);
    }

    @Override
    public boolean supportsTestingLoops()
    {
        return true;
    }

    @Override
    public boolean supportsTestingSequences()
    {
        return true;
    }
}
