package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.iterators.SingletonIterator;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Represents a VanillaState.
 *
 * Internally tracks the positions using an {@code int}.
 *
 * @author Caspar Nonclercq
 */
public class VanillaState implements State
{
    protected static final String EMPTY = "_";
    protected static final String FILLED = "X";

    private final long state;

    public VanillaState(final long state)
    {
        this.state = state;
    }

    public VanillaState(final boolean[] booleans)
    {
        int stateBuilder = 0;
        for (int i = 0; i < booleans.length; i++)
        {
            if (booleans[i])
            {
                stateBuilder = stateBuilder | (1 << i);
            }
        }
        this.state = stateBuilder;
    }


    @Override
    public Thro getMaxThrow()
    {
        return VanillaThro.getMaxThro();
    }

    @Override
    public long excitedness()
    {
        return state;
    }

    @Override
    public Iterator<Thro> getAvailableThrows()
    {
        if (!canThrow())
        {
            return new SingletonIterator<>(VanillaThro.get(0));
        }

        return new Iterator<Thro>()
        {
            private int position;
	        private final int max= getMaxThrow().getNumBeats();
	        private Thro next = getNext();

            @Override
            public boolean hasNext()
            {
                return this.next != null;
            }

            @Override
            public Thro next()
            {
                final Thro next = this.next;
                this.next = getNext();
                return next;
            }

            private Thro getNext()
            {
                while (position < max)
                {
                    if (!isSet(position))
                    {
                        return VanillaThro.get(position++);
                    }

                    position++;
                }
                return null;
            }
        };
    }

    public Thro getThrow(final State toNextState) throws TransitionException
    {
        final VanillaState next = (VanillaState) toNextState;

        final long before = (next.state << 1) | 1;

        long position = (this.state ^ before) >> 1;

        if (!isPowerOfTwo(position))
        {
            throw new TransitionException("Cannot transition between from [" + this.toString() + "] to [" + toNextState.toString() + "]");
        }
        else
        {
            int thro = 0;

            for (; position > 0; thro++)
            {
                position >>= 1;
            }

            return VanillaThro.get(thro);
        }
    }

    @Override
    public boolean canTransition(final State toNextState)
    {
        return isPowerOfTwo((this.state >> 1) ^ ((VanillaState) toNextState).state);
    }

    @Override
    public State thro(final Thro thro) throws BadThrowException
    {
        Objects.requireNonNull(thro, "thro cannot be null");

        if (canThrow() ^ thro.getNumBeats() == 0)
        {
            return new VanillaState((this.state | (1 << thro.getNumBeats())) >> 1);
        }

        throw new BadThrowException("Throw [" + thro + "] cannot be thrown from [" + this.toString() + "]");
    }

    @Override
    public int getNumObjects()
    {
        return numBitsSet(state);
    }

    @Override
    public boolean isGroundState()
    {
        return isPowerOfTwo(state + 1);
    }

    public VanillaState undo(final VanillaThro thro) throws UnsupportedOperationException
    {
        return new VanillaState((this.state << 1 & ~(1 << thro.getNumBeats())) | (thro.getNumBeats() == 0 ? 0 : 1));
    }

    @Override
    public int compareTo(final Object o)
    {
        return (int) (this.state - ((VanillaState) o).state);
    }

    public boolean canThrow()
    {
        return isSet(0);
    }

    private boolean isSet(final int position)
    {
        return isSet(this.state, position);
    }

    @Override
    public String toString()
    {
        final StringBuilder stringBuilder = new StringBuilder();

        long state = this.state;

        while (state > 0)
        {
            stringBuilder.append(((state & 1) == 1) ? FILLED : EMPTY);
            state >>= 1;
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final VanillaState state1 = (VanillaState) o;

        return state == state1.state;
    }

    @Override
    public int hashCode()
    {
        return (int) state;
    }

    static boolean isSet(final long on, final int position)
    {
        return (on & (1 << position)) > 0;
    }

    static boolean isPowerOfTwo(final long num)
    {
        return (num & (num - 1)) == 0;
    }

    static int numBitsSet(long state)
    {
        int count = 0;

        while (state > 0)
        {
            count += state & 1;
            state >>= 1;
        }

        return count;
    }
}
