package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;

/**
 * Creates a {@link FourHandedSiteswap} given a {@link State[]}.
 * @author Caspar Nonclercq
 */
public class StatesToFourHandedSiteswapConstructor implements SiteswapConstructor<FourHandedSiteswap>
{
    public static StatesToFourHandedSiteswapConstructor INSTANCE;

    private StatesToFourHandedSiteswapConstructor()
    {
        // Singleton
    }

    public static StatesToFourHandedSiteswapConstructor get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StatesToFourHandedSiteswapConstructor();
        }
        return INSTANCE;
    }

    @Override
    public FourHandedSiteswap apply(final SiteswapRequest siteswapRequest)
    {
        State[] states = (State[]) siteswapRequest.getConstructor();

        if (siteswapRequest.getReducer().isPresent())
        {
            states = siteswapRequest.getReducer().get().reduce(states);
        }

        if (siteswapRequest.getStartFinder().isPresent())
        {
            final StartFinderResult sort = siteswapRequest.getStartFinder().get().sort(states, siteswapRequest.getStartingStrategy());
            states = sort.getSorted();
        }

        final VanillaState[] vanillaStates = StatesToVanillaStatesConverter.convert(states);

        return new FourHandedSiteswap(vanillaStates);
    }

    @Override
    public boolean accepts(final Object object)
    {
        return State[].class.isAssignableFrom(object.getClass());
    }
}
