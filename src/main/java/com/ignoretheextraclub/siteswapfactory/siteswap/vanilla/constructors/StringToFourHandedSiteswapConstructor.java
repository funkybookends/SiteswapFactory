package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToAllStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;

/**
 * Creates a {@link FourHandedSiteswap} give a {@link SiteswapRequest}. Respects the reducing and sorting preferences.
 *
 * @author Caspar Nonclercq
 */
public class StringToFourHandedSiteswapConstructor implements SiteswapConstructor<FourHandedSiteswap>
{
    public static StringToFourHandedSiteswapConstructor INSTANCE;

    private StringToFourHandedSiteswapConstructor()
    {
        // Singleton
    }

    public static StringToFourHandedSiteswapConstructor get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToFourHandedSiteswapConstructor();
        }
        return INSTANCE;
    }


    @Override
    public FourHandedSiteswap apply(final SiteswapRequest siteswapRequest)
    {
        final String siteswap = siteswapRequest.getConstructor().toString();
        FourHandedSiteswapThro[] fourHandedSiteswapThros = StringToFourHandedSiteswapThrosConverter.convert(siteswap);

        if (siteswapRequest.getReducer().isPresent())
        {
            fourHandedSiteswapThros = siteswapRequest.getReducer().get().reduce(fourHandedSiteswapThros);
        }

        final VanillaState startingState = VanillaThrosToStartingStateConverter.getFirstState(fourHandedSiteswapThros);
        State[] allStates = StartingStateAndThrosToAllStatesConverter.getAllStates(startingState, fourHandedSiteswapThros);

        if (siteswapRequest.getStartFinder().isPresent())
        {
            final StartFinderResult startFinderResult = siteswapRequest.getStartFinder().get().sort(allStates, siteswapRequest.getStartingStrategy());
            allStates = startFinderResult.getSorted();
        }

        final VanillaState[] vanillaStates = StatesToVanillaStatesConverter.convert(allStates);

        return new FourHandedSiteswap(vanillaStates);
    }

    @Override
    public boolean accepts(final Object object)
    {
        return String.class.isInstance(object);
    }

    @Override
    public String toString()
    {
        return "StringToFourHandedSiteswapConstructor{}";
    }
}