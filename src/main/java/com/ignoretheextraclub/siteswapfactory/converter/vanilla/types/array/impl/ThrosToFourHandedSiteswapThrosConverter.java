package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.Objects;
import java.util.function.Function;

/**
 * Downcasts a Thro[] to a FourHandedSiteswapThro[]
 *
 * @author Caspar Nonclercq
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
     *
     * @param thros a Thro array
     *
     * @return a FourHandedSiteswapThro array
     */
    @Override
    public FourHandedSiteswapThro[] apply(final Thro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final FourHandedSiteswapThro[] result = new FourHandedSiteswapThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = FourHandedSiteswapThro.get(thros[i].getNumBeats());
        }

        return result;
    }

    /**
     * Convenient static method to conver thros to FourHandedSiteswapThros
     *
     * @param thros the throw array
     *
     * @return the FourHandedSiteswap array
     */
    public static FourHandedSiteswapThro[] convert(final Thro[] thros)
    {
        return get().apply(thros);
    }
}
