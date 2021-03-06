/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.iterators.SingletonIterator;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.utils.BitMaths;

/**
 * Represents a VanillaState.
 *
 * Internally tracks the positions using an {@code long}.
 *
 * @author Caspar Nonclercq
 */
public class VanillaState implements State, Serializable
{
    protected static final char EMPTY = '_';
    protected static final char FILLED = 'X';

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

    public VanillaState(final char[] chars)
    {
        int stateBuilder = 0;
        for (int i = 0; i < chars.length; i++)
        {
            if (chars[i] == FILLED)
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
        return BitMaths.numBitsSet(state);
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
        return BitMaths.isSet(this.state, position);
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

    static boolean isPowerOfTwo(final long num)
    {
        return (num & (num - 1)) == 0;
    }

}
