package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToSequenceConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Ensures that the state array contains no {@link com.ignoretheextraclub.siteswapfactory.siteswap.Thro} larger
 * than the provided thro.
 *
 * @author Caspar Nonclercq
 */
public class NoThroHigherThanPredicate implements Predicate<State[]>
{
    private final Thro maxThro;

    public NoThroHigherThanPredicate(final Thro maxThro)
    {
        this.maxThro = maxThro;
    }

    @Override
    public boolean test(final State[] states)
    {
        final Thro[] sequence = getThros(states);

        for (final Thro thro : sequence)
        {
            if (thro.compareTo(maxThro) > 0)
            {
                return false;
            }
        }

        return true;
    }

    protected Thro[] getThros(final State[] states)
    {
        if (states.length > 1)
        {
            return StatesToSequenceConverter.getSequence(states);
        }
        return new Thro[0];
    }
}
