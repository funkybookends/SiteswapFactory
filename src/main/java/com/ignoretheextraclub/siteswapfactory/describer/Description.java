package com.ignoretheextraclub.siteswapfactory.describer;

import java.util.List;
import java.util.Locale;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Defines a description of a siteswap
 *
 * @author Caspar Nonclercq
 */
public interface Description<T extends Siteswap>
{
    /**
     * Get the siteswap that these descriptions belong to.
     *
     * @return the siteswap
     */
    T getSiteswap();

    /**
     * Return the locale of this {@link Description};
     *
     * @return The locale
     */
    Locale getLocale();

    /**
     * Return the name of this siteswap.
     *
     * The definition is defined by the {@link SiteswapDescriber}.
     *
     * @return The name of the siteswap.
     */
    String getSiteswapName();

    /**
     * Returns all the names of this siteswap.
     *
     * Ordering is defined by the {@link SiteswapDescriber}.
     *
     * @return All the names.
     */
    List<String> getSiteswapNames();

    /**
     * Returns the description of this siteswap, in around 140 characters or less.
     *
     * @return the siteswaps description.
     */
    String getDescription();

    /**
     * Returns a longer description that includes details of how to juggle the pattern.
     *
     * @return A longer description of the siteswap
     */
    String getLongDescription();
}
