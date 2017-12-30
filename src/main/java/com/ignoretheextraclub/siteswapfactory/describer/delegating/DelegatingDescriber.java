package com.ignoretheextraclub.siteswapfactory.describer.delegating;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;

import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.describer.SiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A describer who delegates building the {@link Description}, using a {@link SimpleDescription.Builder} to
 * the injected {@link DescriptionContributor}s.
 *
 * @author Caspar Nonclercq
 */
public class DelegatingDescriber<T extends Siteswap> implements SiteswapDescriber<T>, DescriptionContributor<T>
{
    private final Collection<Locale> availableLocales;
    private final List<DescriptionContributor<T>> contributors;

    public DelegatingDescriber(final List<DescriptionContributor<T>> contributors, final Collection<Locale> availableLocales)
    {
        if (CollectionUtils.isEmpty(contributors))
        {
            throw new IllegalArgumentException("must provide at least one DescriptionContributor");
        }

        this.contributors = contributors;
        this.availableLocales = availableLocales;
    }

    @Override
    public Description<T> describe(final T siteswap, final Locale locale)
    {
        if (!availableLocales.contains(locale)) // as per interface
        {
            return null;
        }

        final SimpleDescription.Builder<T> builder = new SimpleDescription.Builder<>(siteswap).withLocale(locale);

        contribute(siteswap, locale, builder);

        return builder.createSimpleDescription();
    }

    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return availableLocales;
    }

    @Override
    public void contribute(final T siteswap, final Locale locale, final SimpleDescription.Builder<T> builder)
    {
        if (availableLocales.contains(locale))
        {
            contributors.forEach(contributor -> contributor.contribute(siteswap, locale, builder));
        }
    }
}
