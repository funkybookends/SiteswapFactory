package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 Created by caspar on 30/07/17.
 */
public class TwoHandedSiteswapGenerator extends SiteswapGenerator
{
    private static final Logger LOG = LoggerFactory.getLogger(TwoHandedSiteswapGenerator.class);

    public TwoHandedSiteswapGenerator(final int maxPeriod)
    {
        this(maxPeriod, SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY, true);
    }

    private TwoHandedSiteswapGenerator(final int maxPeriod,
                                      final SortingStrategy<VanillaState> sortingStrategy,
                                      final boolean reduce)
    {
        super(maxPeriod, getSiteswapConstructor(sortingStrategy, reduce));
    }

    private static Function<State[], Siteswap> getSiteswapConstructor(final SortingStrategy<VanillaState> sortingStrategy,
                                                                      final boolean reduce)
    {
        return (State[] states) ->
        {
            try
            {
                return SiteswapFactory.createTHS(VanillaStateUtils.castAllToVanillaState(states), sortingStrategy, reduce);
            }
            catch (final InvalidSiteswapException cause)
            {
                throw new IllegalArgumentException("Provided states were invalid", cause);
            }
        };
    }
}
