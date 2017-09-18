package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StringToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Created by caspar on 14/09/17.
 */
public class StringToVanillaThrosConverter implements Function<String, VanillaThro[]>
{
    public static StringToVanillaThrosConverter INSTANCE;

    private StringToVanillaThrosConverter()
    {
        // Singleton
    }

    public static StringToVanillaThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToVanillaThrosConverter();
        }
        return INSTANCE;
    }

    @Override
    public VanillaThro[] apply(final String siteswap)
    {
        return convertToInts().andThen(convertToVanillaThros()).apply(siteswap);
    }

    private IntsToVanillaThrosConverter convertToVanillaThros()
    {
        return IntsToVanillaThrosConverter.get();
    }

    private StringToIntsConverter convertToInts()
    {
        return StringToIntsConverter.get();
    }
}
