package com.ignoretheextraclub.siteswapfactory.configuration;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;

/**
 Created by caspar on 29/07/17.
 */
public class SiteswapFactoryConfiguration
{
    public static final SortingStrategy<VanillaState> DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY = FourHandedPassingStrategy.get();
    public static final SortingStrategy<VanillaState> DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY = HighestThrowFirstStrategy.get();
}
