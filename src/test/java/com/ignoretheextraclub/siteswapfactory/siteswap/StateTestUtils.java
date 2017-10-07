package com.ignoretheextraclub.siteswapfactory.siteswap;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.generator.state.VanillaStateGenerator;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 Created by caspar on 30/07/17.
 */
public final class StateTestUtils
{
    public static final String MESSAGE = "Test configuration incorrect";

    //@formatter:off
    // 3 Ball - Max Throw: 3
    public static final VanillaState XXX = state( true,  true,  true);

    // 3 Ball - Max Throw: 4
    public static final VanillaState XXX_ = state( true,  true,  true, false);
    public static final VanillaState XX_X = state( true,  true, false,  true);
    public static final VanillaState X_XX = state( true, false,  true,  true);
    public static final VanillaState _XXX = state(false,  true,  true,  true);
    
    // 3 ball - Max Throw: 5
    public static final VanillaState XXX__ = state( true,  true,  true, false, false);
    public static final VanillaState XX_X_ = state( true,  true, false,  true, false);
    public static final VanillaState XX__X = state( true,  true, false, false,  true);
    public static final VanillaState X_XX_ = state( true, false,  true,  true, false);
    public static final VanillaState X_X_X = state( true, false,  true, false,  true);
    public static final VanillaState X__XX = state( true, false, false,  true,  true);
    public static final VanillaState _XXX_ = state(false,  true,  true,  true, false);
    public static final VanillaState _XX_X = state(false,  true,  true, false,  true);
    public static final VanillaState _X_XX = state(false,  true, false,  true,  true);
    public static final VanillaState __XXX = state(false, false,  true,  true,  true);

    // 4 ball - Max Throw: 4
    public static final VanillaState XXX_X      = state( true,  true,  true, false,  true);
    public static final VanillaState XXXX_      = state( true,  true,  true,  true, false);

    // 4 ball - Max Throw: 5
    public static final VanillaState XXXX__     = state( true,  true,  true,  true, false, false);
    public static final VanillaState XXX__X_    = state( true,  true,  true, false, false,  true);
    public static final VanillaState X____X     = state( true, false, false, false, false,  true);
    public static final VanillaState XXX_X_     = state( true,  true,  true, false,  true, false);

    // 7 ball - Max Throw: 9
    public static final VanillaState XXXXXXX__  = state(true, true, true, true, true, true, true, false, false);

    // 7 ball - Max Throw: 10
    public static final VanillaState XXXXXXX___  = state(true, true, true, true, true, true, true, false, false, false);

    // 8 ball - Max Throw: 10
    public static final VanillaState XXXXXXXX__ = state(true, true, true, true, true, true, true, true, false, false);

    // other
    public static final VanillaState ____X = state(false, false, false, false,  true);

    //@formatter:on
    
    private static State thro(final State state, final VanillaThro unchecked)
    {
        try
        {
            return state.thro(unchecked);
        }
        catch (BadThrowException cause)
        {
            throw new RuntimeException(MESSAGE, cause);
        }
    }

    private StateTestUtils()
    {
    }

    public static VanillaState state(final boolean... occupied)
    {
        try
        {
            return new VanillaState(occupied);
        }
        catch (final NumObjectsException | PeriodException cause)
        {
            throw new RuntimeException(MESSAGE, cause);
        }
    }

    public static VanillaState[] states(final VanillaState... states)
    {
        return states;
    }

    public static VanillaThro[] thros(final VanillaThro... thros)
    {
        return thros;
    }

    public static int[] thros(final int... thros)
    {
        return thros;
    }

    public static Integer[] integerArray(final Integer... thros)
    {
        return thros;
    }

    private static State getOrRuntime(final int maxThrow, final int numObjects)
    {
        try
        {
            return VanillaStateGenerator.getGroundState(numObjects, maxThrow);
        }
        catch (final PeriodException | NumObjectsException cause)
        {
            throw new RuntimeException(MESSAGE, cause);
        }
    }
}
