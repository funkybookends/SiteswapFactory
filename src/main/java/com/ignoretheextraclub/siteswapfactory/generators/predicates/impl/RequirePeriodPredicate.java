package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Enforces that all returned siteswaps have a legal period.
 */
public class RequirePeriodPredicate implements ReturnStatePredicate
{
    private final int[] legalPeriods;

    public RequirePeriodPredicate(final int legalPeriod)
    {
        this.legalPeriods = new int[]{legalPeriod};
    }

    public RequirePeriodPredicate(final int... legalPeriods)
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
