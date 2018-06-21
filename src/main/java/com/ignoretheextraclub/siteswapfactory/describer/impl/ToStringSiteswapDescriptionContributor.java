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

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Adds the {@link Siteswap#toString()} as a name to the description. Always happens.
 * @author Caspar Nonclercq
 */
public class ToStringSiteswapDescriptionContributor implements DescriptionContributor
{
    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return Arrays.asList(Locale.getAvailableLocales());
    }

    @Override
    public void contribute(final Siteswap siteswap, final Locale locale, final SimpleDescription.Builder builder)
    {
        builder.addSiteswapName(siteswap.toString());
    }
}
