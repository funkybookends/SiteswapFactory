package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import java.util.Arrays;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

/**
 * Checks to see if a state[] contains a throw combination. Use {@code null} to allow any throw.
 * <p>
 * This implementation is only safe to use as an intermediatePredicate if you require combinations to be banned.
 *
 * @author Caspar Nonclercq
 */
public class ThroCombinationPredicate implements Predicate<GeneralPath>
{
    private static final Logger LOG = LoggerFactory.getLogger(ThroCombinationPredicate.class);

    protected final Thro[] throCombination;

    /**
     * Creates a throCombination predicate. Nulls may be used to match any throw, but the first and last throws must not
     * be null.
     *
     * @param throCombination the throw combination to check.
     */
    public ThroCombinationPredicate(final Thro... throCombination)
    {
        if (throCombination.length < 1)
        {
            throw new IllegalArgumentException("throCombination must have at least one entry.");
        }

        if (throCombination[0] == null)
        {
            throw new IllegalArgumentException("First Thro must not be null");
        }

        if (throCombination[throCombination.length - 1] == null)
        {
            throw new IllegalArgumentException("Last Thro must not be null");
        }

        this.throCombination = throCombination;
    }

    @Override
    public boolean test(final GeneralPath generalPath)
    {
        return test(generalPath.toArray(new Thro[generalPath.size()]));
    }

    /**
     * Accepts a length of throws shorter or longer than the {@code throwCombination}
     *
     * @param thros the array of throws
     * @return true if the thros contains the {@code throwCombination}
     */
    public boolean test(final Thro[] thros)
    {
        if (thros.length < throCombination.length)
        {
            return false;
        }

        if (thros.length > throCombination.length)
        {
            return testSublists(thros);
        }

        return testCombination(thros, throCombination);
    }

    /**
     * Tests if an array of throws matches the given combination.
     *
     * @param thros           The set of throws to check
     * @param throCombination The throw combination to match against
     * @return true if the {@code thros} matches the {@code throCombination}
     */
    public static boolean testCombination(final Thro[] thros, final Thro[] throCombination)
    {
        if (throCombination.length != thros.length)
        {
            throw new IllegalArgumentException("Arrays must have same length.");
        }

        for (int i = 0; i < throCombination.length; i++)
        {
            if (throCombination[i] != null && !throCombination[i].equals(thros[i]))
            {
                LOG.trace("{} does not contain {}", thros, throCombination);
                return false;
            }
        }

        LOG.trace("{} contains {}", thros, throCombination);
        return true;
    }

    /**
     * Tests all sublists
     *
     * @param thros
     * @return
     */
    private boolean testSublists(final Thro[] thros)
    {
        for (int i = 0; i < thros.length - throCombination.length + 1; i++)
        {
            final Thro[] subset = new Thro[throCombination.length];

            System.arraycopy(thros, i, subset, 0, throCombination.length);

            if (test(subset))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a predicate than bans all of these single throws.
     *
     * @param thros the throws to ban. Not a combination, but all individually.
     * @return a predicate.
     */
    public static Predicate<GeneralPath> banAllSingleThros(final Thro... thros)
    {
        if (thros.length == 0)
        {
            throw new IllegalArgumentException("No thros provided");
        }

        Predicate<GeneralPath> predicate = new ThroCombinationPredicate(thros[0]).negate();

        if (thros.length > 1)
        {
            for (int i = 1; i < thros.length; i++)
            {
                predicate = predicate.and(new ThroCombinationPredicate(thros[i]).negate());
            }
        }

        return predicate;
    }

    @Override
    public String toString()
    {
        return "ThroCombinationPredicate{" + Arrays.toString(throCombination) + '}';
    }
}
