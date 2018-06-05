package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

/**
 * Converts an int[] to a FourHandedSiteswapThro[]
 *
 * @author Caspar Nonclercq
 */
public class IntsToFourHandedSiteswapThrosConverter implements Function<int[], FourHandedSiteswapThro[]>
{
    private static IntsToFourHandedSiteswapThrosConverter INSTANCE;

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
     * Converts an int[] to a FourHandedSiteswapThro[]
     *
     * @param thros an array of ints
     *
     * @return an array of FourHandedSiteswapThros
     */
    @Override
    public FourHandedSiteswapThro[] apply(final int[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final FourHandedSiteswapThro[] fourHandedSiteswapThros = new FourHandedSiteswapThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            fourHandedSiteswapThros[i] = FourHandedSiteswapThro.get(thros[i]);
        }

        return fourHandedSiteswapThros;
    }

    /**
     * Convenient static method to apply ints to {@link FourHandedSiteswapThro}s
     *
     * @param thros ints
     *
     * @return FourHandedSiteswapThros
     */
    public static FourHandedSiteswapThro[] convert(final int[] thros)
    {
        return get().apply(thros);
    }
}
