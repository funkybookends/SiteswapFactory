package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.Objects;
import java.util.function.Function;

/**
 * Converts a {@link VanillaThro}[] to an {@code int[]}
 *
 * @author Caspar Nonclercq
 */
public class VanillaThrosToIntsConverter implements Function<VanillaThro[], int[]>
{
    public static VanillaThrosToIntsConverter INSTANCE;

    private VanillaThrosToIntsConverter()
    {
        // Singleton
    }

    public static VanillaThrosToIntsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToIntsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an array of vanilla thros to ints
     *
     * @param thros the vanilla thros
     *
     * @return the array of ints
     */
    @Override
    public int[] apply(final VanillaThro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final int[] intThros = new int[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            intThros[i] = thros[i].getNumBeats();
        }

        return intThros;
    }

    /**
     * Convenient static method to apply an array of vanilla thros to ints
     *
     * @param thros the vanilla thros
     *
     * @return the array of ints
     */
    public static int[] convert(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
