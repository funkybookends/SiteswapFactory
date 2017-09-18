package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Casts {@link Thro} to {@link VanillaThro}.
 */
public class ThrosToVanillaThrosConverter implements Function<Thro[], VanillaThro[]>
{
    public static ThrosToVanillaThrosConverter INSTANCE;

    private ThrosToVanillaThrosConverter()
    {
        // Singleton
    }

    public static ThrosToVanillaThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ThrosToVanillaThrosConverter();
        }
        return INSTANCE;
    }

    @Override
    public VanillaThro[] apply(final Thro[] thros)
    {
        final VanillaThro[] result = new VanillaThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = (VanillaThro) thros[i];
        }

        return result;
    }

    public static VanillaThro[] convert(final Thro[] thros)
    {
        return get().apply(thros);
    }
}
