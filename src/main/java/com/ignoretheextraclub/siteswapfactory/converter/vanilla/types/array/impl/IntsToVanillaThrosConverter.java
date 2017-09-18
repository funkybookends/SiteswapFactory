package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Converts an int[] to a VanillaThro[]
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
     * @param siteswap
     * @return
     */
    @Override
    public VanillaThro[] apply(final int[] siteswap)
    {
        final VanillaThro[] thros = new VanillaThro[siteswap.length];
        for (int i = 0; i < siteswap.length; i++)
        {
            thros[i] = VanillaThro.getUnchecked(siteswap[i]);
        }
        return thros;
    }
}
