package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;

/**
 * Returns the first state from a set of {@link VanillaThro}s. Uses {@link VanillaThrosAndNumObjectsToFirstStateBiConverter} and
 * calculates the number of objects required by taking the average of all the throws.
 *
 * @author Caspar Nonclercq
 * @see VanillaThrosAndNumObjectsToFirstStateBiConverter Use this directly if you already know the number of objects
 */
public class VanillaThrosToStartingStateConverter implements Function<VanillaThro[], VanillaState>
{
    public static VanillaThrosToStartingStateConverter INSTANCE;

    private VanillaThrosToStartingStateConverter()
    {
        // Singleton
    }

    public static VanillaThrosToStartingStateConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToStartingStateConverter();
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
        return VanillaThrosAndNumObjectsToFirstStateBiConverter.get().apply(thros, VanillaThroUtils.numObjects(thros));
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
