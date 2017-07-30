package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 Created by caspar on 30/07/17.
 */
public class BannedStatePredicate implements StatePredicate
{
    private final Set<State> bannedStates = new HashSet<>();

    public BannedStatePredicate(final State bannedState)
    {
        bannedStates.add(bannedState);
    }

    public BannedStatePredicate(final State... bannedStates)
    {
        Collections.addAll(this.bannedStates, bannedStates);
    }

    @Override
    public boolean test(final State state)
    {
        return !bannedStates.contains(state);
    }
}
