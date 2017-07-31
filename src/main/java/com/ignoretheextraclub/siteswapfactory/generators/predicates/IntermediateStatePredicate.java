package com.ignoretheextraclub.siteswapfactory.generators.predicates;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.function.Predicate;

/**
 Created by caspar on 30/07/17.
 */
public interface IntermediateStatePredicate extends Predicate<State[]>
{
    @Override
    default IntermediateStatePredicate and(Predicate<? super State[]> other)
    {
        if (other != null)
        {
            return (t) -> test(t) && other.test(t);
        }
        return this;
    }

    @Override
    default IntermediateStatePredicate or(Predicate<? super State[]> other)
    {
        if (other!= null)
        {
            return (t) -> test(t) || other.test(t);
        }
        return this;
    }
}
