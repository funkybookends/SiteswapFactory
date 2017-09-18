package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.function.Function;

/**
 * Converts an int to a FourHandedSiteswapThro
 */
public class IntToFourHandedSiteswapThroConverter implements Function<Integer, FourHandedSiteswapThro>
{
    public static IntToFourHandedSiteswapThroConverter INSTANCE;

    private IntToFourHandedSiteswapThroConverter()
    {
        // Singleton
    }

    public static IntToFourHandedSiteswapThroConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntToFourHandedSiteswapThroConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an int to a FourHandedSiteswapThro
     * @param integer an int
     * @return a FourHandedSiteswapThro
     */
    @Override
    public FourHandedSiteswapThro apply(final Integer integer)
    {
        return FourHandedSiteswapThro.getUnchecked(integer);
    }
}
