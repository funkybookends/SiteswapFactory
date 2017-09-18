package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.CharsToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts a char[] to a VanillaThro[]
 *
 * @author Caspar Nonclercq
 */
public class CharArrayToVanillaThroArrayConverter implements Function<char[], VanillaThro[]>
{
    public static CharArrayToVanillaThroArrayConverter INSTANCE;

    private CharArrayToVanillaThroArrayConverter()
    {
        // Singleton
    }

    public static CharArrayToVanillaThroArrayConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharArrayToVanillaThroArrayConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char[] to a VanillaThro[]
     */
    @Override
    public VanillaThro[] apply(final char[] chars)
    {
        return convertToInts().andThen(convertToVanillaThros()).apply(chars);
    }

    private IntsToVanillaThrosConverter convertToVanillaThros()
    {
        return IntsToVanillaThrosConverter.get();
    }

    private CharsToIntsConverter convertToInts()
    {
        return CharsToIntsConverter.get();
    }
}
