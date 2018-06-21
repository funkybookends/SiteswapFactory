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

package com.ignoretheextraclub.siteswapfactory.describer;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Defines a class that returns a description object for a siteswap.
 *
 * @author Caspar Nonclercq
 */
public interface SiteswapDescriber
{
    /**
     * Return a description object for the locale, or null if the locale is not supported.
     *
     * @param siteswap The siteswap to describe
     * @param locale   @Nullable The locale to describe in or the default if null
     * @return A siteswap description with the best locale
     */
    Description describe(Siteswap siteswap, Locale locale);

    /**
     * Returns a description object for the best locale available for the preferences provided.
     *
     * @param siteswap     The siteswap to describe
     * @param priorityList user's Language Priority List in which each language tag is sorted in descending order based on priority or weight
     * @return A siteswap description in the best locale available or the default locale if there are no matches.
     * @see #describe(Siteswap, String)
     * @see Locale#filter(List, Collection)
     * @see #getAvailableLocales()
     */
    default Description describe(Siteswap siteswap, List<Locale.LanguageRange> priorityList)
    {
        final List<Locale> localeList = Locale.filter(priorityList, getAvailableLocales());
        return describe(siteswap, localeList.isEmpty() ? null : localeList.get(0));
    }

    /**
     * Parses the localLanguage ranges and then finds the best description or returns a description in the default locale.
     * @param siteswap The siteswap to describe
     * @param localeLanguageRanges The language ranges to parse
     * @return A description with the best locale.
     * @see #describe(Siteswap, Locale)
     * @see Locale.LanguageRange#parse(String)
     */
    default Description describe(Siteswap siteswap, String localeLanguageRanges)
    {
        return describe(siteswap, Locale.LanguageRange.parse(localeLanguageRanges));
    }

    /**
     * Return the available locales.
     *
     * @return The locales available.
     */
    Collection<Locale> getAvailableLocales();
}
