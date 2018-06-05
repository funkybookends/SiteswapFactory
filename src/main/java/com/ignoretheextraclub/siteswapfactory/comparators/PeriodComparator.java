package com.ignoretheextraclub.siteswapfactory.comparators;

import java.util.Comparator;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Compares two siteswaps by {@link Siteswap#getPeriod()}. Siteswaps with a shorter period will come first.
 *
 * @author Caspar Nonclercq
 */
public class PeriodComparator implements Comparator<Siteswap>
{
    private static PeriodComparator INSTANCE;

    private PeriodComparator()
    {
        // Singleton
    }

    public static PeriodComparator get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new PeriodComparator();
        }
        return INSTANCE;
    }

    /**
     * Compares two siteswaps by period. Siteswaps with a shorter period will come first.
     * <p>
     * Note: this comparator imposes orderings that are inconsistent with equals.
     *
     * @param first  The first
     * @param second The second
     *
     * @return negative if {@code first} has a shorter period, 0 if they are the same, or postive if the second has a
     * shorter period.
     */
    @Override
    public int compare(final Siteswap first, final Siteswap second)
    {
        Objects.requireNonNull(first, "first cannot be null");
        Objects.requireNonNull(second, "second cannot be null");
        return first.getPeriod() - second.getPeriod();
    }
}
