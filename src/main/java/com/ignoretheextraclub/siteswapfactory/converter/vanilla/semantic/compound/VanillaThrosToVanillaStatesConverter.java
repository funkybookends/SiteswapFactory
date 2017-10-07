package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.compound;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToAllStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Determines the {@code VanillaState[]} a given {@code VanillaThro[]} corresponds to.
 * <p>
 * This is a composite converter that uses {@link VanillaThrosToStartingStateConverter} to determine the first state, and
 * then uses {@link StartingStateAndThrosToAllStatesConverter} to get all the states, and then uses {@link
 * StatesToVanillaStatesConverter} to get the result as a {@code VanillaState[]}
 *
 * @author Caspar Nonclercq
 */
public class VanillaThrosToVanillaStatesConverter implements Function<VanillaThro[], VanillaState[]>
{
    public static VanillaThrosToVanillaStatesConverter INSTANCE;

    private VanillaThrosToVanillaStatesConverter()
    {
        // Singleton
    }

    public static VanillaThrosToVanillaStatesConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToVanillaStatesConverter();
        }
        return INSTANCE;
    }

    /**
     * Given a {@link VanillaThro}[] determines the {@link VanillaState}[] it corresponds to.
     *
     * @param thros The set of throws
     *
     * @return The set of states this corresponds to.
     *
     * @throws com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException if not valid.
     * @see #convertThrosToStates(VanillaThro[]) A static method.
     */
    @Override
    public VanillaState[] apply(final VanillaThro[] thros)
    {
        try
        {
            final VanillaState firstState = VanillaThrosToStartingStateConverter.get().apply(thros);
            final State[] states = StartingStateAndThrosToAllStatesConverter.get().apply(firstState, thros);
            return StatesToVanillaStatesConverter.get().apply(states);
        }
        catch (final InvalidSiteswapException cause)
        {
            throw new InvalidSiteswapException("Throws [" + Arrays.toString(thros) + "] are not a valid siteswap", cause);
        }
    }

    /**
     * Convenient static method for calling the {@link #apply(VanillaThro[])} method.
     *
     * @param thros The set of throws
     *
     * @return The set of states this corresponds to.
     *
     * @throws com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException if not valid.
     */
    public static VanillaState[] convertThrosToStates(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
