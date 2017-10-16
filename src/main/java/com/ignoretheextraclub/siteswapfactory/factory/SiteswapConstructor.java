package com.ignoretheextraclub.siteswapfactory.factory;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A class that is able to construct a siteswap given a {@link SiteswapRequest}
 *
 * @param <T> The type of siteswap that this implementation creates.
 * @author Caspar Nonclercq
 */
public interface SiteswapConstructor<T extends Siteswap> extends Function<SiteswapRequest, T>
{
    /**
     * Construct the {@link Siteswap} from the {@code SiteswapRequest}. The implementation
     * should respect the preferences of the request where possible.
     *
     * @param siteswapRequest The siteswap request.
     * @return The siteswap.
     * @see SiteswapRequest for details on the preferences of the request.
     */
    T apply(SiteswapRequest siteswapRequest);

    /**
     * Returns true if this constructor can construct using the given type.
     *
     * Implementors should not inspect the object prematurely, as they may be passed a dummy version
     * for sorting or validation purposes.
     *
     * @param object The type in the {@link SiteswapRequest}
     * @return If a siteswap could be constructed using this type.
     */
    boolean accepts(Object object);

    default T get(final Object object)
    {
        return this.apply(new SiteswapRequest(object));
    }
}
