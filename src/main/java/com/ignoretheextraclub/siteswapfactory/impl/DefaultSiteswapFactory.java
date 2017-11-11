package com.ignoretheextraclub.siteswapfactory.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
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
public final class DefaultSiteswapFactory<T extends Siteswap> implements SiteswapFactory<T>
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSiteswapFactory.class);

    /**
     * An ordered list of siteswap constructors that will be used, in order, to create a siteswap.
     */
    private final List<SiteswapConstructor<T>> constructors;

    /**
     * The base {@link SiteswapRequestBuilder} to use.
     */
    private final SiteswapRequestBuilder siteswapRequestBuilder;

    // region Constructors

    /**
     * Takes a list of constructors to use with the default configuration for building those {@link Siteswap}s.
     *
     * @param constructors The list of constructors to use
     * @see #DefaultSiteswapFactory(List, SiteswapRequestBuilder)
     */
    public DefaultSiteswapFactory(final List<SiteswapConstructor<T>> constructors)
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
    public DefaultSiteswapFactory(final List<SiteswapConstructor<T>> constructors,
                                  final SiteswapRequestBuilder siteswapRequestBuilder)
    {
        this.constructors = Collections.unmodifiableList(new ArrayList<>(constructors));
        this.siteswapRequestBuilder = siteswapRequestBuilder;
    }

    // endregion

    // region Public Methods

    @Override
    public T apply(final SiteswapRequest siteswapRequest)
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
    public T get(final Object object)
    {
        return apply(siteswapRequestBuilder.createSiteswapRequest(object));
    }

    /**
     * Gets all the {@link Siteswap}s, in the same order as the {@link DefaultSiteswapFactory#constructors} could construct.
     * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
     * be shorter than the {@link DefaultSiteswapFactory#constructors} list, or empty.
     *
     * @param siteswapRequest The siteswapRequest to construct the siteswap with.
     * @return A modifiable list of {@link Siteswap}s.
     */
    public List<T> getAll(final SiteswapRequest siteswapRequest)
    {
        return getSiteswapStream(siteswapRequest, null).collect(Collectors.toList());
    }

    /**
     * Gets all the {@link Siteswap}s, in the same order as the {@link DefaultSiteswapFactory#constructors} could construct.
     * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
     * be shorter than the {@link DefaultSiteswapFactory#constructors} list, or empty.
     *
     * @param siteswap The object to use to construct the siteswap with.
     * @return A modifiable list of {@link Siteswap}s.
     */
    public List<T> getAll(final Object siteswap)
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
    private Stream<T> getSiteswapStream(final SiteswapRequest siteswapRequest, final InvalidSiteswapException invalidSiteswapException)
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
    private Function<SiteswapConstructor<T>, T> toSiteswapOrNull(final SiteswapRequest siteswapRequest, final InvalidSiteswapException invalidSiteswapException)
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
                return (T) null;
            }
        };
    }

    // endregion


}
