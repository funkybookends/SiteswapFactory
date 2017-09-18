package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.function.Function;

/**
 Created by caspar on 11/12/16.
 */
public class Reducer<T> implements Function<T[], T[]>
{
    public T[] apply(final T[] duplicated)
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

    public static <T> T[] reduce(final T[] duplicated)
    {
        return new Reducer<T>().apply(duplicated);
    }

    public static <T> Function<T[], T[]> getReducerFor(final T[] duplicated)
    {
        return new Reducer<>();
    }

    public static class IntReducer implements Function<int[], int[]>
    {
        public static IntReducer INSTANCE;

        private IntReducer()
        {
            // Singleton
        }

        public static IntReducer get()
        {
            if (INSTANCE == null)
            {
                INSTANCE = new IntReducer();
            }
            return INSTANCE;
        }

        public int[] apply(final int[] duplicated)
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
}
