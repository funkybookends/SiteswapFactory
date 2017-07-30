package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;

/**
 Created by caspar on 26/07/17.
 */
public class TwoHandedVanillaSiteswap extends VanillaSiteswap
{
    private static final String TYPE = "Two Handed Siteswap";
    private static final int NUM_JUGGLERS = 1;
    private static final int NUM_HANDS = 2;

    public TwoHandedVanillaSiteswap(final VanillaState[] states,
                                    final VanillaThro[] thros,
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
        if (forJuggler < 0 || forJuggler > getNumJugglers())
        {
            return super.getThrowsForJuggler(forJuggler);
        }
        throw new IndexOutOfBoundsException("There is only 1 juggler. Juggler 0");
    }

    @Override
    public int getStartingNumberOfObjects(final int forHand) throws IndexOutOfBoundsException
    {
        if (forHand >= 0 && forHand < getNumHands())
        {
            return VanillaSiteswapUtils.getStartingNumberOfObjects(getNumHands(),
                                                                   forHand,
                                                                   getThrows(),
                                                                   getNumObjects());
        }
        throw new IndexOutOfBoundsException("There are only " + getNumHands() + " hands. Cannot get for hand: " + forHand);
    }

    @Override
    public String getType()
    {
        return TYPE;
    }
}
