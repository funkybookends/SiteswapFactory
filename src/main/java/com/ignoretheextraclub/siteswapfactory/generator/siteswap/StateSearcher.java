/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.impl.GeneralPathIterator;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import static java.util.Collections.emptyIterator;

/**
 * The iterator underlying the siteswap generator.
 *
 * @author Caspar Nonclercq
 */
public class StateSearcher<T extends Siteswap> implements Iterator<T>, SiteswapGenerator<T>
{
    private static final Logger LOG = LoggerFactory.getLogger(StateSearcher.class);

    /**
     * Configuration
     */
    private final Iterator<State> startingStates;
    private final int maxPeriod;
    private final Predicate<GeneralPath> intermediatePredicate;
    private final Predicate<GeneralCircuit> resultPredicate;
    private final SiteswapRequestBuilder siteswapRequestBuilder;
    private final SiteswapConstructor<T> siteswapConstructor;

    private T next;
    private GeneralPathIterator routeIterator;

    /**
     * Creates a {@link StateSearcher} which will find successive {@link Siteswap}s based on the provided configuration.
     *
     * @param startingStates        The list of starting states which are acceptable.
     * @param maxPeriod             The maximum period of {@link Siteswap}. To restrict to a specific period, include an {@code resultPredicate} requiring this period. This should be set accurately to ensure good performance.
     * @param intermediatePredicate A predicate that will be tested after each addition of a new {@link State} to the internal State[]. If the intermediate state fails, then the last state will be removed, and the next state will be found.
     * @param resultPredicate       A predicate that will be tested prior to attempting to construct a {@link Siteswap}. If it fails then the {@code siteswapConstructor} will not be invoked.
     * @param siteswapConstructor   A function which takes a {@link State}[] and returns a {@link Siteswap}.
     */
    public StateSearcher(final Set<State> startingStates,
                         final int maxPeriod,
                         final Predicate<GeneralPath> intermediatePredicate,
                         final Predicate<GeneralCircuit> resultPredicate,
                         final SiteswapConstructor<T> siteswapConstructor,
                         final SiteswapRequestBuilder siteswapRequestBuilder)
    {
        this.startingStates = startingStates != null ? startingStates.iterator() : emptyIterator();
        this.siteswapConstructor = Objects.requireNonNull(siteswapConstructor);
        this.maxPeriod = maxPeriod;
        this.intermediatePredicate = intermediatePredicate == null ? acceptAll() : intermediatePredicate;
        this.resultPredicate = resultPredicate == null ? acceptAll() : resultPredicate;
        this.siteswapRequestBuilder = siteswapRequestBuilder == null ? new SiteswapRequestBuilder() : siteswapRequestBuilder;

        prepareNewRouteIterator();
        this.next = getNext().orElse(null);
    }

    @Override
    public boolean hasNext()
    {
        return next !=  null;
    }

    @Override
    public T next()
    {
        if (this.next == null)
        {
            throw new NoSuchElementException();
        }

        final T next = this.next;

        this.next = getNext().orElse(null);

        return next;
    }

    private Optional<T> getNext()
    {
        Optional<T> candidate = Optional.empty();

        while (!(routeIterator == null || candidate.isPresent()))
        {
            if (routeIterator != null && routeIterator.hasNext())
            {
                candidate = Optional.of(routeIterator.next())
                    .filter(GeneralPath::isGeneralCircuit)
                    .map(GeneralPath::toGeneralCircuit)
                    .filter(resultPredicate)
                    .map(this::safeConstruct);
            }
            else
            {
                prepareNewRouteIterator();
            }
        }

        return candidate;
    }

    private T safeConstruct(final GeneralCircuit generalCircuit)
    {
        try
        {
            return siteswapConstructor.apply(siteswapRequestBuilder.createSiteswapRequest(generalCircuit));
        }
        catch (final Exception exception)
        {
            return null;
        }
    }

    private void prepareNewRouteIterator()
    {
        if (this.startingStates.hasNext())
        {
            this.routeIterator = null; // TODO fix
        }
        else
        {
            this.routeIterator = null;
        }
    }

    public Stream<T> generate()
    {
        final Spliterator<T> siteswapSpliterator = Spliterators.spliteratorUnknownSize(this,
            Spliterator.ORDERED | Spliterator.NONNULL);

        return StreamSupport.stream(siteswapSpliterator, false).unordered();
    }

    static <T> Predicate<T> acceptAll()
    {
        return (any) -> true;
    }
}
