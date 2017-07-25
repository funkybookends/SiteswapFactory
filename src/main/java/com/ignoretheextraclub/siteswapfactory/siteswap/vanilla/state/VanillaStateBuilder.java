package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.StateSizeException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState.EMPTY;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState.FILLED;
import static com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils.drop;

/**
 Created by caspar on 25/07/17.
 */
class VanillaStateBuilder
{
    private final int maxThrow;
    private final int expectedObjects;
    private boolean[] occupied;
    private int givenObjects;

    public VanillaStateBuilder(final int maxThrow,
                               final int expectedObjects) throws StateSizeException, NumObjectsException
    {
        VanillaState.validateSize(maxThrow);
        VanillaState.validateNumObjects(expectedObjects);

        this.maxThrow = maxThrow;
        this.expectedObjects = expectedObjects;
        this.occupied = new boolean[maxThrow];
    }

    public VanillaStateBuilder thenThrow(final VanillaThro aThro) throws BadThrowException, NumObjectsException
    {
        final int thro = aThro.getNumBeats();
        if (thro < 0 || thro > maxThrow)
        {
            throw new BadThrowException("VanillaThro [" + thro + "] out of bounds [0," + maxThrow + "]");
        }

        if (!occupied[0] && thro != 0)
        {
            givenObjects++;
        }

        if (givenObjects > expectedObjects)
        {
            throw new NumObjectsException("Given an unexpected object. Already have [" + givenObjects + "] in [" + this
                    .toString() + "]");
        }

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

    public boolean[] getOccupied() throws StateSizeException, NumObjectsException
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
        return VanillaStateUtils.toString(occupied, FILLED, EMPTY);
    }
}
