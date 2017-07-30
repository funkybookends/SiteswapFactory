package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

/**
 Created by caspar on 30/07/17.
 */
public class FourHandedSiteswapGenerator extends SiteswapGenerator
{
    public FourHandedSiteswapGenerator(final int maxPeriod)
    {
        this(maxPeriod, SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY,true);
    }

    public FourHandedSiteswapGenerator(final int maxPeriod,
                                       final SortingStrategy<VanillaState> sortingStrategy,
                                       final boolean reduce)
    {
        super(maxPeriod, (State[] states) ->
        {
            try
            {
                final VanillaState[] vanillaStates = new VanillaState[states.length];
                for (int i = 0; i < vanillaStates.length; i++)
                {
                    vanillaStates[i] = (VanillaState) states[i];
                }
                return SiteswapFactory.createFHS(vanillaStates, sortingStrategy, reduce);
            }
            catch (InvalidSiteswapException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
