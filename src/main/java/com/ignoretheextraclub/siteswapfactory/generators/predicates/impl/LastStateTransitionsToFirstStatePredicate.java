package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Created by caspar on 30/07/17.
 */
public class LastStateTransitionsToFirstStatePredicate implements ReturnStatePredicate
{
    private static final LastStateTransitionsToFirstStatePredicate INSTANCE = new LastStateTransitionsToFirstStatePredicate();

    public static LastStateTransitionsToFirstStatePredicate get()
    {
        return INSTANCE;
    }

    private LastStateTransitionsToFirstStatePredicate(){}

    @Override
    public boolean test(final State[] states)
    {
        return states[states.length - 1].canTransition(states[0]);
    }
}
