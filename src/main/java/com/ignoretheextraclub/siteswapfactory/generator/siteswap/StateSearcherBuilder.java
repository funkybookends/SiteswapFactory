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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 * A builder class for a state searcher. Can be reused with different {@link SiteswapConstructor}s
 * to generate multiple types.
 *
 * @author Caspar Nonclercq
 */
public class StateSearcherBuilder<T extends Siteswap>
{
    private Set<State> startingStates = new HashSet<>();
    private int maxPeriod = -1;
    private Predicate<GeneralPath> intermediatePredicate = StateSearcher.acceptAll();
    private Predicate<GeneralCircuit> resultPredicate = StateSearcher.acceptAll();
    private SiteswapConstructor<T> siteswapConstructor;
    private SiteswapRequestBuilder siteswapRequestBuilder = new SiteswapRequestBuilder();

    private StateSearcherBuilder()
    {
        // Use static builder method
    }

    public static <T extends Siteswap> StateSearcherBuilder<T> builder()
    {
        return new StateSearcherBuilder<>();
    }

    /**
     * This method will override all the starting states currently supplied.
     *
     * @param startingStates The set of starting states, or null to empty.
     *
     * @return this
     */
    public StateSearcherBuilder<T> setStartingStates(final Set<State> startingStates)
    {
        this.startingStates = startingStates != null ? startingStates : new HashSet<>();
        return this;
    }

    /**
     * Add a new starting state to the current set.
     *
     * @param state A new starting state to consider
     *
     * @return this
     */
    public StateSearcherBuilder<T> addStartingState(final State state)
    {
        this.startingStates.add(state);
        return this;
    }

    /**
     * Sets the starting max period.
     *
     * @param maxPeriod The max period to consider
     *
     * @return this
     */
    public StateSearcherBuilder<T> setMaxPeriod(final int maxPeriod)
    {
        this.maxPeriod = maxPeriod;
        return this;
    }

    /**
     * Overwrite the existing intermediatePredicates.
     *
     * @param intermediatePredicate The new intermediatePredicate
     *
     * @return this.
     */
    public StateSearcherBuilder<T> setIntermediatePredicate(final Predicate<GeneralPath> intermediatePredicate)
    {
        this.intermediatePredicate = intermediatePredicate;
        return this;
    }

    /**
     * {@link Predicate#and(Predicate)}s this predicate with the current intermediate predicate.
     *
     * @param intermediatePredicate a new intermediate predicate
     * @return this
     */
    public StateSearcherBuilder<T> andIntermediatePredicate(final Predicate<GeneralPath> intermediatePredicate)
    {
        this.intermediatePredicate = this.intermediatePredicate.and(intermediatePredicate);
        return this;
    }

    /**
     * Get the current intermediatePredicate.
     *
     * @return the current intermediatePredicate
     */
    public Predicate<GeneralPath> getIntermediatePredicate()
    {
        return intermediatePredicate;
    }

    /**
     * Overwrite the existing resultPredicates.
     *
     * @param resultPredicate The new resultPredicate
     *
     * @return this.
     */
    public StateSearcherBuilder<T> setResultPredicate(final Predicate<GeneralCircuit> resultPredicate)
    {
        this.resultPredicate = resultPredicate;
        return this;
    }

    /**
     * {@link Predicate#and(Predicate)}s this predicate with the current result predicate.
     *
     * @param resultPredicate a new result predicate
     * @return this
     */
    public StateSearcherBuilder<T> andResultPredicate(final Predicate<GeneralCircuit> resultPredicate)
    {
        this.resultPredicate = this.resultPredicate.and(resultPredicate);
        return this;
    }

    /**
     * Get the current resultPredicate
     *
     * @return the current resultPredicate
     */
    public Predicate<GeneralCircuit> getResultPredicate()
    {
        return resultPredicate;
    }

    public StateSearcherBuilder<T> withSiteswapConstructor(final SiteswapConstructor<T> siteswapConstructor)
    {
        this.siteswapConstructor = siteswapConstructor;
        return this;
    }

    public StateSearcherBuilder<T> withSiteswaRequestBuilder(final SiteswapRequestBuilder siteswaRequestBuilder)
    {
        this.siteswapRequestBuilder = siteswaRequestBuilder;
        return this;
    }

    public SiteswapGenerator<T> create()
    {
        return new StateSearcher<>(startingStates,
                maxPeriod,
                intermediatePredicate,
                resultPredicate,
                siteswapConstructor,
                siteswapRequestBuilder);
    }
}