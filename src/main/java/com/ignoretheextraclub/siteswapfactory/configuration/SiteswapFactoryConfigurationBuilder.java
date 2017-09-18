package com.ignoretheextraclub.siteswapfactory.configuration;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

import java.util.List;
import java.util.function.Function;

public class SiteswapFactoryConfigurationBuilder
{
    private SortingStrategy defaultFourHandedSiteswapSortingStrategy = SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY;
    private boolean defaultReducePolicy = SiteswapFactoryConfiguration.DEFAULT_REDUCE;
    private SortingStrategy defaultTwoHandedSortingStrategy = SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY;
    private List<Function<String, ? extends Siteswap>> siteswapConstructors = SiteswapFactoryConfiguration.DEFAULT_SITESWAP_CONSTRUCTORS;

    public SiteswapFactoryConfigurationBuilder setDefaultFourHandedSiteswapSortingStrategy(final SortingStrategy defaultFourHandedSiteswapSortingStrategy)
    {
        this.defaultFourHandedSiteswapSortingStrategy = defaultFourHandedSiteswapSortingStrategy;
        return this;
    }

    public SiteswapFactoryConfigurationBuilder setDefaultReducePolicy(final boolean defaultReducePolicy)
    {
        this.defaultReducePolicy = defaultReducePolicy;
        return this;
    }

    public SiteswapFactoryConfigurationBuilder setDefaultTwoHandedSortingStrategy(final SortingStrategy defaultTwoHandedSortingStrategy)
    {
        this.defaultTwoHandedSortingStrategy = defaultTwoHandedSortingStrategy;
        return this;
    }

    public SiteswapFactoryConfigurationBuilder setSiteswapConstructors(final List<Function<String, ? extends Siteswap>> siteswapConstructors)
    {
        this.siteswapConstructors = siteswapConstructors;
        return this;
    }

    public List<Function<String, ? extends Siteswap>> getSiteswapConstructors()
    {
        return siteswapConstructors;
    }

    @Override
    public SiteswapFactoryConfigurationBuilder clone()
    {
        return createSiteswapFactoryConfiguration().cloneToBuilder();
    }

    public SiteswapFactoryConfiguration createSiteswapFactoryConfiguration()
    {
        return new SiteswapFactoryConfiguration(defaultFourHandedSiteswapSortingStrategy,
                defaultReducePolicy,
                defaultTwoHandedSortingStrategy,
                siteswapConstructors);
    }

}