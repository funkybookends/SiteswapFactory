package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.function.Function;

/**
 * Converts a char to a FourHandedSiteswapThro
 */
public class CharToFourHandedSiteswapThroConverter implements Function<Character, FourHandedSiteswapThro>
{
    public static CharToFourHandedSiteswapThroConverter INSTANCE;

    private CharToFourHandedSiteswapThroConverter()
    {
        // Singleton
    }

    public static CharToFourHandedSiteswapThroConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharToFourHandedSiteswapThroConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char to a FourHandedSiteswapThro
     * @param character
     * @return
     */
    @Override
    public FourHandedSiteswapThro apply(final Character character)
    {
        return FourHandedSiteswapThro.getUnchecked(character);
    }
}
