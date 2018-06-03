package com.ignoretheextraclub.siteswapfactory.siteswap;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * A cataloge of states and utilities for making specific states.
 *
 * @author Caspar Nonclercq
 */
public final class StateTestUtils
{
    public static final String MESSAGE = "Test configuration incorrect";


    // 3 Ball - Max Throw: 3
    public static final VanillaState XXX = parse("XXX");

    // 3 Ball - Max Throw: 4
    public static final VanillaState XXX_ = parse("XXX_");
    public static final VanillaState XX_X = parse("XX_X");
    public static final VanillaState X_XX = parse("X_XX");
    public static final VanillaState _XXX = parse("_XXX");

    // 3 ball - Max Throw: 5
    public static final VanillaState XXX__ = parse("XXX__");
    public static final VanillaState XX_X_ = parse("XX_X_");
    public static final VanillaState XX__X = parse("XX__X");
    public static final VanillaState X_XX_ = parse("X_XX_");
    public static final VanillaState X_X_X = parse("X_X_X");
    public static final VanillaState X__XX = parse("X__XX");
    public static final VanillaState _XXX_ = parse("_XXX_");
    public static final VanillaState _XX_X = parse("_XX_X");
    public static final VanillaState _X_XX = parse("_X_XX");
    public static final VanillaState __XXX = parse("__XXX");

    // 3 ball - Max Throw: 6
    public static final VanillaState _XXX__ = parse("_XXX__");
    public static final VanillaState XXX___ = parse("XXX___");

    // 4 ball - Max Throw: 4
    public static final VanillaState XXX_X = parse("XXX_X");
    public static final VanillaState XXXX_ = parse("XXXX_");

    public static final VanillaState XX_XX = parse("XX_XX");
    public static final VanillaState X_XXX = parse("X_XXX");
    public static final VanillaState _XXXX = parse("_XXXX");

    public static final VanillaState XXXXX_X = parse("XXXXX_X");
    public static final VanillaState XXXXXX = parse("XXXXXX");

    // 4 ball - Max Throw: 6
    public static final VanillaState XXXX__ = parse("XXXX__");
    public static final VanillaState X____X = parse("X____X");
    public static final VanillaState XXX_X_ = parse("XXX_X_");
    public static final VanillaState XXX__X = parse("XXX__X");

    // 5 ball - Max Throw: 9
    public static final VanillaState XX_X_X_X_ = parse("XX_X_X_X_");
    public static final VanillaState XXX_X_X__ = parse("XXX_X_X__");
    public static final VanillaState XXXX_X___ = parse("XXXX_X___");
    public static final VanillaState XXXXX____ = parse("XXXXX____");
    public static final VanillaState XX_X_XX__ = parse("XX_X_XX__");

    // 7 ball - Max Throw: 9
    public static final VanillaState XXXXXXX__ = parse("XXXXXXX__");

    // 7 ball - Max Throw: 10
    public static final VanillaState XXXXXXX___ = parse("XXXXXXX___");

    // 8 ball - Max Throw: 10
    public static final VanillaState XXXXXXXX__ = parse("XXXXXXXX__");

    // other
    public static final VanillaState ____X = parse("____X");


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

    private static VanillaState parse(final String string)
    {
        final boolean[] booleans = new boolean[string.length()];

        final char[] charArray = string.toCharArray();

        for (int i = 0; i < charArray.length; i++)
        {
            booleans[i] = charArray[i] == 'X';
        }

        return new VanillaState(booleans);
    }
}
