package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Creates a {@link TwoHandedSiteswap} give a {@link SiteswapRequest}. Respects the reducing and sorting preferences.
 *
 * @author Caspar Nonclercq
 */
public class StringToTwoHandedSiteswapConstructor implements SiteswapConstructor<TwoHandedSiteswap>
{
    private static StringToTwoHandedSiteswapConstructor INSTANCE;

    private StringToTwoHandedSiteswapConstructor()
    {
        // Singleton
    }

    public static StringToTwoHandedSiteswapConstructor get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToTwoHandedSiteswapConstructor();
        }
        return INSTANCE;
    }

    @Override
    public TwoHandedSiteswap apply(final SiteswapRequest siteswapRequest)
    {
        final String siteswap = siteswapRequest.getConstructor().toString();
        final VanillaThro[] vanillaThros = siteswapRequest.getReducer().reduce(StringToVanillaThrosConverter.convert(siteswap));
        final VanillaState startingState = VanillaThrosToStartingStateConverter.getFirstState(vanillaThros);
        final GeneralPath generalPath = GeneralPath.from(startingState, vanillaThros);
        final GeneralCircuit generalCircuit = generalPath.toGeneralCircuit();
        return GeneralCircuitToTwoHandedSiteswapConstructor.get().apply(siteswapRequest.toBuilder().createSiteswapRequest(generalCircuit));
    }

    @Override
    public boolean accepts(final Object object)
    {
        return String.class.isInstance(object);
    }

    @Override
    public String toString()
    {
        return "StringToTwoHandedSiteswapConstructor{}";
    }
}
