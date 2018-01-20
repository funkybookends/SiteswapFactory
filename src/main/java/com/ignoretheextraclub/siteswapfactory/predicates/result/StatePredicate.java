package com.ignoretheextraclub.siteswapfactory.predicates.result;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

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

    public static Predicate<GeneralPath> generalPathPredicate(final StatePredicate statePredicate)
    {
        return (generalPath -> statePredicate.test(generalPath.getStates()));
    }

    public static Predicate<GeneralCircuit> generalCircuitPredicate(final StatePredicate statePredicate)
    {
        return (generalCircuit -> statePredicate.test(generalCircuit.getAllStates()));
    }

    public static Predicate<State[]> anyOf(final State... states)
    {
        return Arrays.stream(states)
            .map(StatePredicate::new)
            .map(statePredicate -> (Predicate<State[]>) statePredicate)
            .reduce(Predicate::or)
            .orElseGet(() -> (states1) -> false);
    }

    public static Predicate<State[]> noneOf(final State... states)
    {
        return Arrays.stream(states)
            .map(StatePredicate::new)
            .map(statePredicate -> (Predicate<State[]>) statePredicate)
            .reduce(Predicate::or)
            .map(Predicate::negate)
            .orElseGet(() -> (states1) -> true);
    }

    public static Predicate<GeneralCircuit> anyOfInGeneralCircuit(final State... states)
    {
        if (states.length == 0)
        {
            throw new IllegalArgumentException("No states provided");
        }

        Predicate<GeneralCircuit> predicate = generalCircuitPredicate(new StatePredicate(states[0]));

        if (states.length > 1)
        {
            for (int i = 1; i < states.length; i++)
            {
                predicate = predicate.or(generalCircuitPredicate(new StatePredicate(states[i])));
            }
        }

        return predicate;
    }

    public static Predicate<GeneralPath> anyOfInGeneralPath(final State... states)
    {
        if (states.length == 0)
        {
            throw new IllegalArgumentException("No states provided");
        }

        Predicate<GeneralPath> predicate = generalPathPredicate(new StatePredicate(states[0]));

        if (states.length > 1)
        {
            for (int i = 1; i < states.length; i++)
            {
                predicate = predicate.or(generalPathPredicate(new StatePredicate(states[i])));
            }
        }

        return predicate;
    }
}
