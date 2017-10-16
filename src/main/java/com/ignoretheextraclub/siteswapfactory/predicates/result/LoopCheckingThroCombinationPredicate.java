package com.ignoretheextraclub.siteswapfactory.predicates.result;

import java.util.Arrays;
import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

/**
 * Returns a predicate that is suitable for resultPredicates
 *
 * @author Caspar Nonclercq
 */
public class LoopCheckingThroCombinationPredicate extends ThroCombinationPredicate
{
    public LoopCheckingThroCombinationPredicate(final Thro... throCombination)
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
     *
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

    /**
     * Returns a predicate than bans all of these single throws.
     *
     * @param thros the throws to ban. Not a combination, but all individually.
     * @return a predicate.
     */
    public static Predicate<State[]> banAllSingleThros(final Thro... thros)
    {
        if (thros.length == 0)
        {
            throw new IllegalArgumentException("No thros provided");
        }

        Predicate<State[]> predicate = new LoopCheckingThroCombinationPredicate(thros[0]).negate();

        if (thros.length > 1)
        {
            for (int i = 1; i < thros.length; i++)
            {
                predicate = predicate.and(new LoopCheckingThroCombinationPredicate(thros[i]).negate());
            }
        }

        return predicate;
    }

    @Override
    public String toString()
    {
        return "LoopCheckingThroCombinationPredicate{" + Arrays.toString(throCombination) + "}";
    }
}
