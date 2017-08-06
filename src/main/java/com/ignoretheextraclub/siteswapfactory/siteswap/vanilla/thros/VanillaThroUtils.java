package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 Created by caspar on 26/07/17.
 */
public final class VanillaThroUtils
{
    private VanillaThroUtils(){}

    /**
     Will determine the number of objects in a pattern.
     <p>
     It will not validate the pattern, provided the list is not empty it will return an int, otherwise it will throw
     {@link java.util.NoSuchElementException}. If it is not a valid siteswap, the int may not be correct.
     <p>
     You are encourage to create a {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap}
     to validate the siteswap.

     @param thros

     @return the average as an int.
     */
    public static int numObjects(VanillaThro[] thros)
    {
        return (int) Arrays.stream(thros).mapToInt(VanillaThro::getNumBeats).average().getAsDouble();
    }

    public static int[] globalToLocal(final int[] global, final int startPos)
    {
        final int[] local = new int[global.length];
        for (int i = 0; i < global.length; i++)
        {
            local[i] = global[(startPos + (i * 2)) % global.length];
        }
        return local;
    }

    /**
     Returns an array of ints in global as if they were given in local. This only makes sense for 4 hands.
     @param local
     @return global
     */
    public static int[] localToGlobal(final int[] local)
    {
        final int[] global = new int[local.length];
        int fromStart = 0;
        int fromMiddle = local.length / 2;
        if (local.length % 2 == 1)
        {
            fromMiddle++;
        }
        int insertionIndex = 0;
        while (fromStart < (local.length / 2) + 1)
        {
            global[insertionIndex] = local[fromStart];
            insertionIndex++;
            if (fromMiddle < local.length)
            {
                global[insertionIndex] = local[fromMiddle]; //incase odd
            }
            insertionIndex++;
            fromStart++;
            fromMiddle++;
        }
        return global;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] globalToLocal(final T[] global, final int startPos)
    {
        final T[] local = (T[]) Array.newInstance(global.getClass().getComponentType(), global.length);
        for (int i = 0; i < global.length; i++)
        {
            local[i] = global[(startPos + (i * 2)) % global.length];
        }
        return local;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] localToGlobal(final T[] local)
    {
        final T[] global = (T[]) Array.newInstance(local.getClass().getComponentType(), local.length);
        int fromStart = 0;
        int fromMiddle = local.length / 2;
        if (local.length % 2 == 1)
        {
            fromMiddle++;
        }
        int insertionIndex = 0;
        while (fromStart < (local.length / 2) + 1)
        {
            global[insertionIndex] = local[fromStart];
            insertionIndex++;
            if (fromMiddle < local.length)
            {
                global[insertionIndex] = local[fromMiddle]; //incase odd
            }
            insertionIndex++;
            fromStart++;
            fromMiddle++;
        }
        return global;
    }

    /**
     Converts an array of VanillaThros to an int array.

     @param thros

     @return an int array
     */
    public static int[] vanillaThrowArrayToIntArray(VanillaThro[] thros)
    {
        final int[] intThros = new int[thros.length];
        for (int i = 0; i < thros.length; i++)
        {
            intThros[i] = thros[i].getNumBeats();
        }
        return intThros;
    }

    public static String vanillaThrowArrayToString(VanillaThro[] thros)
    {
        return intArrayToString(vanillaThrowArrayToIntArray(thros));
    }

    /**
     Converts an int array to a string. Guaranteed to not throw an exception
     <p>
     It is your responsibility to catch invalid throws.

     @param thros

     @return A string representation of the throws, with {@link VanillaThro#INVALID_CHAR} for any invalid throws.
     */
    public static String intArrayToString(final int[] thros)
    {
        return new String(intArrayToCharArray(thros));
    }

    /**
     Converts an array of ints to an array of chars. Guaranteed to not throw an exception
     <p>
     It is your responsibility to catch invalid throws.

     @param intThrows

     @return charThrows, with {@link VanillaThro#INVALID_CHAR}s for invalid throws.
     */
    public static char[] intArrayToCharArray(final int[] intThrows)
    {
        final char[] charThrows = new char[intThrows.length];
        for (int i = 0; i < intThrows.length; i++)
        {
            charThrows[i] = intToChar(intThrows[i]);
        }
        return charThrows;
    }

