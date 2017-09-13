package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

/**
 Created by caspar on 26/07/17.
 */
public class FourHandedSiteswap extends TwoHandedVanillaSiteswap
{
    private static final String TYPE = "Four Handed Siteswap";
    private static final int NUM_JUGGLERS = 2;
    private static final int NUM_HANDS = 4;

    public FourHandedSiteswap(final VanillaState[] states,
                              final FourHandedSiteswapThro[] thros,
                              final SortingStrategy sortingStrategy) throws InvalidSiteswapException
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
    public FourHandedSiteswapThro[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        if (forJuggler >= 0 && forJuggler < getNumJugglers())
        {
            return VanillaThroUtils.globalToLocal(VanillaThroUtils.castAllToFourHandedSiteswapThro(thros), forJuggler);
        }
        throw new IndexOutOfBoundsException("There are only 2 jugglers. Juggler 0 and Juggler 1");
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    @Override
    public Siteswap resort(final SortingStrategy newSortingStrategy)
    {
        try
        {
            return new FourHandedSiteswap(this.getStates(), VanillaThroUtils.castAllToFourHandedSiteswapThro(thros), newSortingStrategy);
        }
        catch (InvalidSiteswapException e)
        {
            throw new IllegalStateException("Could not create new siteswap", e);
        }
    }
}
