package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;
import org.apache.commons.collections4.iterators.PermutationIterator;

import java.util.ArrayList;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 Created by caspar on 26/07/17.
 */
public final class VanillaStateUtils
{
    private VanillaStateUtils()
    {
    }

    public static VanillaState getFirstState(final VanillaThro[] thros) throws InvalidSiteswapException
    {
        return getFirstState(thros, VanillaThroUtils.numObjects(thros));
    }

    public static VanillaState getFirstState(final VanillaThro[] thros,
                                             final int numObjects) throws InvalidSiteswapException
    {
        try
        {
            final VanillaThro highestThro = ThroUtils.getHighestThro(thros);
            final VanillaStateBuilder builder; // period & num objects
            builder = new VanillaStateBuilder(highestThro.getNumBeats(), numObjects);
            final Stack<VanillaThro> throStack = new Stack<>();

            final ArrayLoopingIterator<VanillaThro> throsLooper = new ArrayLoopingIterator<>(thros);

            while (builder.getGivenObjects() != numObjects)
            {
                final VanillaThro thro = throsLooper.next();
                throStack.push(thro);
                builder.thenThrow(thro); // bad throw & num objects

                try
                {
                    VanillaState.validateSize(throStack.size());
                }
                catch (PeriodException e)
                {
                    throw new InvalidSiteswapException("After " + throStack.size() + " throws of " + VanillaThroUtils.vanillaThrowArrayToString(
                            thros) + " still had not thrown " + numObjects + " objects");
                }
            }

            VanillaState state = new VanillaState(builder.getOccupied()); // period & numObjects

            while (throStack.size() > 0)
            {
                final VanillaThro thro = throStack.pop();
                state = state.undo(thro);
            }

            return state;
        }
        catch (final PeriodException | NumObjectsException cause)
        {
            throw new InvalidSiteswapException("Could not determine first state", cause);
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Invalid Sequence", cause);
        }
    }

    public static State getGroundState(final int numObjects, final int maxThrow) throws PeriodException, NumObjectsException
    {
        if (maxThrow < numObjects)
        {
            throw new IllegalArgumentException("maxThrow must not be less than numObjects");
        }
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

    public static VanillaThro[] castAllToVanillaThro(final Thro[] thros)
    {
        final VanillaThro[] result = new VanillaThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = (VanillaThro) thros[i];
        }

        return result;
    }

    public static VanillaState[] castAllToVanillaState(final State[] states)
    {
        final VanillaState[] result = new VanillaState[states.length];

        for (int i = 0; i < states.length; i++)
        {
            result[i] = (VanillaState) states[i];
        }

        return result;
    }

    public static Stream<VanillaState> getAllStates(final int numObjects, final int maxThro)
    {
        if (maxThro < numObjects)
        {
            throw new IllegalArgumentException("maxThrow must not be less than numObjects");
        }
        final ArrayList<Boolean> groundState = new ArrayList<>();

        for (int i = 0; i < maxThro; i++)
        {
            groundState.add(i < numObjects ? Boolean.TRUE : Boolean.FALSE);
        }

        final PermutationIterator<Boolean> permsIterator = new PermutationIterator<>(groundState);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(permsIterator,
                Spliterator.ORDERED | Spliterator.DISTINCT | Spliterator.NONNULL), false)
                            .map(list ->
        {
            final boolean[] array = new boolean[list.size()];

            for (int i = 0; i < array.length; i++)
            {
                array[i] = list.get(i);
            }

            try
            {
                return new VanillaState(array);
            }
            catch (final NumObjectsException | PeriodException cause)
            {
                throw new IllegalArgumentException("Cannot create states", cause);
            }
        }).distinct(); // TODO improve - currently each combination multiple times.
    }
}
