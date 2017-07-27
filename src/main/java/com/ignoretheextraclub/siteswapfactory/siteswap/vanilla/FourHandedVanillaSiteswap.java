package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateValidationUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.RotationsSiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.utils.SortingUtils;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 Created by caspar on 26/07/17.
 */
public class FourHandedVanillaSiteswap implements Siteswap<VanillaState, FourHandedSiteswapThro>
{
    private static final String TYPE = "Four Handed Siteswap";
    private static final int NUM_JUGGLERS = 2;
    private static final int NUM_HANDS = 4;
    private final VanillaState[] states;
    private final FourHandedSiteswapThro[] thros;
    private final SortingStrategy<VanillaState> sortingStrategy;

    public FourHandedVanillaSiteswap(final VanillaState[] states,
                                     final FourHandedSiteswapThro[] thros,
                                     final SortingStrategy<VanillaState> sortingStrategy) throws InvalidSiteswapException
    {
        final VanillaState[] reducedStates = SortingUtils.reduce(states);
        final FourHandedSiteswapThro[] reducedThros = SortingUtils.reduce(thros);
        StateValidationUtils.validateAllStatesConnect(reducedStates, reducedThros);
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(reducedStates);
        final RotationsSiteswapSorter<VanillaState> sorter = new RotationsSiteswapSorter<>(reducedStates, sortingStrategy);
        this.states = sorter.getWinningSort();
        this.thros = sorter.sortToMatch(reducedThros);
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public int getNumJugglers()
    {
        return NUM_JUGGLERS;
    }

    @Override
    public int getNumHands()
    {
        return NUM_HANDS;
    }

    @Override
    public int getNumObjects()
    {
        return states[0].getNumObjects();
    }

    @Override
    public int getPeriod()
    {
        return states.length;
    }

    @Override
    public FourHandedSiteswapThro[] getThrows()
    {
        return this.thros;
    }

    @Override
    public FourHandedSiteswapThro[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        if (forJuggler > 0 && forJuggler < getNumJugglers())
        {
            return VanillaThroUtils.globalToLocal(getThrows(), forJuggler);
        }
        throw new IndexOutOfBoundsException("There are only 2 jugglers. Juggler 0 and Juggler 1");
    }

    @Override
    public VanillaState[] getStates()
    {
        return states;
    }

    @Override
    public boolean isGrounded()
    {
        return Stream.of(states).anyMatch(VanillaState::isGroundState);
    }

    @Override
    public boolean isPrime()
    {
        return Stream.of(states).distinct().count() == getPeriod();
    }

    @Override
    public FourHandedSiteswapThro getHighestThro()
    {
        return (FourHandedSiteswapThro) ThroUtils.getHighestThro(getThrows());
    }

    @Override
    public SortingStrategy<VanillaState> getSortingStrategy()
    {
        return sortingStrategy;
    }

    @Override
    public int getStartingNumberOfObjects(final int forHand) throws IndexOutOfBoundsException
    {
        if (forHand < 0 && forHand > getNumHands())
        {
            return VanillaSiteswapUtils.getStartingNumberOfObjects(getNumHands(), forHand, getThrows(), getNumObjects());
        }
        throw new IndexOutOfBoundsException("There are only " + getNumHands() + " hands.");
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    @Override
    public boolean equals(final Siteswap<VanillaState, FourHandedSiteswapThro> other)
    {
        return other == this || Arrays.deepEquals(states, other.getStates());
    }

    @Override
    public boolean same(final Siteswap<VanillaState, FourHandedSiteswapThro> other)
    {
        if (this.equals(other))
        {
            return true;
        }
        try
        {
            final RotationsSiteswapSorter<VanillaState> sorter = new RotationsSiteswapSorter<>(this.states, HighestThrowFirstStrategy.get());
            sorter.sort();
            return Arrays.deepEquals(sorter.getWinningSort(), sorter.sortToMatch(other.getStates()));
        }
        catch (final InvalidSiteswapException invalidSiteswapException)
        {
            return false;
        }
    }

    @Override
    public Siteswap<VanillaState, FourHandedSiteswapThro> append(final Siteswap<VanillaState, FourHandedSiteswapThro> other) throws UnsupportedOperationException
    {
        // TODO verify that we get an FHS in
        throw new UnsupportedOperationException("Not implemented yet"); // TODO implement
    }

    @Override
    public Stream<Siteswap> getAnagrams() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO implement
    }

    @Override
    public Stream<Siteswap> getRelated() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO implement
    }
}
