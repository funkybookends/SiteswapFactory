package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

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
public class GroundedPredicate implements Predicate<GeneralPath>
{
    private static GroundedPredicate instance;

    private GroundedPredicate()
    {
        // Singleton
    }

    @Override
    public boolean test(final GeneralPath generalPath)
    {
        return test(generalPath.getStates());
    }

    public boolean test(final State[] states)
    {
        Objects.requireNonNull(states, "states cannot be null");
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

    public static boolean isGround(final GeneralCircuit generalCircuit)
    {
        return get().test(generalCircuit.getAllStates());
    }

    public static boolean isGround(final GeneralPath generalPath)
    {
        return get().test(generalPath.getStates());
    }
}
