package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Returns the shortest repeating unit that when repeated n times would return the input array.
 * <p>
 * <pre>
 *     [A, B, C, A, B, C, A, B, C] -> [A, B, C]
 *     [A, B]                      -> [A, B]
 * </pre> // TODO move doc up to interface
 *
 *
 * @author Caspar Nonclercq
 * @see IntReducer A primitive int version
 */
public class SimpleReducer implements Reducer
{
    public static SimpleReducer INSTANCE;

    private SimpleReducer()
    {
        // Singleton
    }

    public static SimpleReducer get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new SimpleReducer();
        }
        return INSTANCE;
    }

    /**
     * Returns the reduced version of an array.
     * <p>
     * <pre>
     *     [A, B, C, A, B, C, A, B, C] -> [A, B, C]
     *     [A, B]                      -> [A, B]
     * </pre>
     *
     * @param duplicated The repeating array
     *
     * @return An new array that does not repeat or the original array
     */
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