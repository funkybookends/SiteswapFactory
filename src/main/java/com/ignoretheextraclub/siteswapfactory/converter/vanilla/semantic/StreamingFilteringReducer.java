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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * A streaming reducer.
 * @author Caspar Nonclercq
 */
public class StreamingFilteringReducer implements Reducer
{
    private static StreamingFilteringReducer INSTANCE;

    private StreamingFilteringReducer()
    {
        // Singleton
    }

    public static StreamingFilteringReducer get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StreamingFilteringReducer();
        }
        return INSTANCE;
    }

    public <T> T[] reduce(final T[] duplicated)
    {
        Objects.requireNonNull(duplicated, "duplicated must not be null");

        return IntStream.rangeClosed(1, duplicated.length / 2) // From 1 to half the length inclusive
                        .filter(candidateFactor -> duplicated.length % candidateFactor == 0)
                        .filter(factor -> isRepeatingUnit(factor, duplicated))
                        .boxed().findFirst()
                        .map(length -> Arrays.copyOf(duplicated, length)) // Get shorter version
                        .orElse(duplicated); // return the full version
    }

    private static <T> boolean isRepeatingUnit(final int factor, final T[] array)
    {
        return IntStream.range(1, array.length / factor)
                        .allMatch(part -> rangeContainsEqualObjects(part * factor, factor, array));
    }

    private static <T> boolean rangeContainsEqualObjects(final int offset, final int length, final T[] array)
    {
        return IntStream.range(0, length)
                        .allMatch(index -> array[index].equals(array[offset + index]));
    }

    /**
     * Convenient static method for reducing an array
     *
     * @param duplicated The repeated array
     * @param <T>        The type of the array
     *
     * @return The shortest repeating unit
     *
     * @see #reduce(int[]) A primative int version
     */
    public static <T> T[] reducing(final T[] duplicated)
    {
        return get().reduce(duplicated);
    }

    /**
     * Convenient static metod for reducing an int array
     *
     * @param duplicated The repeated array
     *
     * @return The shortest repeating unit
     *
     * @see #reduce(Object[]) An object version
     */
    public static int[] reduce(final int[] duplicated)
    {
        return IntReducer.get().apply(duplicated);
    }

    /**
     * A primative int version
     */
    public static class IntReducer implements Reducer.IntReducer
    {
        private static IntReducer INSTANCE;

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

        /**
         * Reduces an array to its shortest repeating unit
         *
         * @param duplicated The repeating array
         *
         * @return The shortest array
         */
        public int[] apply(final int[] duplicated)
        {
            Objects.requireNonNull(duplicated, "duplicated must not be null");

            return IntStream.rangeClosed(1, duplicated.length / 2) // From 1 to half the length inclusive
                            .filter(candidateFactor -> duplicated.length % candidateFactor == 0)
                            .filter(factor -> isRepeatingUnit(factor, duplicated))
                            .boxed().findFirst()
                            .map(length -> Arrays.copyOf(duplicated, length)) // Get shorter version
                            .orElse(duplicated); // return the full version
        }

        private static boolean isRepeatingUnit(final int factor, final int[] array)
        {
            return IntStream.range(1, array.length / factor)
                            .allMatch(part -> rangeContainsEqualObjects(part * factor, factor, array));
        }

        private static boolean rangeContainsEqualObjects(final int offset, final int length, final int[] array)
        {
            return IntStream.range(0, length)
                            .allMatch(index -> array[index] == array[offset + index]);
        }
    }
}
