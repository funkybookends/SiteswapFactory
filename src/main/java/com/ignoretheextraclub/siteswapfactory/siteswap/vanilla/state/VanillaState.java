package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
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

    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 15;

    protected static final int MIN_OBJECTS = 1;
    protected static final int MAX_OBJECTS = 12;

    @JsonIgnore private final boolean[] occupied;

    /**
     Actually constructs a new object

     @param occupied
     */
    public VanillaState(final boolean[] occupied) throws NumObjectsException, PeriodException
    {
        validateSize(occupied.length);
        validateNumObjects(VanillaStateUtils.getNumTrue(occupied));
        this.occupied = occupied;
    }

    @Override
    public Thro getMaxThrow()
    {
        return VanillaThro.getUnchecked(occupied.length);
    }

    /**
     Lower is closer to ground

     @return
     */
    @Override
    public int excitedness()
    {
        int result = 0;
        int position_value = 1;
        final int maxThro = getMaxThrow().getNumBeats();

        for (int position = maxThro - 1; position >= 0; position--)
        {
            if (!occupied[position])
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
            throw new IllegalStateException("Vanilla State could not throw an available throw", e);
        }
    }

    @Override
    public Set<Thro> getAvailableThrows()
    {
        final Set<Thro> availableThros = new TreeSet<>();
        if (canThrow())
        {
            final int maxThro = getMaxThrow().getNumBeats();

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
        final int numBeats = thro.getNumBeats();
        if (!canThrow())
        {
            if (numBeats == 0)
            {
                try
                {
                    return new VanillaState(drop(occupied, false)); // throw 0
                }
                catch (final PeriodException | NumObjectsException cause)
                {
                    throw new IllegalStateException("Was forced to throw a 0, but could not", cause);
                }
            }
            throw new BadThrowException(this.toString() + " Cannot throw [" + thro + "], must throw 0");
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
        catch (final PeriodException | NumObjectsException cause)
        {
            throw new IllegalStateException("Could not throw legal throw.", cause);
        }
    }

    @Override
    public int getNumObjects()
    {
        return VanillaStateUtils.getNumTrue(this.occupied);
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
    public Thro getThrow(State toNextState) throws TransitionException
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
                throw new IllegalStateException("Could not throw legal throw from [" + thro + "] into [" + this.toString() + "]", badThrowException);
            }
        }
        throw new TransitionException("Cannot transition between these two vanillaStates, from [" + this.toString() + "] to [" + toNextState.toString() + "]");
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

        if (state.getNumObjects() != this.getNumObjects())
        {
            return false;
        }

        final int shorterLength = this.occupied.length < state.occupied.length ? this.occupied.length : state.occupied.length;

        for (int i = 0; i < shorterLength; i++)
        {
            if (this.occupied[i] != state.occupied[i])
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public VanillaState undo(final VanillaThro thro) throws UnsupportedOperationException
    {
        final boolean[] previous = new boolean[this.occupied.length];

        System.arraycopy(this.occupied, 0, previous, 1, this.occupied.length - 1);

        if (thro.getNumBeats() == 0)
        {
            previous[0] = false; // because threw 0
        }
        else if (thro.getNumBeats() == this.getMaxThrow().getNumBeats())
        {
            previous[0] = true;
        }
        else
        {
            previous[0] = true;
            previous[thro.getNumBeats()] = false;
        }

        try
        {
            return new VanillaState(previous);
        }
        catch (NumObjectsException | PeriodException e)
        {
            throw new IllegalStateException("Could not create a new state with the same size as this one");
        }
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

    public static void validateSize(final int size) throws PeriodException
    {
        if (size < MIN_SIZE || size > MAX_SIZE)
        {
            throw new PeriodException("State has [" + size + "] positions, must be between [" + MIN_SIZE + "] and [" + MAX_SIZE + "]");
        }
    }

    @Override
    public int compareTo(final Object o)
    {
        throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
    }
}
