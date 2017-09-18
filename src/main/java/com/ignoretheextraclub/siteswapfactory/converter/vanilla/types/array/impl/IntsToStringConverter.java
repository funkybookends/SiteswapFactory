package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.function.Function;

/**
 * Converts an int[] to a string
 */
public class IntsToStringConverter implements Function<int[], String>
{
    public static IntsToStringConverter INSTANCE;

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
     * @param ints
     * @return
     */
    @Override
    public String apply(final int[] ints)
    {
        return new String(IntsToCharsConverter.get().apply(ints));
    }
}
