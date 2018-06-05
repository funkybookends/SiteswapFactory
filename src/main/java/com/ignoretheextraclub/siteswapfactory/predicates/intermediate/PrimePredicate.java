package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import java.util.Collections;
import java.util.HashSet;
import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * Checks if a {@link State[]} is prime.
 *
 * A {@link State}[] is prime if no state is contained twice.
 *
 * This is an Intermediate Predicate or a Result Predicate
 *
 * @author Caspar Nonclercq
 */
public class PrimePredicate implements Predicate<GeneralPath>
{
    private static PrimePredicate instance;

    private PrimePredicate()
    {
        // To enforce singleton instance
    }

    @Override
    public boolean test(final GeneralPath generalPath)
    {
        return test(generalPath.getStates());
    }

    public boolean test(final State[] stateArray)
    {
        final HashSet<Object> states = new HashSet<>();
        Collections.addAll(states, stateArray);
        return states.size() == stateArray.length;
    }

    public static PrimePredicate get()
    {
        if (instance == null)
        {
            instance = new PrimePredicate();
        }
        return instance;
    }

    public static boolean isPrime(final State[] states)
    {
        return get().test(states);
    }

    public static boolean isPrime(final Siteswap siteswap)
    {
        return get().test(siteswap.getStates());
    }
}
