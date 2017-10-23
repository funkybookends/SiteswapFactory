package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils.drop;

/**
 Created by caspar on 25/07/17.
 */
public class VanillaStateBuilder
{
    private final int maxThrow;
    private final int expectedObjects;
    private boolean[] occupied;
    private int givenObjects;

    public VanillaStateBuilder(final int maxThrow, final int expectedObjects) throws PeriodException, NumObjectsException
    {
        if (expectedObjects > maxThrow)
        {
            throw new IllegalArgumentException("expectedObjects cannot be larger than maxThrow");
        }

        this.maxThrow = maxThrow;
        this.expectedObjects = expectedObjects;
        this.occupied = new boolean[maxThrow];
    }

    public VanillaStateBuilder thenThrow(final VanillaThro aThro) throws BadThrowException, NumObjectsException
    {
        final int thro = aThro.getNumBeats();

        // Check Throw
        if (thro < 0 || thro > maxThrow)
        {
            throw new BadThrowException("VanillaThro [" + thro + "] out of bounds [0," + maxThrow + "]");
        }

        // Record new object
        if (!occupied[0] && thro != 0)
        {
            givenObjects++;
        }

        // Check num objects
        if (givenObjects > expectedObjects)
        {
            throw new NumObjectsException("Given an unexpected object. Expecting [" + expectedObjects + "]");
        }

        // Do throw
        if (thro == maxThrow)
        {
            occupied = drop(occupied, true);
        }
        else if (occupied[thro])
        {
            throw new BadThrowException("VanillaThro [" + thro + "] cannot be made: [" + this.toString() + "]");
        }
        else
        {
            occupied[thro] = true;
            occupied = drop(occupied, false);
        }

        return this;
    }

    public boolean[] getOccupied() throws PeriodException, NumObjectsException
    {
        return occupied;
    }

    public int getGivenObjects()
    {
        return givenObjects;
    }

    @Override
    public String toString()
    {
        return new VanillaState(occupied).toString();
    }

    public VanillaState getState()
    {
        return new VanillaState(this.occupied);
    }
}
