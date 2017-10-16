package com.ignoretheextraclub.siteswapfactory.sorters;

import java.util.Arrays;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils;

/**
 * A result that encapsulates the result of a {@link StartFinder} being applied to a {@link State[]}. It provides
 * methods to access the results, get the {@code StartFinder} used sort other arrays of the same length to match.
 *
 * It is intended that result of the operation will be stored and not calculated each time.
 *
 * @author Caspar Nonclercq
 */
public interface StartFinderResult
{
    /**
     * Returns the state array sorted by the starting strategy returned by {@link #getStartingStrategy()}
     *
     * @return The sorted array
     */
    State[] getSorted();

    /**
     * Returns the index of the first element in {@link #getSorted()} where it was in the original Unsorted array
     *
     * @return The index
     */
    int getWinningIndex();

    /**
     * Get the starting strategy that was used to sort the result found int {@link #getSorted()}
     *
     * @return The sorting strategy used.
     */
    StartingStrategy getStartingStrategy();

    /**
     * Sorts an array of the same length to match the sorted array
     *
     * @param unsorted An unsorted array of anytype
     * @param <T> The type
     *
     * @return The array rotated to the same start as {@link #getSorted()}
     */
    default <T> T[] rotateToMatch(final T[] unsorted)
    {
        final State[] sorted = getSorted();

        if (sorted.length != unsorted.length)
        {
            throw new IllegalArgumentException("Cannot sort to match because lengths of sorted " + Arrays.toString(
                    sorted) + " and " + Arrays.toString(unsorted) + " do not match");
        }

        return ArrayUtils.getRotatedCopy(unsorted, getWinningIndex());
    }
}
