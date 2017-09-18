package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts a char to a VanillaThro
 */
public class CharToVanillaThroConverter implements Function<Character, VanillaThro>
{
    public static CharToVanillaThroConverter INSTANCE;

    private CharToVanillaThroConverter()
    {
        // Singleton
    }

    public static CharToVanillaThroConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharToVanillaThroConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char to a VanillaThro
     * @param character
     * @return
     */
    @Override
    public VanillaThro apply(final Character character)
    {
        return VanillaThro.getUnchecked(character);
    }
}
