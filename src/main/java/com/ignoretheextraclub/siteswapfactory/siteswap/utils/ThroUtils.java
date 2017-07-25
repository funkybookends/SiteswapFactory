package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 Created by caspar on 26/07/17.
 */
public final class ThroUtils
{
    private ThroUtils(){};

    /**
     Will find and return the highest throw in an array of throws.

     @param thros

     @return the highest.
     */
    public static Thro getHighestThro(Thro[] thros)
    {
        Thro highest = thros[0];
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
