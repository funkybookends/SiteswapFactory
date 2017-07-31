package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.generators.predicates.IntermediateStatePredicate;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.ReturnStatePredicate;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.impl.LastStateTransitionsToFirstStatePredicate;
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
    private IntermediateStatePredicate intermediateStatePredicate;
    private ReturnStatePredicate returnStatePredicate = LastStateTransitionsToFirstStatePredicate.get(); // Always required
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
        this.statePredicate = statePredicate.and(this.statePredicate);
        return this;
    }

    public SiteswapGenerator addPredicate(final IntermediateStatePredicate intermediateStatePredicate)
    {
        this.intermediateStatePredicate = intermediateStatePredicate.and(this.intermediateStatePredicate);
        return this;
    }

    public SiteswapGenerator addPredicate(final ReturnStatePredicate returnStatePredicate)
    {
        this.returnStatePredicate = returnStatePredicate.and(this.returnStatePredicate);
        return this;
    }

    // ========== Generators

    public Stream<Siteswap> generate()
    {
        final Spliterator<Siteswap> siteswapSpliterator = Spliterators.spliteratorUnknownSize(new StateSearcher(
                startingStates,
                maxPeriod,
                statePredicate, intermediateStatePredicate, returnStatePredicate,
                siteswapConstructor), Spliterator.SIZED);

        return StreamSupport.stream(siteswapSpliterator, false).unordered();
    }

    public Stream<Siteswap> generateDistinct()
    {
         return generate().distinct();
    }
}