/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.describer.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.VanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.HighestThrowFirstStrategy;

/**
 * Adds the configured names to for the siteswap.
 *
 */
public class FromCanonicalNameDescriber implements DescriptionContributor
{
    private static final Logger LOG = LoggerFactory.getLogger(FromCanonicalNameDescriber.class);

    private final Collection<Locale> availableLocales;
    private final Map<String, List<String>> canonicalNameToNamesMap;
    private final Function<Siteswap, String> siteswapToCanonicalName;

    /**
     * Creates a describer for the given inputs.
     *
     * @param supportedLocales        The locales available in the names.
     * @param canonicalNameToNamesMap A map, keyed by the canonicalName to a list of names to add in order to the description
     * @param siteswapToCanonicalName A mapper of the siteswap to the canonical name to find in the {@code #nameToSiteswapSortedByStrategy}
     */
    public FromCanonicalNameDescriber(final Collection<Locale> supportedLocales,
                                      final Map<String, List<String>> canonicalNameToNamesMap,
                                      final Function<Siteswap, String> siteswapToCanonicalName)
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
    public void contribute(final Siteswap siteswap,
                           final Locale locale,
                           final SimpleDescription.Builder builder)
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
     * @return A canonical name of a siteswap.
     */
    public static Function<Siteswap, String> vanillaSiteswapToCanonicalNameMapper()
    {
        return (siteswap) -> GeneralCircuitToTwoHandedSiteswapConstructor.get()
                .apply(new SiteswapRequest(siteswap.getGeneralCircuit(), StreamingFilteringReducer.get(), HighestThrowFirstStrategy.get()))
                .toString();
    }
}
