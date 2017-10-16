package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils;

/**
 * A simple sorting strategy
 */
public class StreamingMappingReducingStartFinder implements StartFinder
{
    public static StreamingMappingReducingStartFinder INSTANCE;

    private StreamingMappingReducingStartFinder()
    {
        // Singleton
    }

    public static StreamingMappingReducingStartFinder get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StreamingMappingReducingStartFinder();
        }
        return INSTANCE;
    }

    @Override
    public StartFinderResult sort(final State[] stateArray,
                                  final StartingStrategy startingStrategy) throws InvalidSiteswapException
    {
        return IntStream.range(0, stateArray.length)
                        .boxed()
                        .map(startIndex -> new SimpleStartFinderResult(ArrayUtils.getRotatedCopy(stateArray, startIndex), startIndex, startingStrategy))
                        .reduce(toBestSort(startingStrategy))
                        .orElseThrow(() -> new IllegalArgumentException("stateArray must have at least one element"));
    }

    private BinaryOperator<SimpleStartFinderResult> toBestSort(final StartingStrategy startingStrategy)
    {
        return (candidate1, candidate2) -> startingStrategy.test(candidate1.getSorted(), candidate2.getSorted()) ? candidate1 : candidate2;
    }
}
