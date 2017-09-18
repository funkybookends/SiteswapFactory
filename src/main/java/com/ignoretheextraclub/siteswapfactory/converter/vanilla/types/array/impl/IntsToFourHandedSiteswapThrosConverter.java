package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.function.Function;

/**
 * Converts an int[] to a FourHandedSiteswap[]
 */
public class IntsToFourHandedSiteswapThrosConverter implements Function<int[], FourHandedSiteswapThro[]>
{
    public static IntsToFourHandedSiteswapThrosConverter INSTANCE;

    private IntsToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static IntsToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntsToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an int[] to a FourHandedSiteswap[]
     * @param siteswap
     * @return
     */
    @Override
    public FourHandedSiteswapThro[] apply(final int[] siteswap)
    {
        final FourHandedSiteswapThro[] thros = new FourHandedSiteswapThro[siteswap.length];

        for (int i = 0; i < siteswap.length; i++)
        {
            thros[i] = FourHandedSiteswapThro.getUnchecked(siteswap[i]);
        }

        return thros;
    }
}
