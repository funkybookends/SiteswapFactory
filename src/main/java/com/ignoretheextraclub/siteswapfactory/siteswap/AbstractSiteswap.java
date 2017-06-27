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
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 Created by caspar on 10/12/16.
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
public abstract class AbstractSiteswap<Thro extends AbstractThro, State extends AbstractState<Thro>> implements Siteswap<Thro, State>
{
    public static final int MAX_PERIOD = 27;
    public static final int MIN_PERIOD = 1;

    protected final
    @JsonProperty("num_objects") int numObjects; // Properties that define the state graph
    protected final State[] states; // Route through the state graph
    protected final
    @JsonProperty("global_throws") Thro[] thros;
    protected final int period; // Properties of the route
    protected final boolean prime;
    protected final
    @JsonProperty("highest_throw") Thro highestThrow;
    protected final boolean grounded;
    protected final
    @JsonIgnore StateSorter<Thro, State> sorter;

    /**
     @param startingState
     @param thros
     @param sorter
     */
    protected AbstractSiteswap(final State startingState,
                               final Thro[] thros,
                               final StateSorter<Thro, State> sorter) throws InvalidSiteswapException
    {
        this(getAllStates(startingState, thros), thros, sorter);
    }

    /**
     @param states
     @param thros
     @param sorter
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

            validateAllStatesHaveTheSameNumberOfObjects(states);
            validateAllStatesConnect(states, thros);

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

    public static int validatePeriod(final int period) throws PeriodException
    {
        if (period > MAX_PERIOD)
        {
            throw new PeriodException("Period too long, cannot be longer than " + MAX_PERIOD);
        }
        else if (period < MIN_PERIOD)
        {
            throw new PeriodException("Period too short cannot be shorter than " + MIN_PERIOD);
        }
        return period;
    }

    protected static <State extends AbstractState> void validateAllStatesHaveTheSameNumberOfObjects(final State[] states) throws InvalidSiteswapException
    {
        for (final State state : states)
        {
            if (state.getNumObjects() != states[0].getNumObjects())
            {
                throw new InvalidSiteswapException("All states in sequence must have the same number of objects");
            }
        }
    }

    protected static <Throw extends AbstractThro, State extends AbstractState<Throw>> void validateAllStatesConnect(
            final State[] states,
            final Throw[] thros) throws InvalidSiteswapException, BadThrowException
    {
        for (int i = 0; i < states.length; i++)
        {
            if (!states[i].thro(thros[i]).equals(states[(i + 1) % states.length]))
            {
                throw new InvalidSiteswapException("States do not all connect via throws."); // TODO improve description
            }
        }
    }

    /*
                Static Utility Methods
     */

    public static <State extends AbstractState> boolean containsARepeatedState(State[] states)
    {
        for (int i = 0; i < states.length; i++)
        {
            for (int j = i + 1; j < states.length; j++)
            {
                if (states[i].equals(states[j]))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <Thro extends AbstractThro> Thro getHighestThro(Thro[] thros)
    {
        Thro highest = thros[0];
        for (int i = 1; i < thros.length; i++)
        {
            if (highest.compareTo(thros[i]) < 0)
            {
                highest = thros[i];
            }
        }
        return highest;
    }

    public static <State extends AbstractState> boolean containsGround(State[] states)
    {
        for (State state : states)
        {
            if (state.isGroundState())
            {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static <Thro extends AbstractThro, State extends AbstractState<Thro>> State[] getAllStates(State startingState,
                                                                                                       Thro[] thros) throws InvalidSiteswapException
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

    protected AbstractSiteswap(final State[] states,
                               final StateSorter<Thro, State> sorter) throws InvalidSiteswapException
    {
        this(states, getAllThrows(states), sorter);
    }

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
                thros[i] = states[i].getThrow(states[(i + 1) % states.length]);
            }
            return thros;
        }
        catch (final NoTransitionException cause)
        {
            throw new InvalidSiteswapException("Cannot construct all throws", cause);
        }
    }

    /*
                Getters
     */

    @Override
    public final int getNumObjects()
    {
        return numObjects;
    }

    @Override
    public final int getPeriod()
    {
        return period;
    }

    @Override
    @JsonIgnore
    public final Thro[] getThrows()
    {
        return this.thros;
    }

    @Override
    public final State[] getStates()
    {
        return this.states;
    }

    @Override
    public final boolean isGrounded()
    {
        return grounded;
    }

    @Override
    public final boolean isPrime()
    {
        return prime;
    }

    @Override
    public final Thro getHighestThro()
    {
        return highestThrow;
    }

    @Override
    @JsonIgnore
    public StateSorter<Thro, State> getSorter()
    {
        return sorter;
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(states);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        AbstractSiteswap<?, ?> that = (AbstractSiteswap<?, ?>) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(states, that.states);
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
