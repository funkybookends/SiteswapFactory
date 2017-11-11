package com.ignoretheextraclub.siteswapfactory.describer.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.VanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.StreamingMappingReducingStartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;

/**
 * Adds the configured names to for the siteswap.
 *
 * @param <T>
 */
public class FromCanonicalNameDescriber<T extends Siteswap> implements DescriptionContributor<T>
{
    private static final Logger LOG = LoggerFactory.getLogger(FromCanonicalNameDescriber.class);

    private final Collection<Locale> availableLocales;
    private final Map<String, List<String>> canonicalNameToNamesMap;
    private final Function<T, String> siteswapToCanonicalName;

    /**
     * Creates a describer for the given inputs.
     *
     * @param supportedLocales        The locales available in the names.
     * @param canonicalNameToNamesMap A map, keyed by the canonicalName to a list of names to add in order to the description
     * @param siteswapToCanonicalName A mapper of the siteswap to the canonical name to find in the {@code #nameToSiteswapSortedByStrategy}
     */
    public FromCanonicalNameDescriber(final Collection<Locale> supportedLocales,
                                      final Map<String, List<String>> canonicalNameToNamesMap,
                                      final Function<T, String> siteswapToCanonicalName)
    {
        this.availableLocales = supportedLocales;
        this.canonicalNameToNamesMap = canonicalNameToNamesMap;
        this.siteswapToCanonicalName = siteswapToCanonicalName;
    }

    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return availableLocales;
    }

    @Override
    public void contribute(final T siteswap,
                           final Locale locale,
                           final SimpleDescription.Builder<T> builder)
    {
        if (!availableLocales.contains(locale))
        {
            return;
        }

        final String canonicalName = siteswapToCanonicalName.apply(siteswap);

        final List<String> names = canonicalNameToNamesMap.getOrDefault(canonicalName, Collections.emptyList());

        names.forEach(builder::addSiteswapName);
    }

    /**
     * A canonical name mapper suitable for {@link VanillaSiteswap}s.
     *
     * Uses {@link HighestThrowFirstStrategy} and {@link StreamingMappingReducingStartFinder} to find the canonical name.
     *
     * @param <T>
     * @return A canonical name of a siteswap.
     */
    public static <T extends VanillaSiteswap> Function<T, String> vanillaSiteswapToCanonicalNameMapper()
    {
        return (siteswap) -> StreamingMappingReducingStartFinder.get()
            .sort(siteswap, HighestThrowFirstStrategy.get(), StatesToVanillaStatesConverter.get().andThen(VanillaSiteswap::new))
            .toString();
    }
}
