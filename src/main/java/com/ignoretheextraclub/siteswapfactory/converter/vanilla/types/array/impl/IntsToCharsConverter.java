package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.IntToCharConverter;

import java.util.Objects;
import java.util.function.Function;

/**
 * Converts an array of ints to an array of chars. Guaranteed to not throw an exception
 *
 * @author Caspar Nonclercq
 */
public class IntsToCharsConverter implements Function<int[], char[]>
{
    private static IntsToCharsConverter INSTANCE;

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
     * @param thros the thros to apply
     *
     * @return the chars
     */
    @Override
    public char[] apply(final int[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final char[] charThrows = new char[thros.length];

        final IntToCharConverter intToCharConverter = IntToCharConverter.get();

        for (int i = 0; i < thros.length; i++)
        {
            charThrows[i] = intToCharConverter.apply(thros[i]);
        }

        return charThrows;
    }

    /**
     * Convenient static method to apply throws to chars
     *
     * @param thros the throws to apply
     *
     * @return the converted chars
     */
    public static char[] convert(final int[] thros)
    {
        return get().apply(thros);
    }
}
