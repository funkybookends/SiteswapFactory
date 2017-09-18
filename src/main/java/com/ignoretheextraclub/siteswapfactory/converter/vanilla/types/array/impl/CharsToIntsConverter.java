package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.CharToIntConverter;

import java.util.function.Function;

/**
 * Converts a char[] to an int[]
 */
public class CharsToIntsConverter implements Function<char[], int[]>
{
    public static CharsToIntsConverter INSTANCE;

    private CharsToIntsConverter()
    {
        // Singleton
    }

    public static CharsToIntsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharsToIntsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char[] to an int[]
     * @param charThrows an array of chars
     * @return an array of ints
     */
    @Override
    public int[] apply(final char[] charThrows)
    {
        final int[] intThrows = new int[charThrows.length];

        final CharToIntConverter charToIntConverter = CharToIntConverter.get();

        for (int i = 0; i < charThrows.length; i++)
        {
            intThrows[i] = charToIntConverter.apply(charThrows[i]);
        }

        return intThrows;
    }
}
