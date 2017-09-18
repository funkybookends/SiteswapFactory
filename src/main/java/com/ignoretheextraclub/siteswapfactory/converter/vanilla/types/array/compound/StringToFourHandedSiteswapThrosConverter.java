package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StringToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import java.util.function.Function;

/**
 * Created by caspar on 17/09/17.
 */
public class StringToFourHandedSiteswapThrosConverter implements Function<String, FourHandedSiteswapThro[]>
{
    public static StringToFourHandedSiteswapThrosConverter INSTANCE;

    private StringToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static StringToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    @Override
    public FourHandedSiteswapThro[] apply(final String siteswap)
    {
        return convertToInts().andThen(convertToFourHandedSiteswapThros()).apply(siteswap);
    }

    private IntsToFourHandedSiteswapThrosConverter convertToFourHandedSiteswapThros()
    {
        return IntsToFourHandedSiteswapThrosConverter.get();
    }

    private StringToIntsConverter convertToInts()
    {
        return StringToIntsConverter.get();
    }
}
