package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * Converts a char[] to a FourHandedSiteswapThro[]
 *
 * @author Caspar Nonclercq
 */
public class CharsToFourHandedSiteswapThrosConverter implements Function<char[], FourHandedSiteswapThro[]>
{
    public static CharsToFourHandedSiteswapThrosConverter INSTANCE;

    private CharsToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static CharsToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharsToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char[] to a FourHandedSiteswapThro[]
     *
     * @param thros the thros to convert
     *
     * @return the converted thros
     */
    @Override
    public FourHandedSiteswapThro[] apply(final char[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        try
        {
            final FourHandedSiteswapThro[] fourHandedSiteswapThros = new FourHandedSiteswapThro[thros.length];

            for (int i = 0; i < thros.length; i++)
            {
                fourHandedSiteswapThros[i] = FourHandedSiteswapThro.get(thros[i]);
            }

            return fourHandedSiteswapThros;
        }
        catch (final BadThrowException cause)
        {
            throw new BadThrowException("thros " + Arrays.toString(thros) + " contained a bad throw", cause);
        }
    }

    /**
     * Convenient static method to convert thros
     *
     * @param thros the thros to convert
     *
     * @return the converted thros
     */
    public static FourHandedSiteswapThro[] convert(final char[] thros)
    {
        return get().apply(thros);
    }
}
