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

import java.util.List;
import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Defines a description of a siteswap
 *
 * @author Caspar Nonclercq
 */
public interface Description
{
    /**
     * Get the siteswap that these descriptions belong to.
     *
     * @return the siteswap
     */
    Siteswap getSiteswap();

    /**
     * Return the locale of this {@link Description};
     *
     * @return The locale
     */
    Locale getLocale();

    /**
     * Return the name of this siteswap.
     *
     * The definition is defined by the {@link SiteswapDescriber}.
     *
     * @return The name of the siteswap.
     */
    String getSiteswapName();

    /**
     * Returns all the names of this siteswap.
     *
     * Ordering is defined by the {@link SiteswapDescriber}.
     *
     * @return All the names.
     */
    List<String> getSiteswapNames();

    /**
     * Returns the description of this siteswap, in around 140 characters or less.
     *
     * @return the siteswaps description.
     */
    String getDescription();

    /**
     * Returns a longer description that includes details of how to juggle the pattern.
     *
     * @return A longer description of the siteswap
     */
    String getLongDescription();
}
