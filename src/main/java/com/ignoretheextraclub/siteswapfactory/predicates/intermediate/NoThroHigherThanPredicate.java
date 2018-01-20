package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Ensures that the state array contains no {@link com.ignoretheextraclub.siteswapfactory.siteswap.Thro} larger
 * than the provided thro.
 *
 * @author Caspar Nonclercq
 */
public class NoThroHigherThanPredicate implements Predicate<GeneralPath>
{
    private final Thro maxThro;

    public NoThroHigherThanPredicate(final Thro maxThro)
    {
        this.maxThro = maxThro;
    }

    @Override
    public boolean test(final GeneralPath generalPath)
    {
        for (final Thro thro : generalPath)
        {
            if (thro.compareTo(maxThro) > 0)
            {
                return false;
            }
        }

        return true;
    }
}
