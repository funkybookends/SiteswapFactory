package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Created by caspar on 14/09/17.
 */
public class IntToVanillaThroConverter implements Function<Integer, VanillaThro>
{
    public static IntToVanillaThroConverter INSTANCE;

    private IntToVanillaThroConverter()
    {
        // Singleton
    }

    public static IntToVanillaThroConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntToVanillaThroConverter();
        }
        return INSTANCE;
    }

    @Override
    public VanillaThro apply(final Integer i)
    {
        return VanillaThro.getUnchecked(i);
    }
}
