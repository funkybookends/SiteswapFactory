package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This predicate tests if a {@link com.ignoretheextraclub.siteswapfactory.siteswap.State}[] is grounded.
 *
 * A {@link com.ignoretheextraclub.siteswapfactory.siteswap.State}[] is grounded if it contains at least one ground state.
 * The concept of a ground state is defined by the {@link com.ignoretheextraclub.siteswapfactory.siteswap.State}.
 *
 * This is suitable as an intermediate Predicate
 *
 * @author Caspar Nonclercq
 */
public class GroundedPredicate implements Predicate<State[]>
{
    private static GroundedPredicate instance;

    private GroundedPredicate()
    {
        // Singleton
    }

    @Override
    public boolean test(final State[] states)
    {
        return Stream.of(states).anyMatch(State::isGroundState);
    }

    public static GroundedPredicate get()
    {
        if (instance == null)
        {
            instance = new GroundedPredicate();
        }
        return instance;
    }

    public static boolean isGround(final State[] states)
    {
        return get().test(states);
    }

    public static boolean isGround(final Siteswap siteswap)
    {
        return get().test(siteswap.getStates());
    }
}
