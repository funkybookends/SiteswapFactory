package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.thros.VanillaThrow;

/**
 Created by caspar on 07/01/17.
 */
@JsonPropertyOrder({
"global_string_siteswap",
"global_int_siteswap",
"num_objects",
"period",
"prime",
"grounded",
"sorting_strategy",
"states",
"global_throws",
"states",
"highest_throw",
"first_hand_objects",
"second_hand_objects"
})
public class TwoHandedSiteswap extends VanillaStateSiteswap<VanillaThrow, VanillaState<VanillaThrow>>
{
    private static final int NUMBER_OF_HANDS = 2;

    private static final StateSorter<VanillaThrow, VanillaState<VanillaThrow>> DEFAULT_SORTER = HighestThrowFirstStrategy
            .get();
    private static final String TYPE = "Two Handed Siteswap";

    public TwoHandedSiteswap(VanillaState<VanillaThrow> startingState,
                             VanillaThrow[] thros,
                             StateSorter<VanillaThrow, VanillaState<VanillaThrow>> sorter) throws InvalidSiteswapException
    {
        super(startingState, thros, sorter);
    }

    public TwoHandedSiteswap(VanillaState<VanillaThrow> startingState,
                             VanillaThrow[] thros) throws InvalidSiteswapException
    {
        super(startingState, thros, DEFAULT_SORTER);
    }

    public TwoHandedSiteswap(VanillaState<VanillaThrow>[] states,
                             StateSorter<VanillaThrow, VanillaState<VanillaThrow>> sorter) throws InvalidSiteswapException
    {
        super(states, sorter);
    }

    public TwoHandedSiteswap(VanillaState<VanillaThrow>[] states) throws InvalidSiteswapException
    {
        super(states, DEFAULT_SORTER);
    }

    public static TwoHandedSiteswap create(final String siteswap,
                                           final StateSorter<VanillaThrow, VanillaState<VanillaThrow>> sorter) throws InvalidSiteswapException
    {
        return create(VanillaThrow.stringToIntArray(siteswap), sorter);
    }

    public static TwoHandedSiteswap create(final int[] siteswap,
                                           final StateSorter<VanillaThrow, VanillaState<VanillaThrow>> sorter) throws InvalidSiteswapException
    {
        try
        {
            final VanillaThrow[] vanillaThrows = VanillaThrow.intArrayToVanillaThrowArray(siteswap);
            final VanillaState<VanillaThrow> firstState = VanillaState.getFirstState(VanillaThrow.intArrayToVanillaThrowArray(
                    siteswap), VanillaThrow::get);
            return new TwoHandedSiteswap(firstState, vanillaThrows, sorter);
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Could not construct siteswap", cause);
        }
    }

    public static TwoHandedSiteswap create(final String siteswap) throws InvalidSiteswapException
    {
        return create(VanillaThrow.stringToIntArray(siteswap));
    }

    public static TwoHandedSiteswap create(final int[] siteswap) throws InvalidSiteswapException
    {
        try
        {
            final VanillaThrow[] vanillaThrows = VanillaThrow.intArrayToVanillaThrowArray(siteswap);
            final VanillaState<VanillaThrow> firstState = VanillaState.getFirstState(VanillaThrow.intArrayToVanillaThrowArray(
                    siteswap), VanillaThrow::get);
            return new TwoHandedSiteswap(firstState, vanillaThrows);
        }
        catch (final Exception cause)
        {
            throw new InvalidSiteswapException("Could not construct siteswap [" + VanillaThrow.intArrayToString(siteswap) + "]",
                                               cause);
        }
    }

    @JsonProperty("first_hand_objects")
    public int getFirstStartingHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.FIRST.globalStartingHand);
    }

    @JsonProperty("second_hand_objects")
    public int getSecondStartingHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.SECOND.globalStartingHand);
    }

    @Override
    public int getNumJugglers()
    {
        return NUMBER_OF_HANDS / 2;
    }

    @Override
    public int getNumHands()
    {
        return NUMBER_OF_HANDS;
    }

    @Override
    public VanillaThrow[] getThrowsForJuggler(final int forJuggler) throws IndexOutOfBoundsException
    {
        if (forJuggler == 0)
        {
            return thros;
        }
        throw new IndexOutOfBoundsException("Only 1 juggler not [" + forJuggler + ".");
    }

    @Override
    public int getStartingNumberOfObjects(final int forHand) throws IndexOutOfBoundsException
    {
        switch (forHand)
        {
            case 0: return getFirstStartingHandObjects();
            case 1: return getSecondStartingHandObjects();
        }
        throw new IndexOutOfBoundsException("Only 2 hands, not [" + forHand + "]");
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    @Override
    public boolean equals(final Siteswap other)
    {
        return other != null && this.toString().equals(other.toString());
    }

    @Override
    public boolean same(final Siteswap other)
    {
        if (this.equals(other))
        {
            return true;
        }
        if (other == null)
        {
            return false;
        }
        try
        {
            if (TwoHandedSiteswap.create(other.toString(), this.getSorter()).equals(this))
            {
                return true;
            }
        }
        catch (final InvalidSiteswapException ignored)
        {
        }
        return false;
    }

    public enum Hand
    {
        FIRST(0),
        SECOND(1);

        public final int globalStartingHand;

        Hand(int globalStartinHand)
        {
            this.globalStartingHand = globalStartinHand;
        }
    }
}
