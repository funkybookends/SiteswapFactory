package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToStringConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.VanillaThrosToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts a VanillaThro[] to a String
 *
 * @author Caspar Nonclercq
 */
public class VanillaThrosToStringConverter implements Function<VanillaThro[], String>
{
    public static VanillaThrosToStringConverter INSTANCE;

    private VanillaThrosToStringConverter()
    {
        // Singleton
    }

    public static VanillaThrosToStringConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToStringConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a VanillaThro[] to a String
     *
     * @param thros The throws
     *
     * @return The string representation
     */
    @Override
    public String apply(final VanillaThro[] thros)
    {
        return convertToInts().andThen(convertToString()).apply(thros);
    }

    private IntsToStringConverter convertToString()
    {
        return IntsToStringConverter.get();
    }

    private VanillaThrosToIntsConverter convertToInts()
    {
        return VanillaThrosToIntsConverter.get();
    }

    /**
     * Convenient static method to convert thros to a string
     *
     * @param thros The throws
     *
     * @return A string representation
     */
    public static String toString(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
