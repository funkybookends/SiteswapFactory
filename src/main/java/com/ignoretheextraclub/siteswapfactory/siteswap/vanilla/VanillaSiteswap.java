package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import java.util.Arrays;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.VanillaThrosToStringConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumJugglersException;
import com.ignoretheextraclub.siteswapfactory.predicates.validation.LoopsPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

/**
 * Created by caspar on 26/07/17.
 */
public class VanillaSiteswap implements Siteswap
{
    private static final String TYPE = "Vanilla Siteswap";

    protected final VanillaState[] states;

    public VanillaSiteswap(final VanillaState[] states)
    {
        if (!LoopsPredicate.loops(states))
        {
            throw new InvalidSiteswapException("States " + Arrays.toString(states) + " is not a valid state loop.");
        }
        this.states = states;
    }

    @Override
    public int getNumJugglers()
    {
        throw new NumJugglersException("The number of jugglers for a " + TYPE + " is undefined.");
    }

    @Override
    public int getNumHands()
    {
        throw new NumJugglersException("The number of jugglers for a " + TYPE + " is undefined.");
    }

    @Override
    public VanillaThro[] getThrows()
    {
        return StatesToThrosConverter.get().andThen(ThrosToVanillaThrosConverter.get()).apply(getStates());
    }

    @Override
    public Thro[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        if (forJuggler >= 0 && forJuggler < getNumJugglers())
        {
            return getThrows();
        }

        if (getNumJugglers() == 1)
        {
            throw new IndexOutOfBoundsException("There is only 1 juggler. Juggler 0");
        }
        else
        {
            throw new IndexOutOfBoundsException("There are only " + getNumJugglers() + " jugglers, 0 indexed");
        }
    }

    @Override
    public VanillaState[] getStates()
    {
        return states;
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
    public boolean equals(final Siteswap other)
    {
        return other == this || Arrays.deepEquals(getStates(), other.getStates());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean same(final Siteswap other)
    {
        if (this.equals(other))
        {
            return true;
        }

        final State[] myStates = getStates();
        final State[] otherStates = other.getStates();

        if (otherStates.length != myStates.length)
        {
            return false;
        }

        for (int i = 0; i < myStates.length; i++)
        {
            if (same(myStates, new ArrayLoopingIterator<>(otherStates, i)))
            {
                return true;
            }
        }
        return false;
    }

    private boolean same(final State[] myStates, final ArrayLoopingIterator<State> stateArrayLoopingIterator)
    {
        for (final State myState : myStates)
        {
            if (!myState.equals(stateArrayLoopingIterator.next()))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return VanillaThrosToStringConverter.get().apply(getThrows());
    }
}
