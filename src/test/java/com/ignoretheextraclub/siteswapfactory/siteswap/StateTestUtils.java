package com.ignoretheextraclub.siteswapfactory.siteswap;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

/**
 Created by caspar on 30/07/17.
 */
public final class StateTestUtils
{
    private StateTestUtils(){}

    public static VanillaState state(final boolean... occupied)
    {
        try
        {
            return new VanillaState(occupied);
        }
        catch (NumObjectsException | PeriodException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static VanillaState[] array(final VanillaState... states)
    {
        return states;
    }

    public static int[] array(final int... thros)
    {
        return thros;
    }

    public static Integer[] integerArray(final Integer... thros)
    {
        return thros;
    }
}
