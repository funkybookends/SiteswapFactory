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

package com.ignoretheextraclub.siteswapfactory.describer.delegating;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;

import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A describer who delegates building the {@link Description}, using a {@link SimpleDescription.Builder} to
 * the injected {@link DescriptionContributor}s.
 *
 * @author Caspar Nonclercq
 */
public class DelegatingDescriber implements SiteswapDescriber, DescriptionContributor
{
    private final Collection<Locale> availableLocales;
    private final List<DescriptionContributor> contributors;

    public DelegatingDescriber(final List<DescriptionContributor> contributors, final Collection<Locale> availableLocales)
    {
        if (CollectionUtils.isEmpty(contributors))
        {
            throw new IllegalArgumentException("must provide at least one DescriptionContributor");
        }

        this.contributors = contributors;
        this.availableLocales = availableLocales;
    }

    @Override
    public Description describe(final Siteswap siteswap, final Locale locale)
    {
        if (!availableLocales.contains(locale)) // as per interface
        {
            return null;
        }

        final SimpleDescription.Builder builder = new SimpleDescription.Builder(siteswap).withLocale(locale);

        contribute(siteswap, locale, builder);

        return builder.createSimpleDescription();
    }

    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return availableLocales;
    }

    @Override
    public void contribute(final Siteswap siteswap, final Locale locale, final SimpleDescription.Builder builder)
    {
        if (availableLocales.contains(locale))
        {
            contributors.forEach(contributor -> contributor.contribute(siteswap, locale, builder));
        }
    }
}
