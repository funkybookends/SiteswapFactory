package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;

/**
 * Created by caspar on 26/07/17.
 */
public class TwoHandedSiteswap extends VanillaSiteswap
{
    private static final String TYPE = "Two Handed Siteswap";

    private static final int NUM_JUGGLERS = 1;
    private static final int NUM_HANDS = 2;

    public TwoHandedSiteswap(final GeneralCircuit states)
    {
        super(states);
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
    public String getType()
    {
        return TYPE;
    }
}
