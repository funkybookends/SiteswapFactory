package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.Function;

/**
 * Converts from local to global.
 * <p>
 * Local is where each throw is in the order of a single thrower, whereas global is from the view of the whole pattern.
 * This makes sense in cases where there are two jugglers, and each throw in turn as in {@link
 * com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap}s.
 * <p>
 * <pre>
 *         [A, B, C, D, E, F, G] -> [A, E, B, F, C, G, D]
 *         [a, c, e, g, b, d, f] -> [a, b, c, d, e, f, g]
 * </pre>
 *
 * @author Caspar Nonclercq
 * @see IntConverter for a primitive int version
 * @see GlobalToLocalBiConverter for the reverse process
 */
public class LocalToGlobalConverter<T> implements Function<T[], T[]>
{
    /**
     * Convert from local to global. Assumes this local juggler is the first to start in the global representation.
     *
     * @param local The local throws
     *
     * @return The global throws.
     *
     * @throws IllegalArgumentException if local is of even length as the result is undefined, since the other juggler
     *                                  could throw many things in response
     */
    @Override
    public T[] apply(final T[] local)
    {
        Objects.requireNonNull(local, "local cannot be null");

        if (local.length % 2 == 0)
        {
            throw new IllegalArgumentException("local must have odd length");
        }

        @SuppressWarnings("unchecked")
        final T[] global = (T[]) Array.newInstance(local.getClass().getComponentType(), local.length);

        int firstJuggler = 0; // Pointer that will start from the first thrower
        final int secondJugglerStart = (local.length / 2) + 1; // The first throw of the second juggler
        int secondJuggler = secondJugglerStart; // Pointer that will start from the second thrower

        int index = 0; // Pointer to where we start inserting into new array

        while (firstJuggler < secondJugglerStart)
        {
            global[index++] = local[firstJuggler++]; // Put in the first jugglers throw

            if (secondJuggler < local.length) // Second juggler has one less throw - so can't do last one
            {
                global[index++] = local[secondJuggler++]; // Put in the second jugglers throw
            }
        }

        return global;
    }

    /**
     * Convenient static method to return the global version of a local array.
     *
     * @param local The local throws
     * @param <T>   The type
     *
     * @return The global throws
     *
     * @throws IllegalArgumentException if local is of even length as the result is undefined, since the other juggler
     *                                  could throw many things in response
     */
    public static <T> T[] convertToGlobal(final T[] local)
    {
        return new LocalToGlobalConverter<T>().apply(local);
    }

    /**
     * Convenient static method for an {@code int[]}
     *
     * @param local The local throws
     *
     * @return the global throws
     *
     * @throws IllegalArgumentException if local is of even length as the result is undefined, since the other juggler
     *                                  could throw many things in response
     * @see #convertToGlobal(Object[]) A typed version
     */
    public static int[] convertToGlobal(final int[] local)
    {
        return LocalToGlobalConverter.IntConverter.get().apply(local);
    }

    /**
     * A specialised primitive int version of {@link LocalToGlobalConverter}
     */
    public static class IntConverter implements Function<int[], int[]>
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
         * Convert from local to global. Assumes this local juggler is the first to start in the global representation.
         *
         * @param local The local throws
         *
         * @return The global throws.
         *
         * @throws IllegalArgumentException if local is of even length as the result is undefined, since the other
         *                                  juggler
         *                                  could throw many things in response
         */
        @Override
        public int[] apply(final int[] local)
        {
            Objects.requireNonNull(local, "local cannot be null");

            if (local.length % 2 == 0)
            {
                throw new IllegalArgumentException("local must have odd length");
            }

            final int[] global = new int[local.length];

            int firstJuggler = 0, index = 0;// Pointer that will start from the first thrower
            final int secondJugglerStart = (local.length / 2) + 1; // The first throw of the second juggler
            int secondJuggler = secondJugglerStart; // Pointer that will start from the second thrower

            while (firstJuggler < secondJugglerStart)
            {
                global[index++] = local[firstJuggler++]; // Put in the first jugglers throw
                if (secondJuggler < local.length) // Second juggler has one less throw - so can't do last one
                {
                    global[index++] = local[secondJuggler++]; // Put in the second jugglers throw
                }
            }

            return global;
        }
    }
}
