package com.ignoretheextraclub.siteswapfactory.predicates.result;

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.NoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Uses the {@link StatesToThrosConverter} to get the throws and is therefore a loop checking
 * version of {@link NoThroHigherThanPredicate}.
 *
 * @author Caspar Nonclercq
 */
public class LoopCheckingNoThroHigherThanPredicate implements Predicate<GeneralCircuit>
{
    private final Thro maxThro;

    public LoopCheckingNoThroHigherThanPredicate(final Thro maxThro)
    {
        this.maxThro = maxThro;
    }

    @Override
    public boolean test(final GeneralCircuit generalPath)
    {
        for (final Thro thro : generalPath.getThros())
        {
            if (thro.compareTo(maxThro) > 0)
            {
                return false;
            }
        }

        return true;
    }
}
