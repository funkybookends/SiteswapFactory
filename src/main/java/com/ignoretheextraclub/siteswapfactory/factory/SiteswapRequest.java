package com.ignoretheextraclub.siteswapfactory.factory;

import java.util.Objects;
import java.util.Optional;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.StreamingMappingReducingStartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoStartingStrategy;

/**
 * An object that encapsulates a request for a siteswap to be constructed.
 *
 * @author Caspar Nonclercq
 * @see #SiteswapRequest(Object, boolean, Reducer, StartFinder, StartingStrategy)
 */
public class SiteswapRequest
{
    /**
     * The default {@link Reducer} to use if reducing is requested, but no {@code Reducer} is provided.
     */
    private static final Reducer DEFAULT_REDUCER = StreamingFilteringReducer.get();

    /**
     * The default {@link StartFinder} to use if a {@link StartingStrategy} is supplied, but no {@code StartFinder} is supplied.
     */
    private static final StartFinder DEFAULT_START_FINDER = StreamingMappingReducingStartFinder.get();

    /**
     * The default {@link StartingStrategy} to use if one is requested, but no {@link StartFinder} was supplied.
     */
    private static final NoStartingStrategy NO_OP_STARTING_STRATEGY = NoStartingStrategy.get();

    private final Object constructor;
    private final Reducer reducer;
    private final boolean reduce;
    private final StartFinder startFinder;
    private final StartingStrategy startingStrategy;

    /**
     * Constructs a siteswap request.
     *
     * @param constructor      The object that will be used to construct the siteswap via the {@link
     *                         SiteswapConstructor} interface.
     * @param reduce           True if you want the siteswap to be reduced, may not be respected.
     * @param reducer          A nullable reducer to reduce the siteswap, a default reducer {@link #DEFAULT_REDUCER} is
     *                         provided if a reducer is not provided but {@code reduce} is {@code true}
     * @param startFinder      A nullable start finder. If no {@link StartFinder} is provided byt a {@code
     *                         startingStrategy} is,  a default one will be provided.
     * @param startingStrategy The starting strategy to use, may not be respected.
     */
    public SiteswapRequest(final Object constructor,
                           final boolean reduce,
                           final Reducer reducer,
                           final StartFinder startFinder,
                           final StartingStrategy startingStrategy)
    {
        this.constructor = Objects.requireNonNull(constructor, "constructor cannot be null");
        this.reduce = reduce;
        this.reducer = reduce ? Optional.ofNullable(reducer).orElse(getDefaultReducer()) : null;
        this.startFinder = startingStrategy == null ? null : Optional.ofNullable(startFinder).orElse(getDefaultStartFinder());
        this.startingStrategy = startingStrategy;
    }

    /**
     * A default siteswap constructor.
     * @param constructor the object to construct with.
     */
    public SiteswapRequest(final Object constructor)
    {
        this(constructor, false, null, null, null);
    }

    /**
     * Returns the object to use to construct the siteswap.
     *
     * @return The siteswap constructor, never null.
     */
    public Object getConstructor()
    {
        return constructor;
    }

    /**
     * Returns the {@code Reducer} that should be used if reducing is desired, otherwise {@link Optional#empty()}
     *
     * @return A {@code Reducer} if the caller should reduce, otherwise {@link Optional#empty()}
     */
    public Optional<Reducer> getReducer()
    {
        return reduce ? Optional.of(reducer) : Optional.empty();
    }

    /**
     * Returns a {@code StartFinder} if the request wants {@link StartingStrategy} to be used.
     *
     * @return A {@code StartFinder} if the caller should attempt to use a {@code StartingStrategy}
     * @see #getStartingStrategy()
     */
    public Optional<StartFinder> getStartFinder()
    {
        if (startingStrategy == null)
        {
            return Optional.empty();
        }
        return Optional.of(startFinder);
    }

    /**
     * Returns the desired starting strategy, otherwise {@link #NO_OP_STARTING_STRATEGY}
     *
     * @return A starting strategy, never null.
     */
    public StartingStrategy getStartingStrategy()
    {
        return getStartFinder().map(sf -> startingStrategy).orElse(getNoOpStartingStrategy());
    }

    /**
     * Returns the default reducer to use if reducing is requested, but no {@link Reducer} is provided.
     */
    protected static Reducer getDefaultReducer()
    {
        return DEFAULT_REDUCER;
    }

    /**
     * Returns the default {@link StartFinder} to use if a {@link StartingStrategy} is supplied, but no {@code StartFinder} is supplied.
     */
    protected static StartFinder getDefaultStartFinder()
    {
        return DEFAULT_START_FINDER;
    }

    /**
     * Returns the default {@link StartingStrategy} to use if one is requested, but no {@link StartFinder} was supplied.
     */
    protected static NoStartingStrategy getNoOpStartingStrategy()
    {
        return NO_OP_STARTING_STRATEGY;
    }
}
