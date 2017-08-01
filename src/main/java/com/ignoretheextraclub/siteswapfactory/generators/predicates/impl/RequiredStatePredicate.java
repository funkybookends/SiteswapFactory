package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.stream.Stream;

/**
 Created by caspar on 30/07/17.
 */
public class RequiredStatePredicate implements SequencePredicate
{
    private final State requiredState;

    public RequiredStatePredicate(final State requiredState)
    {
        this.requiredState = requiredState;
    }

    @Override
    public boolean test(final State[] states, final Boolean loops)
    {
        return !loops || Stream.of(states).anyMatch(requiredState::equals);
    }
}
