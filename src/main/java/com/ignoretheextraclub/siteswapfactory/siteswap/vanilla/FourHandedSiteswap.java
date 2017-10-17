package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.GlobalToLocalBiConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

/**
 * Created by caspar on 26/07/17.
 */
public class FourHandedSiteswap extends VanillaSiteswap
{
    private static final String TYPE = "Four Handed Siteswap";

    private static final int NUM_JUGGLERS = 2;
    private static final int NUM_HANDS = 4;

    public FourHandedSiteswap(final VanillaState[] states)
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
    public FourHandedSiteswapThro[] getThrows()
    {
        return StatesToThrosConverter.get().andThen(ThrosToFourHandedSiteswapThrosConverter.get()).apply(getStates());
    }

    @Override
    public FourHandedSiteswapThro[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        if (forJuggler >= 0 && forJuggler < getNumJugglers())
        {
            return GlobalToLocalBiConverter.convertToLocal(getThrows(), forJuggler);
        }
        throw new IndexOutOfBoundsException("There are only 2 jugglers. Juggler 0 and Juggler 1");
    }

    @Override
    public String getType()
    {
        return TYPE;
    }



}
