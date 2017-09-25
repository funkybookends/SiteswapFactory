package com.ignoretheextraclub.siteswapfactory.siteswap;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by caspar on 26/07/17.
 */
public interface Thro extends Comparable
{
    /**
     * Returns the number of beats before all objects in the throw land
     *
     * @return
     */
    int getNumBeats();

    /**
     * Get the number of objects needed to make this throw.
     *
     * @return int number of objects
     */
    int getNumObjectsThrown();

    @SuppressWarnings("unchecked")
    static <T extends Comparable> T getHighest(final T[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        return Arrays.stream(thros)
                     .reduce((left, right) -> left.compareTo(right) < 0 ? right : left)
                     .orElseThrow(() -> new IllegalArgumentException("thros must have at least one element"));
    }
}
