package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

/**
 Created by caspar on 26/07/17.
 */
public final class VanillaStateUtils
{
    private VanillaStateUtils()
    {
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
