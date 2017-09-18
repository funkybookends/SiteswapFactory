package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A builder class for a state searcher. Can be reused with different {@link StateSearcher.SiteswapConstructor}s
 * to generate multiple types.
 *
 * @author Caspar Nonclercq
 */
public class StateSearcherBuilder<T extends Siteswap>
{
    private Set<State> startingStates = new HashSet<>();
    private int maxPeriod = -1;
    private Predicate<State[]> intermediatePredicate = StateSearcher.acceptAll();
    private Predicate<State[]> resultPredicate = StateSearcher.acceptAll();
    private Function<State[], T> siteswapConstructor;

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
    public StateSearcherBuilder<T> setIntermediatePredicate(final Predicate<State[]> intermediatePredicate)
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
    public StateSearcherBuilder<T> addIntermediatePredicate(final Predicate<State[]> intermediatePredicate)
    {
        this.intermediatePredicate = this.intermediatePredicate.and(intermediatePredicate);
        return this;
    }

    /**
     * Get the current intermediatePredicate.
     *
     * @return the current intermediatePredicate
     */
    public Predicate<State[]> getIntermediatePredicate()
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
    public StateSearcherBuilder<T> setResultPredicate(final Predicate<State[]> resultPredicate)
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
    public StateSearcherBuilder<T> andResultPredicate(final Predicate<State[]> resultPredicate)
    {
        this.resultPredicate = this.resultPredicate.and(resultPredicate);
        return this;
    }

    /**
     * Get the current resultPredicate
     *
     * @return the current resultPredicate
     */
    public Predicate<State[]> getResultPredicate()
    {
        return resultPredicate;
    }

    public StateSearcherBuilder<T> withSiteswapConstructor(final Function<State[], T> siteswapConstructor)
    {
        this.siteswapConstructor = siteswapConstructor;
        return this;
    }

    public SiteswapGenerator<T> create()
    {
        return new StateSearcher<>(startingStates,
                maxPeriod,
                intermediatePredicate,
                resultPredicate,
                siteswapConstructor);
    }
}