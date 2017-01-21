package com.ignoretheextraclub.siteswapfactory.siteswap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NoTransitionException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.sorters.SortingUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by caspar on 10/12/16.
 */
@JsonPropertyOrder({
        "num_objects",
        "period",
        "prime",
        "grounded",
        "sorting_strategy",
        "states",
        "global_throws",
        "states",
        "highest_throw"
})
public abstract class AbstractSiteswap<Thro extends AbstractThro, State extends AbstractState<Thro>>
{
    public static final int MAX_PERIOD = 15;
    public static final int MIN_PERIOD = 1;

    // Properties that define the state graph
    @JsonProperty("num_objects") protected final int numObjects;

    // Route through the state graph
    protected final State[] states;
    @JsonProperty("global_throws") protected final Thro[] thros;

    // Properties of the route
    protected final int period;
    protected final boolean prime;
    @JsonProperty("highest_throw") protected final Thro highestThrow;
    protected final boolean grounded;
    @JsonIgnore protected final StateSorter<Thro, State> sorter;

    /**
     * @param states
     * @param thros
     * @param sorter
     *
     * @throws InvalidSiteswapException
     */
    protected AbstractSiteswap(State[] states,
                               Thro[] thros,
                               final StateSorter<Thro, State> sorter) throws InvalidSiteswapException
    {
        try
        {
            if (states.length != thros.length)
            {
                throw new InvalidSiteswapException("States and Throws are unequal in length");
            }

            states = SortingUtils.reduce(states);
            thros = SortingUtils.reduce(thros);

            this.period = validatePeriod(states.length);

            SortingUtils.Rotations<Thro, State> rotations = new SortingUtils.Rotations<>(states);
            this.states = rotations.sort(sorter);
            this.thros = rotations.sortToMatch(thros);

            for (int i = 0; i < states.length; i++)
            {
                if (this.states[i].getNumObjects() != this.states[0].getNumObjects())
                {
                    throw new InvalidSiteswapException("All states in sequence must have the same number of objects");
                }
                if (!this.states[i].thro(thros[i]).equals(this.states[(i + 1) % period]))
                {
                    throw new InvalidSiteswapException("States and throws do not match"); // TODO improve description
                }
            }

            this.sorter = sorter;
            this.numObjects = this.states[0].getNumObjects();
            this.prime = !containsARepeatedState(this.states);
            this.highestThrow = getHighestThro(this.thros);
            this.grounded = containsGround(this.states);
        }
        catch (final PeriodException | BadThrowException cause)
        {
            throw new InvalidSiteswapException("Cannot construct siteswap", cause);
        }
    }

    /**
     * @param startingState
     * @param thros
     * @param sorter
     *
     * @throws InvalidSiteswapException
     */
    protected AbstractSiteswap(final State startingState,
                               final Thro[] thros,
                               final StateSorter<Thro, State> sorter) throws InvalidSiteswapException
    {
        this(getAllStates(startingState, thros), thros, sorter);
    }

    protected AbstractSiteswap(final State[] states,
                               final StateSorter<Thro, State> sorter) throws InvalidSiteswapException
    {
        this(states, getAllThrows(states), sorter);
    }



    /*
                Static Utility Methods
     */

    @SuppressWarnings("unchecked")
    private static <Thro extends AbstractThro, State extends AbstractState<Thro>> Thro[] getAllThrows(final State[] states) throws InvalidSiteswapException
    {
        try
        {
            final Thro first = states[0].getThrow(states[1 % states.length]);
            final Thro[] thros = (Thro[]) Array.newInstance(first.getClass(), states.length);
            thros[0] = first;
            for (int i = 1; i < states.length; i++)
            {
                thros[i] = states[i].getThrow(states[(i+1) % states.length]);
            }
            return thros;
        }
        catch (final NoTransitionException cause)
        {
            throw new InvalidSiteswapException("Cannot construct all throws", cause);
        }
    }

    @SuppressWarnings("unchecked")
    private static <Thro extends AbstractThro, State extends AbstractState<Thro>> State[] getAllStates(State startingState, Thro[] thros) throws InvalidSiteswapException
    {
        try
        {
            final State[] states = (State[]) Array.newInstance(startingState.getClass(), thros.length);
            states[0] = startingState;
            for (int i = 0; i < thros.length - 1; i++)
            {
                states[i + 1] = states[i].thro(thros[i]);
            }
            return states;
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Cannot construct all states", cause);
        }
    }

    public static int validatePeriod(final int period) throws PeriodException
    {
        if (period > MAX_PERIOD)      throw new PeriodException("Period too long, cannot be longer than " + MAX_PERIOD);
        else if (period < MIN_PERIOD) throw new PeriodException("Period too short cannot be shorter than " + MIN_PERIOD);
        return period;
    }

    public static <State extends AbstractState> boolean containsARepeatedState(State[] states)
    {
        for (int i = 0; i < states.length; i++)
            for (int j = i+1; j < states.length; j++)
                if (states[i].equals(states[j]))
                    return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <Thro extends AbstractThro> Thro getHighestThro(Thro[] thros)
    {
        Thro highest = thros[0];
        for (int i = 1; i < thros.length; i++)
            if (highest.compareTo(thros[i]) < 0)
                highest = thros[i];
        return highest;
    }

    public static <State extends AbstractState> boolean containsGround(State[] states)
    {
        for (State state : states)
            if (state.isGroundState())
                return true;
        return false;
    }

    /*
                Getters
     */

    public final int getPeriod()
    {
        return period;
    }

    public final int getNumObjects()
    {
        return numObjects;
    }

    @JsonIgnore
    public final Thro[] getThrows()
    {
        return this.thros;
    };

    public final State[] getStates()
    {
        return this.states;
    };

    public final Thro getHighestThrow()
    {
        return highestThrow;
    }

    public final boolean isPrime()
    {
        return prime;
    }

    public final boolean isGrounded()
    {
        return grounded;
    }

    @JsonIgnore
    public StateSorter<Thro, State> getSorter()
    {
        return sorter;
    }

    @JsonProperty("sorting_strategy")
    public String getSortingStrategyName()
    {
        return sorter.getName();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSiteswap<?, ?> that = (AbstractSiteswap<?, ?>) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(states, that.states);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(states);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(thros.length);
        for (Thro thro : thros)
        {
            sb.append(thro.toString());
        }
        return sb.toString();
    }
}
