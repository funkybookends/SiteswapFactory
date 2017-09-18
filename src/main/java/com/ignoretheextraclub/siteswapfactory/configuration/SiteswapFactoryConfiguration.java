package com.ignoretheextraclub.siteswapfactory.configuration;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.StringToTwoHandedSiteswapConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * A class for configuring a {@link com.ignoretheextraclub.siteswapfactory.SiteswapFactory}. Holds all the default
 * values for a {@link com.ignoretheextraclub.siteswapfactory.SiteswapFactory}.
 *
 * @author Caspar Nonclercq
 */
public class SiteswapFactoryConfiguration
{
    public static final SortingStrategy DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY = FourHandedPassingStrategy.get();
    public static final SortingStrategy DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY = HighestThrowFirstStrategy.get();
    public static final boolean DEFAULT_REDUCE = true;

    public static final List<Function<String, ? extends Siteswap>> DEFAULT_SITESWAP_CONSTRUCTORS = Arrays.asList
            (
                    new StringToTwoHandedSiteswapConverter(DEFAULT_REDUCE, DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY)
            );

    private final SortingStrategy defaultFourHandedSiteswapSortingStrategy;
    private final boolean defaultReducePolicy;
    private final SortingStrategy defaultTwoHandedSortingStrategy;
    private final List<Function<String, ? extends Siteswap>> siteswapConstructors;

    SiteswapFactoryConfiguration(final SortingStrategy defaultFourHandedSiteswapSortingStrategy,
                                 final boolean defaultReducePolicy,
                                 final SortingStrategy defaultTwoHandedSortingStrategy,
                                 final List<Function<String, ? extends Siteswap>> siteswapConstructors)
    {
        this.defaultFourHandedSiteswapSortingStrategy = defaultFourHandedSiteswapSortingStrategy;
        this.defaultReducePolicy = defaultReducePolicy;
        this.defaultTwoHandedSortingStrategy = defaultTwoHandedSortingStrategy;
        this.siteswapConstructors = siteswapConstructors;
    }

    public SortingStrategy getDefaultFourHandedSiteswapSortingStrategy()
    {
        return defaultFourHandedSiteswapSortingStrategy;
    }

    public boolean getDefaultReducePolicy()
    {
        return defaultReducePolicy;
    }

    public SortingStrategy getDefaultTwoHandedSortingStrategy()
    {
        return defaultTwoHandedSortingStrategy;
    }

    public List<Function<String, ? extends Siteswap>> getSiteswapConstructors()
    {
        return siteswapConstructors;
    }

    public SiteswapFactoryConfigurationBuilder cloneToBuilder()
    {
        return new SiteswapFactoryConfigurationBuilder()
                .setDefaultFourHandedSiteswapSortingStrategy(defaultFourHandedSiteswapSortingStrategy)
                .setDefaultReducePolicy(defaultReducePolicy)
                .setDefaultTwoHandedSortingStrategy(defaultTwoHandedSortingStrategy)
                .setSiteswapConstructors(siteswapConstructors);
    }

    @Override
    public SiteswapFactoryConfiguration clone()
    {
        return cloneToBuilder().createSiteswapFactoryConfiguration();
    }
}