    /**
     Converts a char to an int. Guaranteed to not throw an exception, returns -1 if not a valid char.
     <p>
     It is your responsibility to catch invalid throws.

     @param thro

     @return the thro as an int, or {@link VanillaThro#INVALID_INT} if not valid.
     */
    public static int charToInt(final char thro)
    {
        if (thro >= '0' && thro <= '9')
        {
            return thro - '0';
        }
        else if (thro >= 'A' && thro <= 'Z')
        {
            return thro - 'A' + 10;
        }
        else if (thro >= 'a' && thro <= 'z')
        {
            return thro - 'a' + 10;
        }
        else
        {
            return VanillaThro.INVALID_INT;
        }
    }

    /**
     Converts an int to a char. Guaranteed to not throw an exception, returns '?' if not valid.
     <p>
     It is your responsibility to catch invalid throws.

     @param thro

     @return the thro as a char, or {@link VanillaThro#INVALID_CHAR} if not valid.
     */
    public static char intToChar(final int thro)
    {
        if (thro < 0)
        {
            return VanillaThro.INVALID_CHAR;
        }
        else if (thro < 10)
        {
            return (char) (thro + '0');
        }
        else if (thro < 36)
        {
            return (char) (thro + 'A' - 10);
        }
        else
        {
            return VanillaThro.INVALID_CHAR;
        }
    }

    /**
     Converts an array of {@link VanillaThro} to {@link String}.
     <p>
     Internally uses {@link #stringToIntArray(String)} and {@link #intArrayToVanillaThrowArray(int[])}.

     @param siteswap

     @return An array of {@link VanillaThro}
     */
    public static VanillaThro[] stringToVanillaThrowArray(String siteswap) throws BadThrowException
    {
        return intArrayToVanillaThrowArray(stringToIntArray(siteswap));
    }

    /**
     Batch converts an array of ints to an array of {@link VanillaThro}.
     <p>
     Will throw a {@link BadThrowException} if any are illegal throws.

     @param siteswap

     @return a VanillaThro array of equivalent throws.
     */
    public static VanillaThro[] intArrayToVanillaThrowArray(final int[] siteswap) throws BadThrowException
    {
        final VanillaThro[] thros = new VanillaThro[siteswap.length];
        for (int i = 0; i < siteswap.length; i++)
        {
            thros[i] = VanillaThro.get(siteswap[i]);
        }
        return thros;
    }

    /**
     Converts a string to an int array. Guaranteed to not thow an exception.
     <p>
     It is your responsibility to catch invalid throws.     *

     @param stringThrows

     @return an int array, with {@link VanillaThro#INVALID_INT} for any invalid throws.
     */
    public static int[] stringToIntArray(final String stringThrows)
    {
        return charArrayToIntArray(stringThrows.toCharArray());
    }

    /**
     Converts an array of chars to an int array

     @param charThrows

     @return an array of ints, with {@link VanillaThro#INVALID_INT} for any invalid throws.
     */
    public static int[] charArrayToIntArray(final char[] charThrows)
    {
        final int[] intThrows = new int[charThrows.length];
        for (int i = 0; i < charThrows.length; i++)
        {
            intThrows[i] = charToInt(charThrows[i]);
        }
        return intThrows;
    }

    /**
     Converts an array of ints to an FHS[]
     @param siteswap
     @return represented as an array of FHSs
     @throws InvalidSiteswapException
     */
    public static FourHandedSiteswapThro[] intArrayToFourHandedSiteswapThrowArray(final int[] siteswap) throws InvalidSiteswapException
    {
        try
        {
            final FourHandedSiteswapThro[] thros = new FourHandedSiteswapThro[siteswap.length];
            for (int i = 0; i < siteswap.length; i++)
            {
                thros[i] = FourHandedSiteswapThro.get(siteswap[i]);
            }
            return thros;
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Not a valid four handed siteswap", cause);
        }
    }



    public static FourHandedSiteswapThro[] castAllToFourHandedSiteswapThro(final Thro[] thros)
    {
        final FourHandedSiteswapThro[] result = new FourHandedSiteswapThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = (FourHandedSiteswapThro) thros[i];
        }

        return result;
    }
}
