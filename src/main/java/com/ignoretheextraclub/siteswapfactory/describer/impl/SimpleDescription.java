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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.describer.SiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A pojo for storing {@link Siteswap} description information
 *
 * @author Caspar Nonclercq
 * @see SiteswapDescriber
 */
public class SimpleDescription implements Description
{
    private final Siteswap siteswap;
    private final Locale locale;
    private final List<String> siteswapNames;
    private final String description;
    private final String longDescription;

    public SimpleDescription(final Siteswap siteswap,
                             final Locale locale,
                             final List<String> siteswapNames,
                             final String description,
                             final String longDescription)
    {
        if (CollectionUtils.isEmpty(siteswapNames))
        {
            throw new IllegalArgumentException("siteswapNames cannot be empty");
        }

        this.siteswap = Objects.requireNonNull(siteswap, "siteswap cannot be null");
        this.locale = Objects.requireNonNull(locale, "locale cannot be null");
        this.siteswapNames = siteswapNames;
        this.description = description == null ? "" : description;
        this.longDescription = longDescription == null ? "" : longDescription;
    }

    @Override
    public Siteswap getSiteswap()
    {
        return siteswap;
    }

    @Override
    public Locale getLocale()
    {
        return locale;
    }

    @Override
    public String getSiteswapName()
    {
        return siteswapNames.get(0);
    }

    @Override
    public List<String> getSiteswapNames()
    {
        return siteswapNames;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getLongDescription()
    {
        return longDescription;
    }

    public static class Builder
    {
        private final Siteswap siteswap;
        private Locale locale;
        private List<String> siteswapNames = new ArrayList<>();
        private String description;
        private String longDescription;

        public Builder(final Siteswap siteswap)
        {
            this.siteswap = siteswap;
        }

        public Builder withLocale(final Locale locale)
        {
            this.locale = locale;
            return this;
        }

        public Builder withSiteswapNames(final List<String> siteswapNames)
        {
            this.siteswapNames = siteswapNames;
            return this;
        }

        public Builder addSiteswapName(final String siteswapName)
        {
            this.siteswapNames.add(siteswapName);
            return this;
        }

        public Builder addAllSiteswapNames(final Collection<String> siteswapNames)
        {
            this.siteswapNames.addAll(siteswapNames);
            return this;
        }

        public Builder clearSiteswapNames()
        {
            this.siteswapNames.clear();
            return this;
        }

        public Builder withDescription(final String description)
        {
            this.description = description;
            return this;
        }

        public Builder withLongDescription(final String longDescription)
        {
            this.longDescription = longDescription;
            return this;
        }

        public Siteswap getSiteswap()
        {
            return siteswap;
        }

        public Locale getLocale()
        {
            return locale;
        }

        public List<String> getSiteswapNames()
        {
            return siteswapNames;
        }

        public String getDescription()
        {
            return description;
        }

        public String getLongDescription()
        {
            return longDescription;
        }

        public SimpleDescription createSimpleDescription()
        {
            return new SimpleDescription(siteswap, locale, siteswapNames, description, longDescription);
        }
    }
}
