package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.lang.reflect.Array;
import java.util.function.BiFunction;

/**
 * Converts a global representation to a local representation.
 * <p>
 * A global representation is where the order of the throws is represented on the pattern level, and a local version is
 * from the perspective of a juggler.
 *
 * @param <T> The type
 *
 * @author Caspar Nonclercq
 * @see LocalToGlobalConverter for the reverse
 * @see IntConverter for a primative int implementation
 */
public class GlobalToLocalBiConverter<T> implements BiFunction<T[], Integer, T[]>
{
    /**
     * Convert from global to local for the start, where the start is the juggler you want zero indexed.
     *
     * @param global The global representation
     * @param start  The starting position, i.e. the juggler, zero indexed.
     *
     * @return A new array with the original values rearranged.
     */
    @Override
    public T[] apply(final T[] global, final Integer start)
    {
        @SuppressWarnings("unchecked")
        final T[] local = (T[]) Array.newInstance(global.getClass().getComponentType(), global.length);

        for (int i = 0; i < global.length; i++)
        {
            local[i] = global[(start + (i * 2)) % global.length];
        }

        return local;
    }

    /**
     * A primitive int version of {@link GlobalToLocalBiConverter}
     */
    public static class IntConverter implements BiFunction<int[], Integer, int[]>
    {
        public static IntConverter INSTANCE;

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
         * @param start  The starting position, i.e. the juggler, zero indexed.
         *
         * @return A new array with the original values rearranged.
         */
        @Override
        public int[] apply(final int[] global, final Integer start)
        {
            final int[] local = new int[global.length];

            for (int i = 0; i < global.length; i++)
            {
                local[i] = global[(start + (i * 2)) % global.length];
            }

            return local;
        }
    }
}
