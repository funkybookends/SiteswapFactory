package com.ignoretheextraclub.siteswapfactory.converter.vanilla.prechac;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts an array of {@link VanillaThro} to their Prechac Representation. Does not effect
 * the ordering, and therefore likely users want to use {@link com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.GlobalToLocalBiConverter}
 * to get the local representation.
 * @author Caspar Nonclercq
 */
public class VanillaThrosToPrechacConverter implements Function<VanillaThro[], String>
{
    public static VanillaThrosToPrechacConverter INSTANCE;

    private static final String DELIMETER = " ";

    private VanillaThrosToPrechacConverter()
    {
        // Singleton
    }

    public static VanillaThrosToPrechacConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToPrechacConverter();
        }
        return INSTANCE;
    }

    @Override
    public String apply(VanillaThro[] vanillaThros)
    {
        return Arrays.stream(vanillaThros)
            .map(VanillaThroToPrechacConverter.get())
            .collect(Collectors.joining(DELIMETER));
    }
}
