package com.ignoretheextraclub.siteswapfactory.sorters.utils;

import java.util.Arrays;

/**
 Created by caspar on 11/12/16.
 */
public class SortingUtils
{
    private SortingUtils()
    {
    }

    public static <T> T[] reduce(final T[] duplicated)
    {
        final int len = duplicated.length;
        for (int i = 1; i <= len / 2; i++)
        {
            if (len % i == 0)
            {
                if (checkFactors(i, duplicated))
                {
                    return Arrays.copyOf(duplicated, i);
                }
            }
        }
        return duplicated;
    }

    private static <T> boolean checkFactors(final int factor, final T[] arr)
    {
        for (int j = 1; j < arr.length / factor; j++)
        {
            if (!rangeCompare(j * factor, factor, arr))
            {
                return false;
            }
        }
        return true;
    }

    private static <T> boolean rangeCompare(final int offset, final int len, final T[] arr)
    {
        for (int i = 0; i < len; i++)
        {
            if (!arr[i].equals(arr[offset + i]))
            {
                return false;
            }
        }
        return true;
    }

    public static int[] reduce(final int[] duplicated)
    {
        final int len = duplicated.length;
        for (int i = 1; i <= len / 2; i++)
        {
            if (len % i == 0)
            {
                if (checkFactors(i, duplicated))
                {
                    return Arrays.copyOf(duplicated, i);
                }
            }
        }
        return duplicated;
    }

    private static boolean checkFactors(final int factor, final int[] arr)
    {
        for (int j = 1; j < arr.length / factor; j++)
        {
            if (!rangeCompare(j * factor, factor, arr))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean rangeCompare(final int offset, final int len, final int[] arr)
    {
        for (int i = 0; i < len; i++)
        {
            if (!(arr[i] == arr[offset + i]))
            {
                return false;
            }
        }
        return true;
    }
}
