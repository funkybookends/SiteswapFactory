package com.ignoretheextraclub.siteswapfactory.factory;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.sorters.StartingStrategy;

/**
 * A builder for the SiteswapRequest that is thread safe and therefor suitable to use as
 * {@code private static final} fields. Each call to the builder returns a new builder with
 * the existing properties and the new field overridden.
 *
 * @author Caspar Nonclercq
 */
public class SiteswapRequestBuilder
{
    private final Reducer reducer;
    private final StartingStrategy startingStrategy;

    /**
     * A new {@link SiteswapRequestBuilder} with the default values.
     */
    public SiteswapRequestBuilder()
    {
        reducer = null;
        startingStrategy = null;
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the provided values.
     *
     * @param reducer          The value for the {@link SiteswapRequest}.
     * @param startingStrategy The value for the {@link SiteswapRequest}.
     */
    public SiteswapRequestBuilder(final Reducer reducer, final StartingStrategy startingStrategy)
    {
        this.reducer = reducer;
        this.startingStrategy = startingStrategy;
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the field overridden with the provided value.
     *
     * @param reducer The field for the {@link SiteswapRequest}
     * @return A new {@link SiteswapRequestBuilder}.
     */
    public SiteswapRequestBuilder withReducer(final Reducer reducer)
    {
        return new SiteswapRequestBuilder(reducer, startingStrategy);
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the field overridden with the provided value.
     *
     * @param startingStrategy The field for the {@link SiteswapRequest}
     * @return A new {@link SiteswapRequestBuilder}.
     */
    public SiteswapRequestBuilder withStartingStrategy(final StartingStrategy startingStrategy)
    {
        return new SiteswapRequestBuilder(reducer, startingStrategy);
    }

    /**
     * Creates a new {@link SiteswapRequest} with the given constructor
     *
     * @param constructor The field in the {@link SiteswapRequest}
     * @return a {@link SiteswapRequest}
     */
    public SiteswapRequest createSiteswapRequest(final Object constructor)
    {
        return new SiteswapRequest(constructor, reducer, startingStrategy);
    }
}