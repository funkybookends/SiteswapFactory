package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.lang.reflect.Array;
import java.util.function.Function;

/**
 * Converts from local to global.
 * <p>
 * Local is where each throw is in the order of a single thrower, whereas global is from the view of the whole pattern.
 * This makes sense in cases where there are two jugglers, and each throw in turn as in {@link
 * com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap}s.
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
     */
    @Override
    public T[] apply(final T[] local)
    {
        @SuppressWarnings("unchecked")
        final T[] global = (T[]) Array.newInstance(local.getClass().getComponentType(), local.length);

        int fromStart = 0;
        int fromMiddle = local.length / 2;

        if (local.length % 2 == 1)
        {
            fromMiddle++;
        }

        int insertionIndex = 0;

        while (fromStart < (local.length / 2) + 1)
        {
            global[insertionIndex] = local[fromStart];

            insertionIndex++;

            if (fromMiddle < local.length)
            {
                global[insertionIndex] = local[fromMiddle]; //incase odd
            }

            insertionIndex++;
            fromStart++;
            fromMiddle++;
        }

        return global;
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

        @Override
        public int[] apply(final int[] local)
        {
            final int[] global = new int[local.length];

            int fromStart = 0;
            int fromMiddle = local.length / 2;

            if (local.length % 2 == 1)
            {
                fromMiddle++;
            }

            int insertionIndex = 0;

            while (fromStart < (local.length / 2) + 1)
            {
                global[insertionIndex] = local[fromStart];
                insertionIndex++;

                if (fromMiddle < local.length)
                {
                    global[insertionIndex] = local[fromMiddle]; //incase odd
                }

                insertionIndex++;
                fromStart++;
                fromMiddle++;
            }

            return global;
        }
    }
}
