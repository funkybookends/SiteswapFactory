package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import java.util.Optional;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToAllStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;

/**
 * Creates a {@link TwoHandedSiteswap} give a {@link SiteswapRequest}. Respects the reducing and sorting preferences.
 *
 * @author Caspar Nonclercq
 */
public class StringToTwoHandedSiteswapConstructor implements SiteswapConstructor<TwoHandedSiteswap>
{
    public static StringToTwoHandedSiteswapConstructor INSTANCE;

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
        VanillaThro[] vanillaThros = StringToVanillaThrosConverter.convert(siteswap);

        final Optional<Reducer> reducer = siteswapRequest.getReducer();
        if (reducer.isPresent())
        {
            vanillaThros = reducer.get().reduce(vanillaThros);
        }

        final VanillaState startingState = VanillaThrosToStartingStateConverter.getFirstState(vanillaThros);
        State[] allStates = StartingStateAndThrosToAllStatesConverter.getAllStates(startingState, vanillaThros);

        final Optional<StartFinder> startFinder = siteswapRequest.getStartFinder();
        if (startFinder.isPresent())
        {
            final StartFinderResult startFinderResult = startFinder.get().sort(allStates, siteswapRequest.getStartingStrategy());
            allStates = startFinderResult.getSorted();
        }

        final VanillaState[] vanillaStates = StatesToVanillaStatesConverter.convert(allStates);

        return new TwoHandedSiteswap(vanillaStates);
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
