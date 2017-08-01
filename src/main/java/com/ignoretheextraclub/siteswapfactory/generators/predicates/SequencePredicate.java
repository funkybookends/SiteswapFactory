package com.ignoretheextraclub.siteswapfactory.generators.predicates;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.function.BiPredicate;

/**
 Created by caspar on 30/07/17.
 */
public interface SequencePredicate extends BiPredicate<State[], Boolean>
{
    @Override
    default SequencePredicate and(BiPredicate<? super State[], ? super Boolean> other)
    {
        if (other != null)
        {
            return (t, q) -> test(t, q) && other.test(t, q);
        }
        return this;
    }

    @Override
    default SequencePredicate or(BiPredicate<? super State[], ? super Boolean> other)
    {
        if (other!= null)
        {
            return (t, q) -> test(t, q) || other.test(t, q);
        }
        return this;
    }
}
