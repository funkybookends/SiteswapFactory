package com.ignoretheextraclub.siteswapfactory.describer.delegating;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A delegating describer who's available locales will the intersection of all the injected describers
 *
 * @param <T>
 * @author Caspar Nonclercq
 */
public class LocaleIntersectioningDelegatingDescriber<T extends Siteswap> extends DelegatingDescriber<T>
{
    public LocaleIntersectioningDelegatingDescriber(final List<DescriptionContributor<T>> descriptionContributors)
    {
        super(descriptionContributors, getAvailableLocales(descriptionContributors));
    }

    private static <T extends Siteswap> Collection<Locale> getAvailableLocales(final List<DescriptionContributor<T>> contributors)
    {
        final Set<Locale> locales = new HashSet<>(contributors.get(0).getAvailableLocales());

        contributors.stream()
            .skip(1)
            .map(DescriptionContributor::getAvailableLocales)
            .forEach(availe -> locales.retainAll(availe));

        return locales;
    }
}
