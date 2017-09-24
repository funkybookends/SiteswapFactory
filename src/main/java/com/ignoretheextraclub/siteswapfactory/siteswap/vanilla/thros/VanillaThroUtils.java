package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by caspar on 26/07/17.
 */
public final class VanillaThroUtils
{
    private VanillaThroUtils() {}

    /**
     * Will determine the number of objects in a pattern.
     * <p>
     * It will not validate the pattern, provided the list is not empty it will return an int, otherwise it will throw
     * {@link java.util.NoSuchElementException}. If it is not a valid siteswap, the int may not be correct.
     * <p>
     * You are encourage to create a {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap}
     * to validate the siteswap.
     *
     * @param thros
     *
     * @return the average as an int.
     */
    public static int numObjects(VanillaThro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        if (thros.length < 1)
        {
            throw new IllegalArgumentException("thros cannot be empty");
        }

        return (int) Arrays.stream(thros).mapToInt(VanillaThro::getNumBeats).average().getAsDouble();
    }
}
