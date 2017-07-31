package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Enforces that all returned siteswaps have a legal period.
 */
public class RequiredPeriodPredicate implements ReturnStatePredicate
{
    private final int[] legalPeriods;

    public RequiredPeriodPredicate(final int legalPeriod)
    {
        this.legalPeriods = new int[]{legalPeriod};
    }

    public RequiredPeriodPredicate(final int... legalPeriods)
    {
        this.legalPeriods = legalPeriods;
    }

    @Override
    public boolean test(final State[] states)
    {
        for (final int legalPeriod : legalPeriods)
        {
            if (states.length == legalPeriod)
            {
                return true;
            }
        }
        return false;
    }
}
