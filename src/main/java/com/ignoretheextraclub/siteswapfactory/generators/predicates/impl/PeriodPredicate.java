package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Enforces that all returned siteswaps have a legal period.
 */
public class PeriodPredicate implements SequencePredicate
{
    private final boolean legal;
    private final int[] periods;

    public PeriodPredicate(final boolean legal, final int period)
    {
        this.legal = legal;
        this.periods = new int[]{period};
    }

    public PeriodPredicate(final boolean legal, final int... periods)
    {
        this.legal = legal;
        this.periods = periods;
    }

    @Override
    public boolean test(final State[] states, final Boolean loop)
    {
        if (!loop)
        {
            return true;
        }

        for (final int legalPeriod : periods)
        {
            if (states.length == legalPeriod)
            {
                return legal;
            }
        }
        return !legal;
    }
}
