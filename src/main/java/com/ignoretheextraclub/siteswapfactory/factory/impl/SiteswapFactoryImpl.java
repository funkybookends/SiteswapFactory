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

package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * A factory for creating {@link Siteswap}s. Provides static methods to create {@code Siteswap}s with the
 * default configuration, static methods to get different standard configurations, and a constructor
 * to specify your own configuration.
 *
 * @author Caspar Nonclercq
 */
public class SiteswapFactoryImpl implements SiteswapFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(SiteswapFactoryImpl.class);

    /**
     * An ordered list of siteswap constructors that will be used, in order, to create a siteswap.
     */
    private final List<SiteswapConstructor<? extends Siteswap>> constructors;

    /**
     * The base {@link SiteswapRequestBuilder} to use.
     */
    private final SiteswapRequestBuilder siteswapRequestBuilder;

    // region Constructors

    /**
     * Takes a list of constructors to use with the default configuration for building those {@link Siteswap}s.
     *
     * @param constructors The list of constructors to use
     * @see #SiteswapFactoryImpl(List, SiteswapRequestBuilder)
     */
    public SiteswapFactoryImpl(final List<SiteswapConstructor<? extends Siteswap>> constructors)
    {
        this(constructors, new SiteswapRequestBuilder());
    }

    /**
     * A constructor to specify your own configuration for constructing {@link Siteswap}s. They will be constructed
     * using the list in order.
     *
     * @param constructors           The list of constructors to use.
     * @param siteswapRequestBuilder A prefilled builder with the configuration to use.
     */
    public SiteswapFactoryImpl(final List<SiteswapConstructor<? extends Siteswap>> constructors,
                               final SiteswapRequestBuilder siteswapRequestBuilder)
    {
        this.constructors = Collections.unmodifiableList(new ArrayList<>(constructors));
        this.siteswapRequestBuilder = siteswapRequestBuilder;
    }

    // endregion

    // region Public Methods

    @Override
    public Siteswap apply(final SiteswapRequest siteswapRequest)
    {
        final InvalidSiteswapException invalidSiteswapException = new InvalidSiteswapException("Could not find a suitable constructor for siteswap request constructing: "
            + siteswapRequest.getConstructor().toString());

        return getSiteswapStream(siteswapRequest, invalidSiteswapException)
            .findFirst()
            .orElseThrow(() -> invalidSiteswapException);
    }

    @Override
    public boolean accepts(final Object object)
    {
        return constructors.stream().anyMatch(constructor -> constructor.accepts(object));
    }

    @Override
    public Siteswap get(final Object object)
    {
        return apply(siteswapRequestBuilder.createSiteswapRequest(object));
    }

    @Override
    public List<Siteswap> getAll(final SiteswapRequest siteswapRequest)
    {
        return getSiteswapStream(siteswapRequest, null).collect(Collectors.toList());
    }

    @Override
    public List<Siteswap> getAll(final Object siteswap)
    {
        return getAll(siteswapRequestBuilder.createSiteswapRequest(siteswap));
    }

    // endregion

    // region Private Methods

    /**
     * Returns a stream of {@link Siteswap} using the
     *
     * @param siteswapRequest          The siteswapRequest
     * @param invalidSiteswapException A nullable exception to populate.
     * @return A stream of siteswaps.
     */
    private Stream<Siteswap> getSiteswapStream(final SiteswapRequest siteswapRequest, final InvalidSiteswapException invalidSiteswapException)
    {
        return this.constructors.stream()
            .filter(siteswapConstructor -> siteswapConstructor.accepts(siteswapRequest.getConstructor()))
            .map(toSiteswapOrNull(siteswapRequest, invalidSiteswapException))
            .filter(Objects::nonNull);
    }

    /**
     * Constructs a siteswap, or returns null. If an exception is throw, and an exception is provided, the throw exception will
     * be added to the suppressed exceptions of the exception provided.
     *
     * @param siteswapRequest          The siteswap request to fill
     * @param invalidSiteswapException Optional exception whose suppressed exceptions will be populated if an exception is thrown during construction
     * @return A function that constructs a siteswap or returns null
     */
    private Function<SiteswapConstructor<? extends Siteswap>, Siteswap> toSiteswapOrNull(final SiteswapRequest siteswapRequest, final InvalidSiteswapException invalidSiteswapException)
    {
        return constructor ->
        {
            try
            {
                return constructor.apply(siteswapRequest);
            }
            catch (final Throwable cause)
            {
                if (invalidSiteswapException != null)
                {
                    if (invalidSiteswapException.getCause() == null)
                    {
                        invalidSiteswapException.initCause(cause);
                    }
                    else
                    {
                        invalidSiteswapException.addSuppressed(cause);
                    }
                }
                return null;
            }
        };
    }

    // endregion


}
