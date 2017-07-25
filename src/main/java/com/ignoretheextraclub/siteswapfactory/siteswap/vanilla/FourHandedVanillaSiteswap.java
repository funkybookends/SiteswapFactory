package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

import java.util.stream.Stream;

/**
 Created by caspar on 26/07/17.
 */
public class FourHandedVanillaSiteswap extends VanillaSiteswap
{
    private static final String TYPE = "Four Handed Siteswap";
    private static final int NUM_JUGGLERS = 2;
    private static final int NUM_HANDS = 4;

    public FourHandedVanillaSiteswap(final VanillaState[] states,
                                     final FourHandedSiteswapThro[] thros,
                                     final SortingStrategy<VanillaState> sortingStrategy) throws InvalidSiteswapException
    {
        super(states, thros, sortingStrategy);
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
    public VanillaThro[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        if (forJuggler > 0 && forJuggler < getNumJugglers())
        {
            return VanillaThroUtils.globalToLocal(getThrows(), forJuggler);
        }
        throw new IndexOutOfBoundsException("There are only 2 jugglers. Juggler 0 and Juggler 1");
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
    public Siteswap<VanillaState, VanillaThro> append(final Siteswap<VanillaState, VanillaThro> other) throws UnsupportedOperationException
    {
        // TODO verify that we get an FHS in
        return super.append(other);
    }

    @Override
    public Stream<Siteswap> getAnagrams() throws UnsupportedOperationException
    {
        return super.getAnagrams();
    }

    @Override
    public Stream<Siteswap> getRelated() throws UnsupportedOperationException
    {
        return super.getRelated();
    }
}
