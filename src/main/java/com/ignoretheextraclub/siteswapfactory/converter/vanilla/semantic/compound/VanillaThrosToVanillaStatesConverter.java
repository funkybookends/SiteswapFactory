package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.compound;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToAllStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToFirstStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.function.Function;

/**
 * Determines the {@link VanillaState}[] a given {@link VanillaThro}[] corresponds to.
 * <p>
 * This is a composite converter that uses {@link VanillaThrosToFirstStateConverter} to determine the first state, and
 * then uses {@link StartingStateAndThrosToAllStatesConverter} to get all the states, and then uses {@link
 * StatesToVanillaStatesConverter} to get the result as a VanillaState[]
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
     * @see #convertThrosToStates(VanillaThro[]) for a static version.
     */
    @Override
    public VanillaState[] apply(final VanillaThro[] thros)
    {
        final VanillaState firstState = VanillaThrosToFirstStateConverter.get().apply(thros);
        final State[] states = StartingStateAndThrosToAllStatesConverter.get().apply(firstState, thros);
        return StatesToVanillaStatesConverter.get().apply(states);
    }

    /**
     * Convenient static method for calling the {@link #apply(VanillaThro[])} method.
     *
     * @param thros The set of throws
     *
     * @return The set of states this corresponds to.
     *
     * @throws com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException if not valid.
     * @see #apply(VanillaThro[]) for the implementation
     */
    public static VanillaState[] convertThrosToStates(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
