package com.ignoretheextraclub.siteswapfactory.comparators;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import java.util.Comparator;
import java.util.Objects;

/**
 * Compares two siteswaps by the number of jugglers. Siteswaps with less jugglers will come first.
 *
 * @author Caspar Nonclercq
 */
public class NumJugglersComparator implements Comparator<Siteswap>
{
    public static NumJugglersComparator INSTANCE;

    private NumJugglersComparator()
    {
        // Singleton
    }

    public static NumJugglersComparator get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new NumJugglersComparator();
        }
        return INSTANCE;
    }

    /**
     * Compares two siteswaps by the number of jugglers. Siteswaps with less jugglers will come first.
     * <p>
     * Note: this comparator imposes orderings that are inconsistent with equals.
     *
     * @param first  The first siteswap
     * @param second The second siteswap
     *
     * @return A negative number if {@code first} has less jugglers, 0 if they are the same, and positive if second has
     * less jugglers.
     */
    @Override
    public int compare(final Siteswap first, final Siteswap second)
    {
        Objects.requireNonNull(first, "first cannot be null");
        Objects.requireNonNull(second, "second cannot be null");
        return first.getNumJugglers() - second.getNumJugglers();
    }
}
