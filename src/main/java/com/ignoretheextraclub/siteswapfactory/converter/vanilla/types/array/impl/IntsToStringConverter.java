package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.function.Function;

/**
 * Converts an int[] to a string
 *
 * @author Caspar Nonclercq
 */
public class IntsToStringConverter implements Function<int[], String>
{
    private static IntsToStringConverter INSTANCE;

    private IntsToStringConverter()
    {
        // Singleton
    }

    public static IntsToStringConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntsToStringConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an int[] to a string
     *
     * @param ints ints to apply
     *
     * @return a string
     */
    @Override
    public String apply(final int[] ints)
    {
        return new String(IntsToCharsConverter.get().apply(ints));
    }

    /**
     * Convenient static method to apply ints to string
     *
     * @param ints the ints to apply
     *
     * @return the string
     */
    public static String convert(final int[] ints)
    {
        return get().apply(ints);
    }
}
