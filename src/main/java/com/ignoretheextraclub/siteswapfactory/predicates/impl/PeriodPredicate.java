package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Enforces that all returned siteswaps have a legal period.
 */
public class PeriodPredicate implements SequencePredicate
{
    private final int[] periods;

    public PeriodPredicate(final int period)
    {
        this.periods = new int[]{period};
    }

    public PeriodPredicate(final int... periods)
    {
        this.periods = periods;
    }

    @Override
    public boolean testLoop(final State[] states)
    {
        for (final int legalPeriod : periods)
        {
            if (states.length == legalPeriod)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean testSequence(final State[] sequence)
    {
        throw new UnsupportedOperationException("testSequence");
    }

    @Override
    public boolean supportsTestingLoops()
    {
        return true;
    }

    @Override
    public boolean supportsTestingSequences()
    {
        return false;
    }
}
