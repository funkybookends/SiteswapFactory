package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.stream.Stream;

/**
 Created by caspar on 30/07/17.
 */
public class RequiredStatePredicate implements ReturnStatePredicate
{
    private final State requiredState;

    public RequiredStatePredicate(final State requiredState)
    {
        this.requiredState = requiredState;
    }

    @Override
    public boolean test(final State[] states)
    {
        return Stream.of(states).anyMatch(requiredState::equals);
    }
}
