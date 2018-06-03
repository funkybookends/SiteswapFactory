package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Objects;
import java.util.function.Function;

/**
 * Converts a string to an int[]
 *
 * @author Caspar Nonclercq
 */
public class StringToIntsConverter implements Function<String, int[]>
{
    private static StringToIntsConverter INSTANCE;

    private StringToIntsConverter()
    {
        // Singleton
    }

    public static StringToIntsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToIntsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a string to an int[]
     *
     * @param thros a string to apply
     *
     * @return an array of ints
     */
    @Override
    public int[] apply(final String thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        return CharsToIntsConverter.get().apply(thros.toCharArray());
    }

    /**
     * Convenient static method to conver string to ints
     *
     * @param thros the string to apply
     *
     * @return the ints
     */
    public static int[] convert(final String thros)
    {
        return get().apply(thros);
    }
}
