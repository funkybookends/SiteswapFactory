package com.ignoretheextraclub.siteswapfactory.predicates.result;

import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import java.util.function.Predicate;

/**
 * Returns a predicate that is suitable for resultPredicates
 * @author Caspar Nonclercq
 */
public class LoopCheckingThrowCombinationPredicate extends ThroCombinationPredicate
{
    public LoopCheckingThrowCombinationPredicate(final Thro... throCombination)
    {
        super(throCombination);
    }

    @Override
    public boolean test(final State[] states)
    {
        for (int i = 0; i < states.length; i++)
        {
            final ArrayLoopingIterator<State> arrayLoopingIterator = new ArrayLoopingIterator<>(states, i);
            final Thro[] thros = getThros(arrayLoopingIterator, throCombination.length);

            if (ThroCombinationPredicate.testCombination(thros, throCombination))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a predicate that requires the state
     * @param thros
     * @return
     */
    public static Predicate<State[]> requireAnyOneOf(final Thro[] thros)
    {
        if (thros.length == 0)
        {
            throw new IllegalArgumentException("No thros provided");
        }

        Predicate<State[]> predicate = new ThroCombinationPredicate(thros[0]);

        if (thros.length > 1)
        {
            for (int i = 1; i < thros.length; i++)
            {
                predicate = predicate.or(new ThroCombinationPredicate(thros[i]));
            }
        }

        return predicate;
    }
}
