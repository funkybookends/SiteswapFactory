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
     * @throws com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException if not valid
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

    /**
     * Calls {@link #apply(SiteswapRequest)} with the object wrapped in the default {@link SiteswapRequest}.
     * @param object The constructor
     * @return A siteswap
     * @see #apply(SiteswapRequest)
     */
    default T get(final Object object)
    {
        return this.apply(new SiteswapRequest(object));
    }
}
