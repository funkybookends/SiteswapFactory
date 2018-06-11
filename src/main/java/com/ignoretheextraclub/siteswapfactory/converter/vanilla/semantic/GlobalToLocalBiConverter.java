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

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Converts a global representation to a local representation.
 * <p>
 * A global representation is where the order of the throws is represented on the pattern level, and a local version is
 * from the perspective of a juggler.
 * <p>
 * <pre>
 * [A, B, C, D, E], 0 -> [A, C, E, B, D]
 * [A, B, C, D, E], 1 -> [B, D, A, C, E]
 * [A, B, C, D]   , 0 -> [A, C]
 * [A, B, C, D]   , 1 -> [B, D]
 * </pre>
 *
 * @param <T> The type
 *
 * @author Caspar Nonclercq
 * @see LocalToGlobalConverter A converter performing the reverse
 * @see IntConverter A primative int implementation
 */
public class GlobalToLocalBiConverter<T> implements BiFunction<T[], Integer, T[]>
{
    /**
     * Convert from global to local for the start, where the start is the juggler you want zero indexed.
     *
     * @param global The global representation
     * @param start  The starting position, i.e. the juggler, zero indexed, must not be negative.
     *
     * @return A new array with the original values rearranged.
     */
    @Override
    public T[] apply(final T[] global, final Integer start)
    {
        Objects.requireNonNull(start, "start cannot be null");
        Objects.requireNonNull(global, "global cannot be null");
        if (start < 0)
        {
            throw new IllegalArgumentException("start cannot be negative");
        }
        if (global.length < 1)
        {
            throw new IllegalArgumentException("global must have at least one throw");
        }

        final int length = global.length % 2 == 0 ? global.length / 2 : global.length;

        @SuppressWarnings("unchecked")
        final T[] local = (T[]) Array.newInstance(global.getClass().getComponentType(), length);

        for (int i = 0; i < length; i++)
        {
            local[i] = global[(start + (i * 2)) % global.length];
        }

        return local;
    }

    /**
     * Convenient static method to apply to local representation.
     *
     * @param global The global representation
     * @param start  The starting position, i.e. the juggler, zero indexed, must not be negative
     * @param <T>    The type
     *
     * @return A local representation.
     *
     * @see #convertToLocal(int[], Integer) for a convenient {@code int[]} method
     */
    public static <T> T[] convertToLocal(final T[] global, final Integer start)
    {
        return new GlobalToLocalBiConverter<T>().apply(global, start);
    }

    /**
     * Convenient static method to apply an {@code int[]} to local representation.
     *
     * @param global The global representation
     * @param start  The starting position, i.e. the juggler, zero indexed, must not be negative
     *
     * @return A local representation.
     *
     * @see #convertToLocal(Object[], Integer) An Object version
     */
    public static int[] convertToLocal(final int[] global, final Integer start)
    {
        return IntConverter.get().apply(global, start);
    }

    /**
     * A primitive int version of {@link GlobalToLocalBiConverter}
     */
    public static class IntConverter implements BiFunction<int[], Integer, int[]>
    {
        private static IntConverter INSTANCE;

        private IntConverter()
        {
            // Singleton
        }

        public static IntConverter get()
        {
            if (INSTANCE == null)
            {
                INSTANCE = new IntConverter();
            }
            return INSTANCE;
        }

        /**
         * Convert from global to local for the start, where the start is the juggler you want zero indexed.
         *
         * @param global The global representation
         * @param start  The starting position, i.e. the juggler, zero indexed, must not be negative
         *
         * @return A new array with the original values rearranged.
         */
        @Override
        public int[] apply(final int[] global, final Integer start)
        {
            Objects.requireNonNull(global, "global cannot be null");
            Objects.requireNonNull(start, "start cannot be null");
            if (start < 0)
            {
                throw new IllegalArgumentException("start cannot be negative");
            }
            if (global.length < 1)
            {
                throw new IllegalArgumentException("global must have at least one throw");
            }

            final int length = global.length % 2 == 0 ? global.length / 2 : global.length;

            final int[] local = new int[length];

            for (int i = 0; i < length; i++)
            {
                local[i] = global[(start + (i * 2)) % global.length];
            }

            return local;
        }
    }
}
