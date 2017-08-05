package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.predicates.impl.ThrowCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
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
                return SiteswapFactory.createFHS(VanillaStateUtils.castAllToVanillaState(states), sortingStrategy, reduce);
            }
            catch (final InvalidSiteswapException cause)
            {
                throw new IllegalArgumentException("Provided states were not valid.", cause);
            }
        });

        this.addPredicate(new ThrowCombinationPredicate(VanillaThro.getUnchecked(3)).negate());
        this.addPredicate(new ThrowCombinationPredicate(VanillaThro.getUnchecked(1)).negate());
    }
}
