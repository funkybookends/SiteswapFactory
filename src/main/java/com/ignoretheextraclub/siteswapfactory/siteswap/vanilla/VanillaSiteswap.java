package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateValidationUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.RotationsSiteswapSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 Created by caspar on 26/07/17.
 */
public class VanillaSiteswap implements Siteswap
{
    private static final String UNDEFINED_NUMBER_OF_JUGGLERS_ERROR_MESSAGE = "The number of jugglers is undefined for this type of siteswap.";
    private static final String TYPE = "Vanilla Siteswap";
    protected final VanillaState[] states;
    protected final VanillaThro[] thros;
    protected final SortingStrategy sortingStrategy;

    public VanillaSiteswap(final VanillaState[] states,
                           final VanillaThro[] thros,
                           final SortingStrategy sortingStrategy) throws InvalidSiteswapException
    {
        try
        {
            StateValidationUtils.validateAllStatesConnect(states, thros);
            StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(states);
        }
        catch (final BadThrowException | TransitionException cause)
        {
            throw new InvalidSiteswapException("Invalid Siteswap.", cause);
        }

        final RotationsSiteswapSorter sorter = new RotationsSiteswapSorter(states, sortingStrategy);
        this.states = VanillaStateUtils.castAllToVanillaState(sorter.getWinningSort());
        this.thros = sorter.sortToMatch(thros);
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
        return ThroUtils.getHighestThro(thros);
    }

    @Override
    public SortingStrategy getSortingStrategy()
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
    public boolean equals(final Siteswap other)
    {
        return other == this || Arrays.deepEquals(states, other.getStates());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean same(final Siteswap other)
    {
        if (this.equals(other))
        {
            return true;
        }
        try
        {
            final RotationsSiteswapSorter thisSorter = new RotationsSiteswapSorter(this.states,
                    HighestThrowFirstStrategy.get());
            thisSorter.sort();
            final RotationsSiteswapSorter otherSorter = new RotationsSiteswapSorter(other.getStates(),
                    HighestThrowFirstStrategy.get());
            otherSorter.sort();
            return Arrays.deepEquals(thisSorter.getWinningSort(), otherSorter.getWinningSort());
        }
        catch (final InvalidSiteswapException invalidSiteswapException)
        {
            return false;
        }
    }

    @Override
    public Siteswap append(final Siteswap other) throws UnsupportedOperationException
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

    @Override
    public String toString()
    {
        return VanillaThroUtils.vanillaThrowArrayToString(thros);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        final VanillaSiteswap that = (VanillaSiteswap) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(states, that.states))
        {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(thros, that.thros))
        {
            return false;
        }
        return sortingStrategy.equals(that.sortingStrategy);
    }

    @Override
    public int hashCode()
    {
        int result = Arrays.hashCode(states);
        result = 31 * result + Arrays.hashCode(thros);
        result = 31 * result + sortingStrategy.hashCode();
        return result;
    }

    @Override
    public Siteswap resort(final SortingStrategy newSortingStrategy)
    {
        try
        {
            return new VanillaSiteswap(this.getStates(), this.getThrows(), newSortingStrategy);
        }
        catch (InvalidSiteswapException e)
        {
            throw new IllegalStateException("Could not create new siteswap", e);
        }
    }
}
