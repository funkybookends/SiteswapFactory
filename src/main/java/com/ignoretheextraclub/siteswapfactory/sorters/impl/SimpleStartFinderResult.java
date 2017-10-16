package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import java.util.Arrays;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

/**
 * A simple POJO that holds the result of the {@link StartFinder#sort(State[], StartingStrategy)}
 */
public class SimpleStartFinderResult implements StartFinderResult
{
    /**
     * The result of the finding
     */
    protected final State[] sorted;

    /**
     * The winning index
     */
    protected final int winningIndex;

    /**
     * The {@link StartingStrategy} used
     */
    protected final StartingStrategy startingStrategy;

    public SimpleStartFinderResult(final State[] sorted,
                                   final int winningIndex,
                                   final StartingStrategy startingStrategy)
    {
        this.sorted = Objects.requireNonNull(sorted, "sorted cannot be null");
        this.winningIndex = Objects.requireNonNull(winningIndex, "winningIndex cannot be null");
        this.startingStrategy = Objects.requireNonNull(startingStrategy, "startingStrategy cannot be null");
    }

    @Override
    public State[] getSorted()
    {
        return sorted;
    }

    @Override
    public int getWinningIndex()
    {
        return winningIndex;
    }

    @Override
    public StartingStrategy getStartingStrategy()
    {
        return startingStrategy;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SimpleStartFinderResult that = (SimpleStartFinderResult) o;

        if (winningIndex != that.winningIndex) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(sorted, that.sorted)) return false;
        return Objects.equals(startingStrategy, that.startingStrategy);
    }

    @Override
    public int hashCode()
    {
        int result = Arrays.hashCode(sorted);
        result = 31 * result + winningIndex;
        result = 31 * result + startingStrategy.hashCode();
        return result;
    }
}
