package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.VanillaThrosToStringConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import java.util.Stack;
import java.util.function.BiFunction;

/**
 * Calculates the first state from an array of Thros. Requires the expected number of objects.
 *
 * @author Caspar Nonclercq
 */
public class ThrosToFirstStateBiConverter implements BiFunction<VanillaThro[], Integer, VanillaState>
{
    public static ThrosToFirstStateBiConverter INSTANCE;

    private ThrosToFirstStateBiConverter()
    {
        // Singleton
    }

    public static ThrosToFirstStateBiConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ThrosToFirstStateBiConverter();
        }
        return INSTANCE;
    }

    /**
     * Calculate the first state given an array of thros and the expected number of objects.
     *
     * @param thros      The array of thros
     * @param numObjects The expected number of objects
     *
     * @return The first state.
     */
    @Override
    public VanillaState apply(final VanillaThro[] thros, final Integer numObjects)
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
                builder.thenThrow(thro); // Throws bad throw & num objects

                try
                {
                    VanillaState.validateSize(throStack.size()); // To check we haven't gone too deep
                }
                catch (PeriodException e)
                {
                    throw new InvalidSiteswapException("After " + throStack.size() + " throws of " + VanillaThrosToStringConverter
                            .get()
                            .apply(thros) + " still had not thrown " + numObjects + " objects");
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
        catch (final BadThrowException | PeriodException | NumObjectsException cause)
        {
            throw new InvalidSiteswapException("Could not determine first state: " + cause.getMessage(), cause);
        }
    }
}
