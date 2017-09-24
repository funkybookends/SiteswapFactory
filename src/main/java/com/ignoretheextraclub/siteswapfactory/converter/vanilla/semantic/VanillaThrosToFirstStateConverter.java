package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;

import java.util.function.Function;

/**
 * Returns the first state from a set of {@link VanillaThro}s. Uses {@link VanillaThrosToFirstStateBiConverter} and
 * calculates the number of objects required by taking the average of all the throws.
 *
 * @author Caspar Nonclercq
 * @see VanillaThrosToFirstStateBiConverter Use this directly if you already know the number of objects
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

    /**
     * Gets the first state from an array of {@link VanillaThro}. Calculates the number of objects by taking the average
     * of the throws.
     *
     * @param thros The thros from which to calculate the first state
     *
     * @return The first state.
     */
    @Override
    public VanillaState apply(final VanillaThro[] thros)
    {
        return VanillaThrosToFirstStateBiConverter.get().apply(thros, VanillaThroUtils.numObjects(thros));
    }

    /**
     * Conveneint static method to get the first state from an array of {@link VanillaThro}.
     *
     * @param thros The thros from which to calculate the first state
     *
     * @return The first state
     *
     * @see #apply(VanillaThro[])
     */
    public static VanillaState getFirstState(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
