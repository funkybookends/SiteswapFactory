package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 Created by caspar on 26/07/17.
 */
public final class ThroUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(ThroUtils.class);

    private ThroUtils()
    {
    }

    /**
     Will find and return the highest throw in an array of throws.

     @param thros The throws to search through, cannot be null.

     @return the highest.
     */
    @SuppressWarnings("unchecked") // Thro extends Comparable
    public static <T extends Thro> T getHighestThro(T[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        T highest = thros[0];
        for (int i = 1; i < thros.length; i++)
        {
            if (highest.compareTo(thros[i]) < 0)
            {
                highest = thros[i];
            }
        }
        return highest;
    }
}
