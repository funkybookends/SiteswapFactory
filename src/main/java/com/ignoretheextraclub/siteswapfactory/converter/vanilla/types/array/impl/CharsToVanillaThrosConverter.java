package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * Converts a char[] to a VanillaThro[]
 *
 * @author Caspar Nonclercq
 */
public class CharsToVanillaThrosConverter implements Function<char[], VanillaThro[]>
{
    public static CharsToVanillaThrosConverter INSTANCE;

    private CharsToVanillaThrosConverter()
    {
        // Singleton
    }

    public static CharsToVanillaThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharsToVanillaThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char[] to a VanillaThro[]
     * @param thros the char[]
     * @return The vanilla throw array
     */
    @Override
    public VanillaThro[] apply(final char[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        try
        {
            final VanillaThro[] vanillaThros = new VanillaThro[thros.length];

            for (int i = 0; i < thros.length; i++)
            {
                vanillaThros[i] = VanillaThro.get(thros[i]);
            }

            return vanillaThros;
        }
        catch (final BadThrowException cause)
        {
            throw new BadThrowException("thros " + Arrays.toString(thros) + " contained an invalid throw", cause);
        }
    }

    /**
     * Convenient static method to apply chars to a VanillaThro array
     * @param chars the chars
     * @return the vanilla throw array
     */
    public static VanillaThro[] convertToVanillaThros(final char[] chars)
    {
        return get().apply(chars);
    }
}
