package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.impl.BannedThrowCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Created by caspar on 30/07/17.
 */
public class FourHandedSiteswapGenerator extends SiteswapGenerator
{
    private static final Logger LOG = LoggerFactory.getLogger(FourHandedSiteswapGenerator.class);

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
            LOG.debug("{}, {}", "trying", states);
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
                LOG.debug("invalid, {}", e);
                throw new RuntimeException(e);
            }
        });

        this.addPredicate(new BannedThrowCombinationPredicate(VanillaThro.getUnchecked(3)));
        this.addPredicate(new BannedThrowCombinationPredicate(VanillaThro.getUnchecked(1)));
    }
}
