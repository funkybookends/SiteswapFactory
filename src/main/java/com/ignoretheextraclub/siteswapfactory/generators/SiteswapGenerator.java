package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<SequencePredicate> sequencePredicates = new ArrayList<>();
    private List<SequencePredicate> loopPredicates = new ArrayList<>();
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

    public SiteswapGenerator addResultPredicate(final SequencePredicate sequencePredicate)
    {
        this.loopPredicates.add(sequencePredicate);
        return this;
    }

    public SiteswapGenerator addIntermediatePredicate(final SequencePredicate sequencePredicate)
    {
        this.sequencePredicates.add(sequencePredicate);
        return this;
    }

    // ========== Generators

    public Stream<Siteswap> generate()
    {
        final Spliterator<Siteswap> siteswapSpliterator = Spliterators.spliteratorUnknownSize(new StateSearcher(
                startingStates,
                maxPeriod,
                sequencePredicates,
                loopPredicates,
                siteswapConstructor), Spliterator.ORDERED | Spliterator.NONNULL);

        return StreamSupport.stream(siteswapSpliterator, false).unordered();
    }

    public Stream<Siteswap> generateDistinct()
    {
        return generate().distinct();
    }
}