package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.IntermediateStatePredicate;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SiteswapGenerator
{
    private final int MAX_PERIOD = 15;

    private Set<State> startingStates = new HashSet<>();
    private final int maxPeriod;
    private StatePredicate statePredicate;
    private IntermediateStatePredicate intermediateStatePredicates;
    private ReturnStatePredicate returnStatePredicates;
    private final Function<State[], Siteswap> siteswapConstructor;

    public SiteswapGenerator(final int maxPeriod, final Function<State[], Siteswap> siteswapConstructor)
    {
        if (maxPeriod > MAX_PERIOD || maxPeriod < 1)
        {
            throw new IllegalArgumentException("Max period out of bound.");
        }
        this.maxPeriod = maxPeriod;
        this.siteswapConstructor = Objects.requireNonNull(siteswapConstructor, "siteswapConstructor must not be null");
    }

    public SiteswapGenerator addStartingState(final State startingState)
    {
        this.startingStates.add(startingState);
        return this;
    }

    public SiteswapGenerator addPredicate(final StatePredicate statePredicate)
    {
        this.statePredicate = andPredicate(this.statePredicate, statePredicate);
        return this;
    }

    public SiteswapGenerator addPredicate(final IntermediateStatePredicate intermediateStatePredicate)
    {
        this.intermediateStatePredicates = andPredicate(this.intermediateStatePredicates, intermediateStatePredicate);
        return this;
    }

    public SiteswapGenerator addPredicate(final ReturnStatePredicate returnStatePredicate)
    {
        this.returnStatePredicates = andPredicate(this.returnStatePredicates, returnStatePredicate);
        return this;
    }

    // ========== Generators

    public Stream<Siteswap> generate()
    {
        final Spliterator<Siteswap> siteswapSpliterator = Spliterators.spliteratorUnknownSize(new StateSearcher(
                startingStates,
                maxPeriod,
                statePredicate,
                intermediateStatePredicates,
                returnStatePredicates,
                siteswapConstructor), Spliterator.SIZED);

        return StreamSupport.stream(siteswapSpliterator, false).unordered();
    }

    public Stream<Siteswap> generateDistinct()
    {
         return generate().distinct();
    }

    // Private members

    private StatePredicate andPredicate(final StatePredicate first, final StatePredicate second)
    {
        if (first != null && second != null)
        {
            return (StatePredicate) first.and(second);
        }
        else if (first == null)
        {
            return second;
        }
        else
        {
            return first;
        }
    }

    private IntermediateStatePredicate andPredicate(final IntermediateStatePredicate first, final IntermediateStatePredicate second)
    {
        if (first != null && second != null)
        {
            return (IntermediateStatePredicate) first.and(second);
        }
        else if (first == null)
        {
            return second;
        }
        else
        {
            return first;
        }
    }

    private ReturnStatePredicate andPredicate(final ReturnStatePredicate first, final ReturnStatePredicate second)
    {
        if (first != null && second != null)
        {
            return (ReturnStatePredicate) first.and(second);
        }
        else if (first == null)
        {
            return second;
        }
        else
        {
            return first;
        }
    }
}