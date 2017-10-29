package com.ignoretheextraclub.siteswapfactory.describer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A describer who delegates building the {@link Description}, using a {@link SimpleDescription.Builder} to
 * the injected {@link DescriptionContributor}s. The {@link #getAvailableLocales()} is the union of all locales
 * provided by the {@code DescriptionContributor}s.
 *
 * @author Caspar Nonclercq
 */
public class DelegatingDescriber<T extends Siteswap> implements SiteswapDescriber<T>
{
    private final Collection<Locale> availableLocales;
    private final List<DescriptionContributor<T>> contributors;

    public DelegatingDescriber(final List<DescriptionContributor<T>> contributors)
    {
        if (CollectionUtils.isEmpty(contributors))
        {
            throw new IllegalArgumentException("must provide at least one DescroptionContributor");
        }

        this.contributors = contributors;
        this.availableLocales = new HashSet<>(contributors.get(0).getAvailableLocales());

        contributors.stream()
            .skip(1)
            .map(DescriptionContributor::getAvailableLocales)
            .forEach(availableLocales::retainAll);
    }

    @Override
    public Description<T> describe(final T siteswap, final Locale locale)
    {
        if (!availableLocales.contains(locale)) // as per interface
        {
            return null;
        }

        final SimpleDescription.Builder<T> builder = new SimpleDescription.Builder<>(siteswap)
            .withLocale(locale);

        contributors.forEach(contributor -> contributor.contribute(siteswap, locale, builder));

        return builder.createSimpleDescription();
    }

    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return availableLocales;
    }
}
