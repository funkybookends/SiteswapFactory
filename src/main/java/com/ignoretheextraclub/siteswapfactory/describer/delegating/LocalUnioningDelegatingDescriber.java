package com.ignoretheextraclub.siteswapfactory.describer.delegating;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Determines the available locales by unioning the available locales of all the {@link DescriptionContributor}s provided.
 * @author Caspar Nonclercq
 */
public class LocalUnioningDelegatingDescriber<T extends Siteswap> extends DelegatingDescriber<T>
{
    public LocalUnioningDelegatingDescriber(final List<DescriptionContributor<T>> descriptionContributors)
    {
        super(descriptionContributors, getAvailableLocales(descriptionContributors));
    }

    private static <T extends Siteswap> Collection<Locale> getAvailableLocales(final List<DescriptionContributor<T>> contributors)
    {
        return contributors.stream()
            .map(DescriptionContributor::getAvailableLocales)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }
}
