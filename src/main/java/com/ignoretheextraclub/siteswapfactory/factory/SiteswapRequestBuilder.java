package com.ignoretheextraclub.siteswapfactory.factory;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

/**
 * @author Caspar Nonclercq
 * @see SiteswapRequest#SiteswapRequest(Object, boolean, Reducer, StartFinder, StartingStrategy)
 */
public class SiteswapRequestBuilder
{
    private boolean reduce;
    private Reducer reducer;
    private StartingStrategy startingStrategy;
    private StartFinder startFinder;

    public SiteswapRequestBuilder withReduce(final boolean reduce)
    {
        this.reduce = reduce;
        return this;
    }

    public SiteswapRequestBuilder withReducer(final Reducer reducer)
    {
        this.reducer = reducer;
        return this;
    }

    public SiteswapRequestBuilder withStartingStrategy(final StartingStrategy startingStrategy)
    {
        this.startingStrategy = startingStrategy;
        return this;
    }

    public SiteswapRequestBuilder withStartFinder(final StartFinder startFinder)
    {
        this.startFinder = startFinder;
        return this;
    }

    public SiteswapRequest createSiteswapRequest(final Object constructor)
    {
        return new SiteswapRequest(constructor, reduce, reducer, startFinder, startingStrategy);
    }
}