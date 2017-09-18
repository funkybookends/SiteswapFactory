package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.function.Function;

/**
 * Converts a string to an int[]
 */
public class StringToIntsConverter implements Function<String, int[]>
{
    public static StringToIntsConverter INSTANCE;

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
     * @param siteswap
     * @return
     */
    @Override
    public int[] apply(final String siteswap)
    {
        return CharsToIntsConverter.get().apply(siteswap.toCharArray());
    }
}
