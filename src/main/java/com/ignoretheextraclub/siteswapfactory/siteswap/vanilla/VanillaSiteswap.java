package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import java.util.Arrays;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.VanillaThrosToStringConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumJugglersException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
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

    protected final GeneralCircuit states;

    public VanillaSiteswap(final GeneralCircuit generalCircuit)
    {
        if (!(generalCircuit.getStartingState() instanceof VanillaState))
        {
            throw new IllegalArgumentException("GeneralCircuit must be a circuit of " + VanillaState.class.getCanonicalName() + "s");
        }

        for (final Thro thro : generalCircuit.getThros())
        {
            if (!(thro instanceof VanillaThro))
            {
                throw new IllegalArgumentException("GeneralCircuit must be a ciruit of " + VanillaThro.class.getCanonicalName() + "s");
            }
        }

        this.states = generalCircuit;
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
    public GeneralCircuit getGeneralCircuit()
    {
        return states;
    }

    @Override
    public int getStartingNumberOfObjects(final int hand) throws IndexOutOfBoundsException
    {
        final int numHands = getNumHands();

        if (hand < 0 || hand >= numHands)
        {
            throw new IndexOutOfBoundsException("There are only " + numHands + " hands. Cannot get for hand: " + hand);
        }

        final boolean[] landings = new boolean[getPeriod() + getHighestThro().getNumBeats()];

        final ArrayLoopingIterator<VanillaThro> looper = new ArrayLoopingIterator<>(ThrosToVanillaThrosConverter.convert(getThrows()));

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
        final int[] hands = new int[numHands];
        final int numObjects = getNumObjects();

        while (tot < numObjects)
        {
            if (!landings[i])
            {
                hands[i % numHands]++;
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
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final VanillaSiteswap other = (VanillaSiteswap) o;

        return Arrays.deepEquals(getStates(), other.getStates());
    }

    @Override
    public int hashCode()
    {
        return states.hashCode();
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
        return VanillaThrosToStringConverter.get().apply(ThrosToVanillaThrosConverter.convert(getThrows()));
    }
}
