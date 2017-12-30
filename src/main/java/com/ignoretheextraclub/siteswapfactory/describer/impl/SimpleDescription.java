package com.ignoretheextraclub.siteswapfactory.describer.impl;

import java.util.ArrayList;
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
 * @param <T> The type of siteswap
 * @author Caspar Nonclercq
 * @see SiteswapDescriber
 */
public class SimpleDescription<T extends Siteswap> implements Description<T>
{
    private final T siteswap;
    private final Locale locale;
    private final List<String> siteswapNames;
    private final String description;
    private final String longDescription;

    public SimpleDescription(final T siteswap,
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
    public T getSiteswap()
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

    public static class Builder<T extends Siteswap>
    {
        private final T siteswap;
        private Locale locale;
        private List<String> siteswapNames = new ArrayList<>();
        private String description;
        private String longDescription;

        public Builder(final T siteswap)
        {
            this.siteswap = siteswap;
        }

        public Builder<T> withLocale(final Locale locale)
        {
            this.locale = locale;
            return this;
        }

        public Builder<T> withSiteswapNames(final List<String> siteswapNames)
        {
            this.siteswapNames = siteswapNames;
            return this;
        }

        public Builder<T> addSiteswapName(final String siteswapName)
        {
            this.siteswapNames.add(siteswapName);
            return this;
        }

        public Builder<T> withDescription(final String description)
        {
            this.description = description;
            return this;
        }

        public Builder<T> withLongDescription(final String longDescription)
        {
            this.longDescription = longDescription;
            return this;
        }

        public T getSiteswap()
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

        public SimpleDescription<T> createSimpleDescription()
        {
            return new SimpleDescription<>(siteswap, locale, siteswapNames, description, longDescription);
        }
    }
}
