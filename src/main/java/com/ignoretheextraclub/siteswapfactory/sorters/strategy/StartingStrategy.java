package com.ignoretheextraclub.siteswapfactory.sorters.strategy;

import java.util.Locale;
import java.util.function.BiPredicate;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Starting strategies must be able to tell which of two rotations are better. It must return {@code true} if the first
 * is preferable to the {@code second}
 *
 * @author Caspar Nonclercq
 */
public interface StartingStrategy extends BiPredicate<State[], State[]>
{
    /**
     * Returns the simple name for this sorting strategy.
     *
     * @return the name.
     */
    String getName();

    /**
     * The main method that needs to be implemented. When sorting, the state sorter will be asked which two state sequences is preferred.
     * <p>
     * In a tie break, the implementer can return either true or false.
     *
     * @param first  the first candidate.
     * @param second the second candidate.
     *
     * @return if the first is preferred
     *
     * @throws InvalidSiteswapException      if the sorter does not think the siteswap is valid
     * @throws UnsupportedOperationException if the sorter is unable to sort for this siteswap type.
     */
    boolean test(State[] first, State[] second) throws InvalidSiteswapException, UnsupportedOperationException;

    /**
     * A human friendly description of how this sorter sorts.
     *
     * @return a description.
     */
    String getDescription(Locale locale);
}
