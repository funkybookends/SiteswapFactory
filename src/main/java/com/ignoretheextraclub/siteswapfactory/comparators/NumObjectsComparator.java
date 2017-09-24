package com.ignoretheextraclub.siteswapfactory.comparators;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import java.util.Comparator;
import java.util.Objects;

/**
 * Compares two siteswaps by the number of objects. Siteswaps with less objects will come first.
 *
 * @author Caspar Nonclercq
 */
public class NumObjectsComparator implements Comparator<Siteswap>
{
    public static NumObjectsComparator INSTANCE;

    private NumObjectsComparator()
    {
        // Singleton
    }

    public static NumObjectsComparator get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new NumObjectsComparator();
        }
        return INSTANCE;
    }

    /**
     * Compares two siteswaps by the number of objects. Siteswaps with less objects will come first.
     * <p>
     * Note: this comparator imposes orderings that are inconsistent with equals.
     *
     * @param first  The first siteswap
     * @param second The second siteswap
     *
     * @return A negative number if {@code first} has less objects, 0 if they are the same, and positive if second has
     * less objects.
     */
    @Override
    public int compare(final Siteswap first, final Siteswap second)
    {
        Objects.requireNonNull(first, "first cannot be null");
        Objects.requireNonNull(second, "second cannot be null");
        return first.getNumObjects() - second.getNumObjects();
    }
}
