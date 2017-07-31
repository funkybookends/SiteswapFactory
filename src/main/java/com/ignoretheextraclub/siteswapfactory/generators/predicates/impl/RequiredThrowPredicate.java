package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Created by caspar on 30/07/17.
 */
public class RequiredThrowPredicate implements ReturnStatePredicate
{
    @Override
    public boolean test(final State[] states)
    {
        throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
    }
}
