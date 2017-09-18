package com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.SiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.RotationsSiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;

import java.util.function.Function;

/**
 * Created by caspar on 17/09/17.
 */
public class StatesToTwoHandedSiteswapConverter implements Function<VanillaState[], TwoHandedVanillaSiteswap>
{
    private static final boolean DEFAULT_REDUCE = false;
    private static final SortingStrategy DEFAULT_SORTING_STRATEGY = NoSortingStrategy.get();

    private final boolean reduce;
    private final SortingStrategy sortingStrategy;

    public StatesToTwoHandedSiteswapConverter()
    {
        reduce = DEFAULT_REDUCE;
        sortingStrategy = DEFAULT_SORTING_STRATEGY;
    }

    public StatesToTwoHandedSiteswapConverter(final boolean reduce)
    {
        this.reduce = reduce;
        sortingStrategy = DEFAULT_SORTING_STRATEGY;
    }

    public StatesToTwoHandedSiteswapConverter(final SortingStrategy sortingStrategy)
    {
        this.reduce = DEFAULT_REDUCE;
        this.sortingStrategy = sortingStrategy;
    }

    public StatesToTwoHandedSiteswapConverter(final boolean reduce,
                                              final SortingStrategy sortingStrategy)
    {
        this.reduce = reduce;
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public TwoHandedVanillaSiteswap apply(VanillaState[] states)
    {
        if (reduce)
        {
            states = Reducer.reduce(states);
        }

        VanillaThro[] thros = StatesToThrosConverter.get().andThen(ThrosToVanillaThrosConverter.get()).apply(states);

        final SiteswapSorter sorter = new RotationsSiteswapSorter(states, sortingStrategy);

        sorter.sort();

        states = StatesToVanillaStatesConverter.get().apply(sorter.getWinningSort());

        thros = sorter.sortToMatch(thros);

        return new TwoHandedVanillaSiteswap(states, thros, sortingStrategy);
    }

    @Override
    public String toString()
    {
        return "StatesToTwoHandedSiteswapConverter{" +
                "reduce=" + reduce +
                ", sortingStrategy=" + sortingStrategy +
                '}';
    }
}
