/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.utils;

import java.lang.reflect.Array;

/**
 * Utility methods for array operations
 *
 * @author Caspar Nonclercq
 */
public final class ArrayUtils
{
    private ArrayUtils()
    {
    }

    /**
     * Creates a new array that matches the input.
     *
     * @param positions the input
     * @return A copy of the input
     */
    public static boolean[] copy(final boolean[] positions)
    {
        boolean[] copy = new boolean[positions.length];
        System.arraycopy(positions, 0, copy, 0, positions.length);
        return copy;
    }

    /**
     * Creates a new array where all the positions have been shifted left, and the topmost place has been filled with the {@code highestState}
     *
     * @param filledPositions the original array
     * @param highestState    the new topmost position
     * @return a copy bitshifted
     */
    public static boolean[] drop(final boolean[] filledPositions, final boolean highestState)
    {
        final int maxThrow = filledPositions.length;
        boolean[] next = new boolean[maxThrow];
        System.arraycopy(filledPositions, 1, next, 0, maxThrow - 1);
        next[maxThrow - 1] = highestState;
        return next;
    }

    /**
     * Creates a copt of the input array rotated such that the {@code start} is at the {@code start} position
     *
     * @param src   The input array
     * @param start The new 0th position
     * @param <T>   The type
     * @return A copy that has been rotated
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getRotatedCopy(final T[] src, final int start)
    {
        final T[] dest = (T[]) Array.newInstance(src.getClass().getComponentType(), src.length);
        System.arraycopy(src, start, dest, 0, src.length - start);
        System.arraycopy(src, 0, dest, src.length - start, start);
        return dest;
    }
}
