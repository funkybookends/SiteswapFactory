package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.CharsToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.function.Function;

/**
 * Converts a char[] to a FourHandedSiteswapThro[]
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
     * @param chars
     * @return
     */
    @Override
    public FourHandedSiteswapThro[] apply(final char[] chars)
    {
        return convertToInts().andThen(convertToFHSThros()).apply(chars);
    }

    private IntsToFourHandedSiteswapThrosConverter convertToFHSThros()
    {
        return IntsToFourHandedSiteswapThrosConverter.get();
    }

    private CharsToIntsConverter convertToInts()
    {
        return CharsToIntsConverter.get();
    }
}
