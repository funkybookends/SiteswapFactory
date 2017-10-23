package com.ignoretheextraclub.siteswapfactory.generator.state;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

/**
 * A generator for finding {@link VanillaState}s.
 *
 * @author Caspar Nonclercq
 */
public class VanillaStateGenerator
{
    private static final Logger LOG = LoggerFactory.getLogger(VanillaStateGenerator.class);

    public static State getGroundState(final int numObjects) throws PeriodException, NumObjectsException
    {
        return new VanillaState(getGroundAsLong(numObjects));
    }

    public static Stream<VanillaState> getAllStates(final int numObjects, final int maxThro)
    {
        if (maxThro < numObjects)
        {
            throw new IllegalArgumentException("maxThrow must not be less than numObjects");
        }

        final long ground = getGroundAsLong(numObjects);
        final long max = ground << (maxThro - numObjects);

        return LongStream.rangeClosed(ground, max)
            .filter(state -> numBitsSet(state) == numObjects)
            .boxed()
            .map(VanillaState::new);
    }

    private static int numBitsSet(long state)
    {
        int count = 0;

        while (state > 0)
        {
            count += state & 1;
            state >>= 1;
        }

        return count;
    }

    private static int getGroundAsLong(final int numObjects)
    {
        return (1 << numObjects) - 1;
    }
}
