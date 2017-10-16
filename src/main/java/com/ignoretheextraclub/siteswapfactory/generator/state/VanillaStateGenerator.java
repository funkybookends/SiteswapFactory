package com.ignoretheextraclub.siteswapfactory.generator.state;

import java.util.ArrayList;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.iterators.PermutationIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

/**
 * Created by caspar on 14/09/17.
 */
public class VanillaStateGenerator
{
    private static final Logger LOG = LoggerFactory.getLogger(VanillaStateGenerator.class);

    public static State getGroundState(final int numObjects,
                                       final int maxThrow) throws PeriodException, NumObjectsException
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
                            }).distinct(); // TODO improve - currently each combination multiple times - cannot be a recursive solution though.
    }
}
