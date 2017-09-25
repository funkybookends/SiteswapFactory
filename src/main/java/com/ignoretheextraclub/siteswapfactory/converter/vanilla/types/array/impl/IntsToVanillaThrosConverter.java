package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.Objects;
import java.util.function.Function;

/**
 * Converts an int[] to a VanillaThro[]
 *
 * @author Caspar Nonclercq
 */
public class IntsToVanillaThrosConverter implements Function<int[], VanillaThro[]>
{
    public static IntsToVanillaThrosConverter INSTANCE;

    private IntsToVanillaThrosConverter()
    {
        // Singleton
    }

    public static IntsToVanillaThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntsToVanillaThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an int[] to a VanillaThro[]
     *
     * @param thros the ints to convert
     *
     * @return an array of VanillaThros
     */
    @Override
    public VanillaThro[] apply(final int[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final VanillaThro[] vanillaThros = new VanillaThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            vanillaThros[i] = VanillaThro.get(thros[i]);
        }
        return vanillaThros;
    }

    /**
     * Convenient static method to convert ints to VanillaThros
     *
     * @param thros the ints to conver
     *
     * @return the VanillaThros
     */
    public static VanillaThro[] convert(final int[] thros)
    {
        return get().apply(thros);
    }
}
