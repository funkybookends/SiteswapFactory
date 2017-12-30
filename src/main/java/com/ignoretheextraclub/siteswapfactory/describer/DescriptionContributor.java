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
