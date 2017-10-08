package com.ignoretheextraclub.siteswapfactory.utils;

import java.lang.reflect.Array;

/**
 Created by caspar on 25/07/17.
 */
public final class ArrayUtils
{
    private ArrayUtils(){}

    public static boolean[] copy(final boolean[] positions)
    {
        boolean[] copy = new boolean[positions.length];
        System.arraycopy(positions, 0, copy, 0, positions.length);
        return copy;
    }

    public static boolean[] drop(final boolean[] filledPositions, final boolean highestState)
    {
        final int maxThrow = filledPositions.length;
        boolean[] next = new boolean[maxThrow];
        System.arraycopy(filledPositions, 1, next, 0, maxThrow - 1);
        next[maxThrow - 1] = highestState;
        return next;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] getCopy(final T[] src, final int start)
    {
        final T[] dest = (T[]) Array.newInstance(src.getClass().getComponentType(), src.length);
        System.arraycopy(src, start, dest, 0, src.length - start);
        System.arraycopy(src, 0, dest, src.length - start, start);
        return dest;
    }
}
