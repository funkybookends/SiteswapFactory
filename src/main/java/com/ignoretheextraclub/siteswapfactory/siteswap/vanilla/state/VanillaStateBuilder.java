package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState.isSet;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState.numBitsSet;

/**
 * A {@link VanillaState} builder that accepts throws and builds the state as the throws are made.
 * New objects are thrown if no existing object is available.
 *
 * @author Caspar Nonclercq
 */
public class VanillaStateBuilder
{
    private int state;

    public VanillaStateBuilder() throws PeriodException, NumObjectsException
    {
        this.state = 0;
    }

    public VanillaStateBuilder thenThrow(final VanillaThro aThro) throws BadThrowException, NumObjectsException
    {
        final int thro = aThro.getNumBeats();

        // Check landing
        if (isSet(state, thro))
        {
            throw new BadThrowException("VanillaThro [" + thro + "] cannot be made: [" + this.toString() + "]");
        }

        // Do throw
        state |= (1 << thro);
        state >>= 1;

        return this;
    }

    public int getGivenObjects()
    {
        return numBitsSet(state);
    }

    @Override
    public String toString()
    {
        return new VanillaState(state).toString();
    }

    public VanillaState getState()
    {
        return new VanillaState(this.state);
    }
}
