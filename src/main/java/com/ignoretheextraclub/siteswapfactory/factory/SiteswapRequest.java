package com.ignoretheextraclub.siteswapfactory.factory;

import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.sorters.StartingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.NoStartingStrategy;
import com.sun.istack.internal.Nullable;

/**
 * An object that encapsulates a request for a siteswap to be constructed.
 *
 * @author Caspar Nonclercq
 * @see SiteswapRequestBuilder for a builder that can be used as {@code private static final} fields
 */
public class SiteswapRequest
{
	/**
	 * The default {@link Reducer} to use if reducing is requested, but no {@code Reducer} is provided.
	 */
	private static final Reducer DEFAULT_REDUCER = Reducer.identity();

	/**
	 * The default {@link StartingStrategy} to use if one not supplied.
	 */
	private static final NoStartingStrategy NO_OP_STARTING_STRATEGY = NoStartingStrategy.get();

	private final Object constructor;
	private final Reducer reducer;
	private final StartingStrategy startingStrategy;

	/**
	 * Constructs a siteswap request.
	 *
	 * @param constructor      The object that will be used to construct the siteswap via the {@link
	 *                         SiteswapConstructor} interface.
	 * @param reducer          A nullable reducer to reduce the siteswap, a default reducer {@link #DEFAULT_REDUCER} is
	 *                         provided if null.
	 * @param startingStrategy The starting strategy to use, may not be respected.
	 */
	public SiteswapRequest(final Object constructor,
	                       @Nullable final Reducer reducer,
	                       @Nullable final StartingStrategy startingStrategy)
	{
		this.constructor = Objects.requireNonNull(constructor, "constructor cannot be null");
		this.reducer = reducer == null ? DEFAULT_REDUCER : reducer;
		this.startingStrategy = startingStrategy == null ? NO_OP_STARTING_STRATEGY : startingStrategy;
	}

	/**
	 * A default siteswap constructor.
	 *
	 * @param constructor the object to construct with.
	 */
	public SiteswapRequest(final Object constructor)
	{
		this(constructor, null, null);
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
	 * Returns the {@code Reducer} that should be used if reducing is desired, otherwise the {@link #DEFAULT_REDUCER} is returned
	 * @return The reducer to use
	 */
	public Reducer getReducer()
	{
		return reducer;
	}

	/**
	 * Returns the desired starting strategy, otherwise {@link #NO_OP_STARTING_STRATEGY}
	 *
	 * @return A starting strategy, never null.
	 */
	public StartingStrategy getStartingStrategy()
	{
		return startingStrategy;
	}

	/**
	 * Returns this constructor to an equivalent builder, with the constructor missing of course.
	 *
	 * @return A Builder matching this
	 */
	public SiteswapRequestBuilder toBuilder()
	{
		return new SiteswapRequestBuilder()
			.withReducer(reducer)
			.withStartingStrategy(startingStrategy);
	}
}
