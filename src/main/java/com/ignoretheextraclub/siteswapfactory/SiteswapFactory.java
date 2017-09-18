package com.ignoretheextraclub.siteswapfactory;

import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.OptionalWrapperConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.SiteswapWrapperConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.StatesToFourHandedSiteswapConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.StatesToTwoHandedSiteswapConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.StringToFourHandedSiteswapConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.StringToTwoHandedSiteswapConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by caspar on 29/07/17.
 */
public class SiteswapFactory
{
    private static final Pattern VANILLA_SITESWAP_FORMAT = Pattern.compile("^[\\da-zA-Z]+$");

    private final SiteswapFactoryConfiguration config;

    public SiteswapFactory(final SiteswapFactoryConfiguration config)
    {
        this.config = config;
    }

    public Siteswap createSiteswap(final String siteswap)
    {
        return config.getSiteswapConstructors().stream()

                     // Wrap result into a Siteswap
                     .map(constructor -> constructor.andThen(new SiteswapWrapperConverter<>()))

                     // Catch all exceptions and convert to Optional.empty()
                     .map(constructor -> new OptionalWrapperConverter<Siteswap>().compose(constructor))

                     // Map to optional
                     .map(constructor -> constructor.apply(siteswap))

                     // Get first
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .findFirst()
                     .orElseThrow(() -> new InvalidSiteswapException("No suitable constructor found"));
    }

    // ============================== Configured Creators: These use the provided configuration

    public FourHandedSiteswap createFourHandedSiteswap(final String siteswap)
    {
        return createFHS(siteswap,
                config.getDefaultFourHandedSiteswapSortingStrategy(),
                config.getDefaultReducePolicy());
    }

    public TwoHandedVanillaSiteswap createTwoHandedSiteswap(final String siteswap)
    {
        return createTHS(siteswap, config.getDefaultTwoHandedSortingStrategy(), config.getDefaultReducePolicy());
    }

    // ============================== STATIC CONSTRUCTORS: These use the default configurations

    // ============= Four Handed Siteswap

    public static FourHandedSiteswap createFHS(final String siteswap) throws InvalidSiteswapException
    {
        return createFHS(siteswap,
                SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY,
                SiteswapFactoryConfiguration.DEFAULT_REDUCE);
    }

    public static FourHandedSiteswap createFHS(final String siteswap,
                                               final SortingStrategy sortingStrategy,
                                               final boolean reduce) throws InvalidSiteswapException
    {
        return new StringToFourHandedSiteswapConverter(reduce, sortingStrategy).apply(siteswap);
    }

    public static FourHandedSiteswap createFHS(final State[] states)
    {
        return createFHS(states,
                SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY,
                SiteswapFactoryConfiguration.DEFAULT_REDUCE);
    }

    public static FourHandedSiteswap createFHS(final State[] states,
                                               final SortingStrategy sortingStrategy,
                                               final boolean reduce)
    {
        return StatesToVanillaStatesConverter.get()
                                             .andThen(new StatesToFourHandedSiteswapConverter(reduce, sortingStrategy))
                                             .apply(states);
    }

    // ============= Two Handed Siteswap

    public static TwoHandedVanillaSiteswap createTHS(final String siteswap) throws InvalidSiteswapException
    {
        return createTHS(siteswap,
                SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY,
                SiteswapFactoryConfiguration.DEFAULT_REDUCE);
    }

    public static TwoHandedVanillaSiteswap createTHS(final String siteswap,
                                                     final SortingStrategy sortingStrategy,
                                                     final boolean reduce) throws InvalidSiteswapException
    {
        return new StringToTwoHandedSiteswapConverter(reduce, sortingStrategy).apply(siteswap);
    }

    public static TwoHandedVanillaSiteswap createTHS(final State[] states)
    {
        return createTHS(states,
                SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY,
                SiteswapFactoryConfiguration.DEFAULT_REDUCE);
    }

    public static TwoHandedVanillaSiteswap createTHS(final State[] states,
                                                     final SortingStrategy sortingStrategy,
                                                     final boolean reduce)
    {
        return StatesToVanillaStatesConverter.get()
                                             .andThen(new StatesToTwoHandedSiteswapConverter(reduce, sortingStrategy))
                                             .apply(states);
    }
}
