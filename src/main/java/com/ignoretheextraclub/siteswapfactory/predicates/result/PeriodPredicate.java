package com.ignoretheextraclub.siteswapfactory.predicates.result;

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Checks if a siteswap has a specified period, i.e. the length matches
 *
 * This is not suitable as an intermediate predicate.
 *
 * @author Caspar Nonclercq
 */
public class PeriodPredicate implements Predicate<State[]>
{
    private final int period;

    public PeriodPredicate(final int period)
    {
        if (period < 0)
        {
            throw new IllegalArgumentException("period cannot be less than 0");
        }
        this.period = period;
    }

    @Override
    public boolean test(final State[] states)
    {
        return states.length == period;
    }

    public boolean test(final Siteswap siteswap)
    {
        return test(siteswap.getStates());
    }

    /**
     * Creates a {@link Predicate<State[]>} that will return true if the given {@code State[]} is any of the provided
     * periods.
     * @param periods The allowed periods.
     * @return A predicate that will allow any of the periods.
     */
    public static Predicate<State[]> anyOf(final int... periods)
    {
        if (periods.length == 0)
        {
            throw new IllegalArgumentException("No periods provided");
        }

        Predicate<State[]> predicate = new PeriodPredicate(periods[0]);

        if (periods.length > 1)
        {
            for (int i = 1; i < periods.length; i++)
            {
                predicate = predicate.or(new PeriodPredicate(periods[i]));
            }
        }

        return predicate;
    }

    public static Predicate<State[]> noneOf(final int... periods)
    {
        return anyOf(periods).negate();
    }
}
