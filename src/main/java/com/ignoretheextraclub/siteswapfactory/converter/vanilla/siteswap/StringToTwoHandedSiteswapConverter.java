package com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.compound.VanillaThrosToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StatesToVanillaStatesConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.SiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.RotationsSiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;

import java.util.function.Function;

/**
 * Converts a string to a TwoHandedSiteswap
 */
public class StringToTwoHandedSiteswapConverter implements Function<String, TwoHandedVanillaSiteswap>
{
    private static final boolean DEFAULT_REDUCE = false;
    private static final SortingStrategy DEFAULT_SORTING_STRATEGY = NoSortingStrategy.get();

    private final boolean reduce;
    private final SortingStrategy sortingStrategy;

    public StringToTwoHandedSiteswapConverter()
    {
        reduce = DEFAULT_REDUCE;
        sortingStrategy = DEFAULT_SORTING_STRATEGY;
    }

    public StringToTwoHandedSiteswapConverter(final boolean reduce)
    {
        this.reduce = reduce;
        sortingStrategy = DEFAULT_SORTING_STRATEGY;
    }

    public StringToTwoHandedSiteswapConverter(final SortingStrategy sortingStrategy)
    {
        this.reduce = DEFAULT_REDUCE;
        this.sortingStrategy = sortingStrategy;
    }

    public StringToTwoHandedSiteswapConverter(final boolean reduce,
                                              final SortingStrategy sortingStrategy)
    {
        this.reduce = reduce;
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public TwoHandedVanillaSiteswap apply(final String siteswap)
    {
        VanillaThro[] thros = StringToVanillaThrosConverter.get().apply(siteswap);

        if (reduce)
        {
            thros = Reducer.reduce(thros);
        }

        VanillaState[] states = VanillaThrosToVanillaStatesConverter.get().apply(thros);

        final SiteswapSorter sorter = new RotationsSiteswapSorter(states, sortingStrategy);

        sorter.sort();

        states = StatesToVanillaStatesConverter.get().apply(sorter.getWinningSort());

        thros = sorter.sortToMatch(thros);

        return new TwoHandedVanillaSiteswap(states, thros, sortingStrategy);
    }

    @Override
    public String toString()
    {
        return "StringToTwoHandedSiteswapConverter{" +
                "reduce=" + reduce +
                ", sortingStrategy=" + sortingStrategy +
                '}';
    }
}
