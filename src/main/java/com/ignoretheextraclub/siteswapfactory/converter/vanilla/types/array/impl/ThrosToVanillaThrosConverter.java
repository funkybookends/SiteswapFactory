package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Casts {@link Thro} to {@link VanillaThro}.
 *
 * @author Caspar Nonclercq
 */
public class ThrosToVanillaThrosConverter implements Function<Thro[], VanillaThro[]>
{
    private static ThrosToVanillaThrosConverter INSTANCE;

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

    /**
     * Converts an array of throws to an array of VanillaThros
     *
     * @param thros the thros array
     *
     * @return the VanillaThros array
     */
    @Override
    public VanillaThro[] apply(final Thro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final VanillaThro[] result = new VanillaThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = VanillaThro.get(thros[i].getNumBeats());
        }

        return result;
    }

    /**
     * Convenient static method to apply an array of throws to an array of VanillaThros
     *
     * @param thros the thros array
     *
     * @return the VanillaThros array
     */
    public static VanillaThro[] convert(final Thro[] thros)
    {
        return get().apply(thros);
    }
}
