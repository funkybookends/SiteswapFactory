package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.thros.VanillaThrow;
import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
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
})
public abstract class VanillaStateSiteswap<Thro extends VanillaThrow, State extends VanillaState<Thro>> extends
                                                                                                       AbstractSiteswap<Thro, State>
{

    public VanillaStateSiteswap(State startingState,
                                Thro[] thros,
                                StateSorter<Thro, State> sorter) throws InvalidSiteswapException
    {
        super(startingState, thros, sorter);
    }

    public VanillaStateSiteswap(State[] states,
                                StateSorter<Thro, State> sorter) throws InvalidSiteswapException
    {
        super(states, sorter);
    }


    @JsonProperty("global_string_siteswap")
    public String getStringSiteswap()
    {
        return Thro.vanillaThrowArrayToString(thros);
    }


    @JsonProperty("global_int_siteswap")
    public int[] getGlobalIntSiteswap()
    {
        return Thro.vanillaThrowArrayToIntArray(thros);
    }

    @Override
    public String toString()
    {
        return getStringSiteswap();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VanillaStateSiteswap that = (VanillaStateSiteswap) o;

        return getStringSiteswap().equals(that.getStringSiteswap());
    }

    @JsonIgnore
    public int getStartingNumberOfObjects(final int numberOfHands, final int hand)
    {
        final boolean[] landings = new boolean[period + highestThrow.getThro()];
        for (int i = 0; i < landings.length; i++)
        {
            final int landing_position = i + thros[i % period].getThro();
            if (landing_position < landings.length)
            {
                landings[landing_position] = true;
            }
        }
        int tot = 0;
        int i = 0;
        final int[] hands = new int[numberOfHands];
        while (tot < numObjects)
        {
            if (!landings[i])
            {
                hands[i % numberOfHands]++;
                tot++;
            }
            i++;
        }
        return hands[hand];
    }

    @Override
    public int hashCode()
    {
        return getStringSiteswap().hashCode();
    }
}
