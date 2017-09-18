package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts a char to an int. Guaranteed to not throwan exception, returns -1 if not a valid char.
 * It is your responsibility to catch invalid throws.
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
}
