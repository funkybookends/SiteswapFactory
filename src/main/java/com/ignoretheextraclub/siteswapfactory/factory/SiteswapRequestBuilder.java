package com.ignoretheextraclub.siteswapfactory.factory;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

/**
 * A builder for the SiteswapRequest that is thread safe and therefor suitable to use as
 * {@code private static final} fields. Each call to the builder returns a new builder with
 * the existing properties and the new field overridden.
 *
 * @author Caspar Nonclercq
 * @see SiteswapRequest#SiteswapRequest(Object, boolean, Reducer, StartFinder, StartingStrategy)
 */
public class SiteswapRequestBuilder
{
    private final boolean reduce;
    private final Reducer reducer;
    private final StartingStrategy startingStrategy;
    private final StartFinder startFinder;

    /**
     * A new {@link SiteswapRequestBuilder} with the default values.
     */
    public SiteswapRequestBuilder()
    {
        reduce = false;
        reducer = null;
        startingStrategy = null;
        startFinder = null;
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the provided values.
     *
     * @param reduce           The value for the {@link SiteswapRequest}.
     * @param reducer          The value for the {@link SiteswapRequest}.
     * @param startingStrategy The value for the {@link SiteswapRequest}.
     * @param startFinder      The value for the {@link SiteswapRequest}.
     */
    public SiteswapRequestBuilder(final boolean reduce, final Reducer reducer, final StartingStrategy startingStrategy, final StartFinder startFinder)
    {
        this.reduce = reduce;
        this.reducer = reducer;
        this.startingStrategy = startingStrategy;
        this.startFinder = startFinder;
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the field overridden with the provided value.
     *
     * @param reduce The field for the {@link SiteswapRequest}
     * @return A new {@link SiteswapRequestBuilder}.
     */
    public SiteswapRequestBuilder withReduce(final boolean reduce)
    {
        return new SiteswapRequestBuilder(reduce, reducer, startingStrategy, startFinder);
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the field overridden with the provided value.
     *
     * @param reducer The field for the {@link SiteswapRequest}
     * @return A new {@link SiteswapRequestBuilder}.
     */
    public SiteswapRequestBuilder withReducer(final Reducer reducer)
    {
        return new SiteswapRequestBuilder(reduce, reducer, startingStrategy, startFinder);
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the field overridden with the provided value.
     *
     * @param startingStrategy The field for the {@link SiteswapRequest}
     * @return A new {@link SiteswapRequestBuilder}.
     */
    public SiteswapRequestBuilder withStartingStrategy(final StartingStrategy startingStrategy)
    {
        return new SiteswapRequestBuilder(reduce, reducer, startingStrategy, startFinder);
    }

    /**
     * A new {@link SiteswapRequestBuilder} with the field overridden with the provided value.
     *
     * @param startFinder The field for the {@link SiteswapRequest}
     * @return A new {@link SiteswapRequestBuilder}.
     */
    public SiteswapRequestBuilder withStartFinder(final StartFinder startFinder)
    {
        return new SiteswapRequestBuilder(reduce, reducer, startingStrategy, startFinder);
    }

    /**
     * Creates a new {@link SiteswapRequest} with the given constructor
     *
     * @param constructor The field in the {@link SiteswapRequest}
     * @return a {@link SiteswapRequest}
     */
    public SiteswapRequest createSiteswapRequest(final Object constructor)
    {
        return new SiteswapRequest(constructor, reduce, reducer, startFinder, startingStrategy);
    }
}