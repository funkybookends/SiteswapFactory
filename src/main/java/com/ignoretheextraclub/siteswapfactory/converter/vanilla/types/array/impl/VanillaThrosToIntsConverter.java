package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts a {@link VanillaThro}[] to an {@code int[]}
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

    @Override
    public int[] apply(final VanillaThro[] thros)
    {
        final int[] intThros = new int[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            intThros[i] = thros[i].getNumBeats();
        }

        return intThros;
    }
}
