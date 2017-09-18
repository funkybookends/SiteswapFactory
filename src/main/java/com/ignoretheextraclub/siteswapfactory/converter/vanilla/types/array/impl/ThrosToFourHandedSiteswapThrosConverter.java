package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.function.Function;

/**
 * Downcasts a Thro[] to a FourHandedSiteswapThro[]
 */
public class ThrosToFourHandedSiteswapThrosConverter implements Function<Thro[], FourHandedSiteswapThro[]>
{
    public static ThrosToFourHandedSiteswapThrosConverter INSTANCE;

    private ThrosToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static ThrosToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ThrosToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Downcasts a Thro[] to a FourHandedSiteswapThro[]
     * @param thros a Thro array
     * @return a FourHandedSiteswapThro array
     */
    @Override
    public FourHandedSiteswapThro[] apply(final Thro[] thros)
    {
        final FourHandedSiteswapThro[] result = new FourHandedSiteswapThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = FourHandedSiteswapThro.getUnchecked(thros[i].getNumBeats());
        }

        return result;
    }
}
