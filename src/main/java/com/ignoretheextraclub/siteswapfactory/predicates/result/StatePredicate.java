package com.ignoretheextraclub.siteswapfactory.predicates.result;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A predicate that checks to see if a {@code State[]} contains a given state.
 *
 * This is not suitable as an intermediate Predicate.
 *
 * @author Caspar Nonclercq
 */
public class StatePredicate implements Predicate<State[]>
{
    private final State requiredState;

    public StatePredicate(final State requiredState)
    {
        this.requiredState = requiredState;
    }

    public boolean test(final State[] states)
    {
        return Stream.of(states).anyMatch(requiredState::equals);
    }

    public static Predicate<State[]> anyOf(final State... states)
    {
        if (states.length == 0)
        {
            throw new IllegalArgumentException("No states provided");
        }

        Predicate<State[]> predicate = new StatePredicate(states[0]);

        if (states.length > 1)
        {
            for (int i = 1; i < states.length; i++)
            {
                predicate = predicate.or(new StatePredicate(states[i]));
            }
        }

        return predicate;
    }

    public static Predicate<State[]> noneOf(final State... states)
    {
        if (states.length == 0)
        {
            throw new IllegalArgumentException("No states provided");
        }

        Predicate<State[]> predicate = new StatePredicate(states[0]).negate();

        if (states.length > 1)
        {
            for (int i = 1; i < states.length; i++)
            {
                predicate = predicate.and(new StatePredicate(states[i]).negate());
            }
        }

        return predicate;
    }
}
