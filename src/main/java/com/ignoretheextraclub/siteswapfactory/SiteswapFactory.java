package com.ignoretheextraclub.siteswapfactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

/**
 * A factory for creating {@link Siteswap}s. Provides static methods to create {@code Siteswap}s with the
 * default configuration, static methods to get different standard configurations, and a constructor
 * to specify your own configuration.
 *
 * @author Caspar Nonclercq
 */
public final class SiteswapFactory
{

    /**
     * An ordered list of siteswap constructors that will be used, in order, to create a siteswap.
     */
    private final List<SiteswapConstructor<? extends Siteswap>> constructors;

    /**
     * The base {@link SiteswapRequestBuilder} to use.
     */
    private final SiteswapRequestBuilder siteswapRequestBuilder;

    /**
     * Takes a list of constructors to use with the default configuration for building those {@link Siteswap}s.
     *
     * @param constructors The list of constructors to use
     * @see #SiteswapFactory(List, SiteswapRequestBuilder)
     */
    public SiteswapFactory(final List<SiteswapConstructor<? extends Siteswap>> constructors)
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
    public SiteswapFactory(final List<SiteswapConstructor<? extends Siteswap>> constructors,
                           final SiteswapRequestBuilder siteswapRequestBuilder)
    {
        this.constructors = Collections.unmodifiableList(new ArrayList<>(constructors));
        this.siteswapRequestBuilder = siteswapRequestBuilder;
    }

    /**
     * Gets the first siteswap that a constructor from {@link SiteswapFactory#constructors} could construct.
     *
     * @param siteswap The object to use to construct the siteswap with.
     * @return A siteswap
     * @throws InvalidSiteswapException If no constructor available could construct a siteswap.
     */
    public Siteswap getOne(final Object siteswap) throws InvalidSiteswapException
    {
        final InvalidSiteswapException invalidSiteswapException = new InvalidSiteswapException(
            "Could not find a suitable constructor for siteswap request");

        return getSiteswapStream(siteswap, invalidSiteswapException)
            .findFirst()
            .orElseThrow(() -> invalidSiteswapException);
    }

    /**
     * Gets all the {@link Siteswap}s, in the same order as the {@link SiteswapFactory#constructors} could construct.
     * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
     * be shorter than the {@link SiteswapFactory#constructors} list, or empty.
     *
     * @param siteswap The object to use to construct the siteswap with.
     * @return A modifiable list of {@link Siteswap}s.
     */
    public List<Siteswap> getAll(final Object siteswap)
    {
        return getSiteswapStream(siteswap, null)
            .collect(Collectors.toList());
    }

    /**
     * Returns a stream of {@link Siteswap} using the
     *
     * @param siteswap                 The constructor
     * @param invalidSiteswapException A nullable exception to populate.
     * @return A stream of siteswaps.
     */
    private Stream<Siteswap> getSiteswapStream(final Object siteswap, final InvalidSiteswapException invalidSiteswapException)
    {
        final SiteswapRequest siteswapRequest = siteswapRequestBuilder.createSiteswapRequest(siteswap);

        return this.constructors.stream()
            .filter(siteswapConstructor -> siteswapConstructor.accepts(siteswapRequest.getConstructor().getClass()))
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
            catch (final InvalidSiteswapException cause)
            {
                if (invalidSiteswapException != null)
                {
                    invalidSiteswapException.addSuppressed(cause);
                }
                return (Siteswap) null;
            }
        };
    }

    // region TwoHandedSiteswap

    /**
     * A list of {@link com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap} {@link SiteswapConstructor}s.
     */
    private static final List<SiteswapConstructor<? extends Siteswap>> TWO_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
        StringToTwoHandedSiteswapConstructor.get()
    );

    public static TwoHandedSiteswap getTwoHandedSiteswap(final Object siteswap)
    {
        final SiteswapFactory siteswapFactory = new SiteswapFactory(TWO_HANDED_SITESWAP_CONSTRUCTORS);
        return (TwoHandedSiteswap) siteswapFactory.getOne(siteswap);
    }

    // endregion

    // region FourHandedSiteswap

    /**
     * A list of {@link com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap} {@link SiteswapConstructor}s.
     */
    private static final List<SiteswapConstructor<? extends Siteswap>> FOUR_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
        StringToFourHandedSiteswapConstructor.get()
    );

    public static FourHandedSiteswap getFourHandedSiteswap(final Object siteswap)
    {
        final SiteswapFactory siteswapFactory = new SiteswapFactory(FOUR_HANDED_SITESWAP_CONSTRUCTORS);
        return (FourHandedSiteswap) siteswapFactory.getOne(siteswap);
    }

    // endregion
}
