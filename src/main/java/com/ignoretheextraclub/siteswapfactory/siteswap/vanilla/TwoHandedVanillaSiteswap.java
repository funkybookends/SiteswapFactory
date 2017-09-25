package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

/**
 * Created by caspar on 26/07/17.
 */
public class TwoHandedVanillaSiteswap extends VanillaSiteswap
{
    private static final String TYPE = "Two Handed Siteswap";
    private static final int NUM_JUGGLERS = 1;
    private static final int NUM_HANDS = 2;

    public TwoHandedVanillaSiteswap(final VanillaState[] states,
                                    final VanillaThro[] thros,
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
    public VanillaThro[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        if (forJuggler >= 0 && forJuggler < getNumJugglers())
        {
            return thros;
        }
        throw new IndexOutOfBoundsException("There is only 1 juggler. Juggler 0");
    }

    @Override
    public int getStartingNumberOfObjects(final int hand) throws IndexOutOfBoundsException
    {
        if (hand < 0 || hand >= getNumHands())
        {
            throw new IndexOutOfBoundsException("There are only " + getNumHands() + " hands. Cannot get for hand: " + hand);
        }

        final boolean[] landings = new boolean[getPeriod() + getHighestThro().getNumBeats()];

        final ArrayLoopingIterator<VanillaThro> looper = new ArrayLoopingIterator<>(getThrows());

        for (int i = 0; i < landings.length; i++)
        {
            final int landing_position = i + looper.next().getNumBeats();

            if (landing_position < landings.length)
            {
                landings[landing_position] = true;
            }
        }

        int tot = 0;
        int i = 0;
        final int[] hands = new int[getNumHands()];

        while (tot < getNumObjects())
        {
            if (!landings[i])
            {
                hands[i % getNumHands()]++;
                tot++;
            }
            i++;
        }

        return hands[hand];
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
            return new TwoHandedVanillaSiteswap(this.getStates(), this.getThrows(), newSortingStrategy);
        }
        catch (final InvalidSiteswapException cause)
        {
            throw new IllegalStateException("Could not create new siteswap", cause);
        }
    }
}
