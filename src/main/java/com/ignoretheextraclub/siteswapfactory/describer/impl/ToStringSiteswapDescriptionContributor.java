package com.ignoretheextraclub.siteswapfactory.describer.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Adds the {@link Siteswap#toString()} as a name to the description. Always happens.
 * @author Caspar Nonclercq
 */
public class ToStringSiteswapDescriptionContributor<T extends Siteswap> implements DescriptionContributor<T>
{
    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return Arrays.asList(Locale.getAvailableLocales());
    }

    @Override
    public void contribute(final T siteswap, final Locale locale, final SimpleDescription.Builder<T> builder)
    {
        builder.addSiteswapName(siteswap.toString());
    }
}
