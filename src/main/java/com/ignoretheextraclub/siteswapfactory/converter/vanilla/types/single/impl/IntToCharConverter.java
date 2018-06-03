package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts an int to a char. Guaranteed to not throw an exception, returns '?' if not valid.
 * @author Caspar Nonclercq
 */
public class IntToCharConverter implements Function<Integer, Character>
{
    private static IntToCharConverter INSTANCE;

    private IntToCharConverter()
    {
        // Singleton
    }

    public static IntToCharConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntToCharConverter();
        }
        return INSTANCE;
    }

    /**
     *
     * @param thro
     * @return
     */
    @Override
    public Character apply(final Integer thro)
    {
        if (thro == null || thro < 0)
        {
            return VanillaThro.INVALID_CHAR;
        }
        else if (thro < 10)
        {
            return (char) (thro + '0');
        }
        else if (thro < 36)
        {
            return (char) (thro + 'A' - 10);
        }
        else
        {
            return VanillaThro.INVALID_CHAR;
        }
    }
}
