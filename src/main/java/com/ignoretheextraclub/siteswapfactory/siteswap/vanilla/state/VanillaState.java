package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NoTransitionException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.StateSizeException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils.drop;

/**
 Created by caspar on 26/11/16.
 */
@Immutable
public class VanillaState implements State
{
    protected static final String EMPTY = "_";
    protected static final String FILLED = "X";

    protected static final int MIN_SIZE = 1;
    protected static final int MAX_SIZE = 15;

    protected static final int MIN_OBJECTS = 1;
    protected static final int MAX_OBJECTS = 12;

    @JsonIgnore private final boolean[] occupied;

    /**
     Actually constructs a new object

     @param occupied
     */
    protected VanillaState(final boolean[] occupied) throws StateSizeException, NumObjectsException
    {
        validateSize(occupied.length);
        validateNumObjects(StateUtils.getNumObjects(occupied));
        this.occupied = occupied;
    }

    @Override
    public Thro getMaxThrow()
    {
        return VanillaThro.getOrNull(occupied.length);
    }

    /**
     Lower is better

     @return
     */
    @Override
    @JsonIgnore
    public int excitedness()
    {
        int result = 0;
        int position_value = 1;
        final int maxThro = ((VanillaThro) getMaxThrow()).getNumBeats();

        for (int position = 0; position < maxThro; position++)
        {
            if (occupied[position])
            {
                result += position_value;
            }
            position_value *= 2;
        }
        return result;
    }

    public boolean equals(final boolean[] other)
    {
        return Arrays.equals(this.occupied, other);
    }

    @Override
    public Set<State> getNextStates()
    {
        try
        {
            Set<State> states = new HashSet<>();
            for (Thro thro : getAvailableThrows())
            {
                State state = this.thro(thro);
                states.add(state);
            }
            return states;
        }
        catch (final BadThrowException e)
        {
            throw new RuntimeException("Vanilla State could not throw an available throw", e);
        }
    }

    @Override
    public Set<Thro> getAvailableThrows()
    {
        final Set<Thro> availableThros = new TreeSet<>();
        if (canThrow())
        {
            final int maxThro = ((VanillaThro) getMaxThrow()).getNumBeats();

            for (int pos = 0; pos < maxThro; pos++)
            {
                if (!occupied[pos])
                {
                    try
                    {
                        availableThros.add(VanillaThro.get(pos));
                    }
                    catch (final BadThrowException ignored)
                    {
                    }
                }
            }
                availableThros.add(getMaxThrow());
        }
        else
        {
            try
            {
                availableThros.add(VanillaThro.get(0));
            }
            catch (final BadThrowException ignored)
            {
            }
        }
        return availableThros;
    }

    protected boolean canThrow()
    {
        return occupied[0];
    }

    @Override
    public boolean canTransition(State toNextState)
    {
        try
        {
            this.getThrow(toNextState);
            return true;
        }
        catch (final Exception any)
        {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public State thro(Thro thro) throws BadThrowException
    {
        final int numBeats = ((VanillaThro) thro).getNumBeats();
        if (!canThrow())
        {
            if (numBeats == 0)
            {
                try
                {
                    return new VanillaState(drop(occupied, false)); // throw 0
                }
                catch (StateSizeException | NumObjectsException e)
                {
                    throw new RuntimeException("Something went wrong! you tried to throw 0 when you had to, but I erred",
                                               e);
                }
            }
            throw new BadThrowException("Cannot throw [" + thro + "], must throw 0");
        }
        try
        {
            if (thro.equals(getMaxThrow()))
            {
                return new VanillaState(drop(occupied, true)); // throw max_throw
            }
            else if (occupied[numBeats])
            {
                throw new BadThrowException("Cannot throw [" + thro + "], already occupied.");
            }
            else
            {
                boolean[] nextState = ArrayUtils.copy(occupied);
                nextState[numBeats] = true;
                return new VanillaState(drop(nextState, false)); // throw non max throw throw
            }
        }
        catch (StateSizeException | NumObjectsException e)
        {
            throw new RuntimeException("Something went wrong! the throw should've been fine.", e);
        }
    }

    @Override
    public int getNumObjects()
    {
        return StateUtils.getNumObjects(this.occupied);
    }

    @Override
    public boolean isGroundState()
    {
        for (int position = 0; position < getNumObjects(); position++)
        {
            if (!occupied[position])
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Thro getThrow(State toNextState) throws NoTransitionException
    {
        if (toNextState == null)
        {
            throw new IllegalArgumentException("State must be non null");
        }
        for (Thro thro : this.getAvailableThrows())
        {
            try
            {
                if (this.thro(thro).equals(toNextState))
                {
                    return thro;
                }
            }
            catch (final BadThrowException badThrowException)
            {
                throw new RuntimeException("I threw a throw I thought was legal: [" + thro + "] into [" + this.toString() + "]", badThrowException);
            }
        }
        throw new NoTransitionException("Cannot transition between these two vanillaStates, from [" + this.toString() + "] to [" + toNextState
                .toString() + "]");
    }

    @Override
    public int hashCode()
    {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (this == o)
        {
            return true;
        }
        if (getClass() != o.getClass())
        {
            return false;
        }
        VanillaState state = (VanillaState) o;
        return Arrays.equals(occupied, state.occupied);
    }

    @JsonProperty("occupancy")
    public String toString()
    {
        return VanillaStateUtils.toString(this.occupied, FILLED, EMPTY);
    }

    protected static void validateNumObjects(final int numObjects) throws NumObjectsException
    {
        if (numObjects < MIN_OBJECTS || numObjects > MAX_OBJECTS)
        {
            throw new NumObjectsException("State has [" + numObjects + "] objects, must be between [" + MIN_OBJECTS + "] and [" + MAX_OBJECTS + "]");
        }
    }

    protected static void validateSize(final int size) throws StateSizeException
    {
        if (size < MIN_SIZE || size > MAX_SIZE)
        {
            throw new StateSizeException("State has [" + size + "] positions, must be between [" + MIN_SIZE + "] and [" + MAX_SIZE + "]");
        }
    }

    @Override
    public int compareTo(final Object o)
    {
        throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
    }
}
