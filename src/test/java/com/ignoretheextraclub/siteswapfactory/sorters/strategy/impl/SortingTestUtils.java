package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

/**
 Created by caspar on 12/08/17.
 */
public final class SortingTestUtils
{
    private SortingTestUtils(){}

    public static String[] getRotations(final String siteswap)
    {
        final int period = siteswap.length();

        final String doubleSiteswap = siteswap + siteswap;

        final String[] rotations = new String[period];

        for (int i = 0; i < period; i++)
        {
            rotations[i] = doubleSiteswap.substring(i, period + i);
        }

        return rotations;
    }
}
