package com.ignoretheextraclub.siteswapfactory.sorters.impl;

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
        this.sorted = sorted;
        this.winningIndex = winningIndex;
        this.startingStrategy = startingStrategy;
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
}
