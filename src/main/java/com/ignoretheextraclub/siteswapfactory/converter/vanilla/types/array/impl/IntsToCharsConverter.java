package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.IntToCharConverter;

import java.util.function.Function;

/**
 * Converts an array of ints to an array of chars. Guaranteed to not throw an exception
 *
 * It is your responsibility to catch invalid throws.
 */
public class IntsToCharsConverter implements Function<int[], char[]>
{
    public static IntsToCharsConverter INSTANCE;

    private IntsToCharsConverter()
    {
        // Singleton
    }

    public static IntsToCharsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntsToCharsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an array of ints to an array of chars. Guaranteed to not throw an exception
     *
     * It is your responsibility to catch invalid throws.
     */
    @Override
    public char[] apply(final int[] intThrows)
    {
        final char[] charThrows = new char[intThrows.length];

        final IntToCharConverter intToCharConverter = IntToCharConverter.get();

        for (int i = 0; i < intThrows.length; i++)
        {
            charThrows[i] = intToCharConverter.apply(intThrows[i]);
        }

        return charThrows;
    }
}
