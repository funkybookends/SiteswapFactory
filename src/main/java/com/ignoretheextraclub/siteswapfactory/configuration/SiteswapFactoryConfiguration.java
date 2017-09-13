package com.ignoretheextraclub.siteswapfactory.configuration;

import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;

/**
 * A class for configuring a {@link com.ignoretheextraclub.siteswapfactory.SiteswapFactory}. Holds all the default
 * values for a {@link com.ignoretheextraclub.siteswapfactory.SiteswapFactory}.
 * @author Caspar Nonclercq
 */
public class SiteswapFactoryConfiguration
{
    public static final SortingStrategy DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY = FourHandedPassingStrategy.get();
    public static final SortingStrategy DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY = HighestThrowFirstStrategy.get();
}
