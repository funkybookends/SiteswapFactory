package com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.sorters.SiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.RotationsSiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;

import java.util.function.Function;

/**
 * Created by caspar on 17/09/17.
 */
public class StatesToFourHandedSiteswapConverter implements Function<VanillaState[], FourHandedSiteswap>
{
    private static final boolean DEFAULT_REDUCE = false;
    private static final SortingStrategy DEFAULT_SORTING_STRATEGY = NoSortingStrategy.get();

    private final boolean reduce;
    private final SortingStrategy sortingStrategy;

    public StatesToFourHandedSiteswapConverter()
    {
        reduce = DEFAULT_REDUCE;
        sortingStrategy = DEFAULT_SORTING_STRATEGY;
    }

    public StatesToFourHandedSiteswapConverter(final boolean reduce)
    {
        this.reduce = reduce;
        sortingStrategy = DEFAULT_SORTING_STRATEGY;
    }

    public StatesToFourHandedSiteswapConverter(final SortingStrategy sortingStrategy)
    {
        this.reduce = DEFAULT_REDUCE;
        this.sortingStrategy = sortingStrategy;
    }

    public StatesToFourHandedSiteswapConverter(final boolean reduce,
                                               final SortingStrategy sortingStrategy)
    {
        this.reduce = reduce;
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public FourHandedSiteswap apply(VanillaState[] states)
    {
        if (reduce)
        {
            states = Reducer.reduce(states);
        }

        FourHandedSiteswapThro[] thros = convertToStates().andThen(converToFHSThros()).apply(states);

        final SiteswapSorter sorter = new RotationsSiteswapSorter(states, sortingStrategy);

        sorter.sort();;

        states = StatesToVanillaStatesConverter.get().apply(sorter.getWinningSort());

        thros = sorter.sortToMatch(thros);

        return new FourHandedSiteswap(states, thros, sortingStrategy);
    }

    private ThrosToFourHandedSiteswapThrosConverter converToFHSThros()
    {
        return ThrosToFourHandedSiteswapThrosConverter.get();
    }

    private StatesToThrosConverter convertToStates()
    {
        return StatesToThrosConverter.get();
    }

    @Override
    public String toString()
    {
        return "StatesToFourHandedSiteswapConverter{" +
                "reduce=" + reduce +
                ", sortingStrategy=" + sortingStrategy +
                '}';
    }
}
