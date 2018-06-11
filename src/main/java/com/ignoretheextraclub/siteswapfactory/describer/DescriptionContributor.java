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
import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A class that contributes to a {@link SimpleDescription.Builder}.
 *
 * @param <T>
 */
public interface DescriptionContributor<T extends Siteswap>
{
    /**
     * The locales this contributor supports.
     * @return A collection of available Locales
     */
    Collection<Locale> getAvailableLocales();

    /**
     * Add in the description to the builder that this provides.
     * @param siteswap
     * @param locale
     * @param builder
     */
    void contribute(final T siteswap, final Locale locale, final SimpleDescription.Builder<T> builder);
}
