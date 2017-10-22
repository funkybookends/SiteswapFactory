package com.ignoretheextraclub.siteswapfactory.generator.sequence;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Joins two {@link State[]} together ensuring transitions are dealth with.
 *
 * @author Caspar Nonclercq
 */
public interface StateJoiner
{
    /**
     * Returns a state array that contains the first {@code State[]), followed by the second {@code State[]} with transition throws if needed
     * and the transition throws to return to the first state.
     *
     * @param first  The first sequence to include
     * @param second The second sequence to include
     * @return A full loop
     * @throws TransitionException if they cannot be joined, or if no join was found.
     */
    State[] join(State[] first, State[] second) throws TransitionException;

    /**
     * Convenence method for passing in two {@link Siteswap}s. Both {@code State[]}s will be extracted
     * and applied to {@link #join(State[], State[])}.
     *
     * @param first  The first siteswap
     * @param second The second siteswap
     * @return The joined states as per {@link #join(State[], State[])}
     */
    default State[] join(final Siteswap first, final Siteswap second) throws TransitionException
    {
        return join(first.getStates(), second.getStates());
    }

    /**
     * Joins two siteswaps together, and using the {@link SiteswapRequestBuilder} and {@link SiteswapConstructor} constructs
     * a siteswap with them.
     *
     * @param first                  The first siteswap
     * @param second                 The second siteswap
     * @param siteswapRequestBuilder The siteswapRequestBuilder to use.
     * @param constructor
     * @return
     * @throws TransitionException
     */
    default Siteswap join(final Siteswap first,
                          final Siteswap second,
                          final SiteswapRequestBuilder siteswapRequestBuilder,
                          final SiteswapConstructor<Siteswap> constructor) throws TransitionException
    {
        final State[] states = join(first, second);

        if (!constructor.accepts(states))
        {
            throw new IllegalArgumentException("Constructor provided does not support " + states.getClass().getCanonicalName());
        }

        final SiteswapRequest siteswapRequest = siteswapRequestBuilder.createSiteswapRequest(states);

        return constructor.apply(siteswapRequest);
    }
}
