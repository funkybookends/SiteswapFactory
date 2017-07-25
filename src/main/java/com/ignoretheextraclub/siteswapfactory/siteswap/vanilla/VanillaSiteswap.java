package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateValidationUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
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
public class VanillaSiteswap implements Siteswap<VanillaState, VanillaThro>
{
    private static final String UNDEFINED_NUMBER_OF_JUGGLERS_ERROR_MESSAGE = "The number of jugglers is undefined for this type of siteswap.";
    private static final String TYPE = "Vanilla Siteswap";
    private final VanillaState[] states;
    private final VanillaThro[] thros;
    private final SortingStrategy<VanillaState> sortingStrategy;

    public VanillaSiteswap(final VanillaState[] states,
                           final VanillaThro[] thros,
                           final SortingStrategy<VanillaState> sortingStrategy) throws InvalidSiteswapException
    {
        final VanillaState[] reducedStates = SortingUtils.reduce(states);
        final VanillaThro[] reducedThros = SortingUtils.reduce(thros);
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
        throw new UnsupportedOperationException(UNDEFINED_NUMBER_OF_JUGGLERS_ERROR_MESSAGE);
    }

    @Override
    public int getNumHands()
    {
        throw new UnsupportedOperationException(UNDEFINED_NUMBER_OF_JUGGLERS_ERROR_MESSAGE);
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
    public VanillaThro[] getThrows()
    {
        return thros;
    }

    @Override
    public VanillaThro[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        return thros;
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
    public VanillaThro getHighestThro()
    {
        return VanillaThroUtils.getHighestThro(thros);
    }

    @Override
    public SortingStrategy<VanillaState> getSorter()
    {
        return sortingStrategy;
    }

    @Override
    public int getStartingNumberOfObjects(final int forHand) throws IndexOutOfBoundsException
    {
        throw new UnsupportedOperationException(UNDEFINED_NUMBER_OF_JUGGLERS_ERROR_MESSAGE);
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    @Override
    public boolean equals(final Siteswap<VanillaState, VanillaThro> other)
    {
        return other == this || Arrays.deepEquals(states, other.getStates());
    }

    @Override
    public boolean same(final Siteswap<VanillaState, VanillaThro> other)
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
    public Siteswap<VanillaState, VanillaThro> append(final Siteswap<VanillaState, VanillaThro> other) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
    }

    @Override
    public Stream<Siteswap> getAnagrams() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
    }

    @Override
    public Stream<Siteswap> getRelated() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
    }
}
