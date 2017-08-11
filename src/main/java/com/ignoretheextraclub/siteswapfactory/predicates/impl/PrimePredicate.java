package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.Collections;
import java.util.HashSet;

/**
 Created by caspar on 06/08/17.
 */
public class PrimePredicate implements SequencePredicate
{
    @Override
    public boolean testLoop(final State[] looping)
    {
        final HashSet<Object> states = new HashSet<>();
        Collections.addAll(states, looping);
        return states.size() == looping.length;
    }

    @Override
    public boolean testSequence(final State... sequence)
    {
        final HashSet<Object> states = new HashSet<>();
        Collections.addAll(states, sequence);
        return states.size() == sequence.length;
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
