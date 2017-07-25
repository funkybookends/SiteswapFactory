package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.sorters.SiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils;

/**
 Created by caspar on 26/07/17.
 */
public class RotationsSiteswapSorter<State> implements SiteswapSorter<State>
{
    private final State[] origin;
    private final SortingStrategy<State> sortingStrategy;
    private int winningIndex;
    private boolean sorted = false;

    public RotationsSiteswapSorter(final State[] states, final SortingStrategy<State> sortingStrategy)
    {
        this.origin = states;
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public State[] sort() throws InvalidSiteswapException
    {
        winningIndex = 0;
        Rotation<State> winner = new Rotation<>(origin, winningIndex);
        for (int second = 1; second < origin.length; second++)
        {
            Rotation<State> candidate = new Rotation<State>(origin, second);
            boolean takeFirst = sortingStrategy.takeFirst(winner.getStates(), candidate.getStates());
            if (!takeFirst)
            {
                winner = candidate;
                winningIndex = second;
            }
        }
        sorted = true;
        return winner.getStates();
    }

    @Override
    public int getWinningIndex()
    {
        return winningIndex;
    }

    public State[] getWinningSort()
    {
        if (!sorted)
        {
            throw new IllegalStateException("Cannot when have not sorted");
        }
        return (new Rotation<>(origin, winningIndex)).getStates();
    }

    @Override
    public <T> T[] sortToMatch(final T[] unsorted)
    {
        if (!sorted)
        {
            throw new IllegalStateException("Cannot sort to match when have not sorted.");
        }
        if (unsorted.length != origin.length)
        {
            throw new IllegalArgumentException("Lengths differ");
        }
        return (new Rotation<>(unsorted, winningIndex)).getStates();
    }

    private static class Rotation<S>
    {
        private final S[] states;
        private final int index;

        public Rotation(final S[] states, final int index)
        {
            this.states = ArrayUtils.getCopy(states, index);
            this.index = index;
        }

        public S[] getStates()
        {
            return states;
        }

        public int getIndex()
        {
            return index;
        }
    }
}
