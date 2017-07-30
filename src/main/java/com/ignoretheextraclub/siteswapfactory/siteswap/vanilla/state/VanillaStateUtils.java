package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;

/**
 Created by caspar on 26/07/17.
 */
public final class VanillaStateUtils
{
    private VanillaStateUtils(){}

    public static VanillaState getFirstState(VanillaThro[] thros) throws InvalidSiteswapException
    {
        try
        {
            final int numObjects = VanillaThroUtils.numObjects(thros);
            final int period = thros.length;
            final VanillaThro highestThro = ThroUtils.getHighestThro(thros);
            final VanillaStateBuilder builder = new VanillaStateBuilder(highestThro.getNumBeats(), numObjects);

            int index = 0;

            while (builder.getGivenObjects() < numObjects || index % period != 0)
            {
                builder.thenThrow(thros[index % period]);
                index++;
            }

            return new VanillaState(builder.getOccupied());
        }
        catch (final PeriodException | NumObjectsException | BadThrowException cause)
        {
            throw new InvalidSiteswapException("Could not determine first state", cause);
        }
    }

    public static State getGroundState(final int maxThrow, final int numObjects) throws PeriodException, NumObjectsException
    {
        final boolean[] occupied = new boolean[maxThrow];
        for (int i = 0; i < maxThrow; i++)
        {
            occupied[i] = (i < numObjects);
        }
        return new VanillaState(occupied);
    }

    public static String toString(final boolean[] filledPositions, final String filled, final String empty)
    {
        StringBuilder strBuilder = new StringBuilder();
        for (boolean filledPosition : filledPositions)
        {
            if (filledPosition)
            {
                strBuilder.append(filled);
            }
            else
            {
                strBuilder.append(empty);
            }
        }
        return strBuilder.toString();
    }

    public static int getNumTrue(final boolean[] array)
    {
        int i = 0;
        for (boolean position : array)
        {
            if (position)
            {
                i++;
            }
        }
        return i;
    }
}
