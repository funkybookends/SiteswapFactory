package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts a char to an int. Guaranteed to not throwan exception, returns -1 if not a valid char.
 *
 * @author Caspar Nonclercq
 */
public class CharToIntConverter implements Function<Character, Integer>
{
    public static CharToIntConverter INSTANCE;

    private CharToIntConverter()
    {
        // Singleton
    }

    public static CharToIntConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharToIntConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a character to an int
     *
     * @param thro a thro
     *
     * @return an int, -1 if invalid.
     */
    @Override
    public Integer apply(final Character thro)
    {
        if (thro >= '0' && thro <= '9')
        {
            return thro - '0';
        }
        else if (thro >= 'A' && thro <= 'Z')
        {
            return thro - 'A' + 10;
        }
        else if (thro >= 'a' && thro <= 'z')
        {
            return thro - 'a' + 10;
        }
        else
        {
            return VanillaThro.INVALID_INT;
        }
    }

    public static Integer convert(final Character thro)
    {
        return get().apply(thro);
    }
}
