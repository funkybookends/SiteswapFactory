package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;

import java.util.function.Function;

/**
 * // TODO
 */
public class VanillaThrosToFirstStateConverter implements Function<VanillaThro[], VanillaState>
{
    public static VanillaThrosToFirstStateConverter INSTANCE;

    private VanillaThrosToFirstStateConverter()
    {
        // Singleton
    }

    public static VanillaThrosToFirstStateConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToFirstStateConverter();
        }
        return INSTANCE;
    }

    @Override
    public VanillaState apply(final VanillaThro[] thros)
    {
        return ThrosToFirstStateBiConverter.get().apply(thros, VanillaThroUtils.numObjects(thros));
    }
}
