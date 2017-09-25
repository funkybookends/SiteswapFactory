package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import java.util.Arrays;
import java.util.Stack;
import java.util.function.BiFunction;

/**
 * Calculates the first state from an array of Thros. Requires the expected number of objects.
 * <p>
 * If the number of objects is higher than the throw array will supply, it will throw an {@link
 * InvalidSiteswapException} where the cause is a {@link PeriodException}.
 * <p>
 * If the number of objects is lower than intended, it will return prematurely.
 *
 * @author Caspar Nonclercq
 * @see VanillaThrosToFirstStateConverter A version that attempts to detect the correct number of objects.
 */
public class VanillaThrosToFirstStateBiConverter implements BiFunction<VanillaThro[], Integer, VanillaState>
{
    public static VanillaThrosToFirstStateBiConverter INSTANCE;

    private VanillaThrosToFirstStateBiConverter()
    {
        // Singleton
    }

    public static VanillaThrosToFirstStateBiConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToFirstStateBiConverter();
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
        if (numObjects < 1)
        {
            throw new IllegalArgumentException("numObjects can't be less than one");
        }
        if (thros == null || thros.length < 1)
        {
            throw new IllegalArgumentException("thros must have at least one throw");
        }

        final Stack<VanillaThro> throStack = new Stack<>();

        try
        {
            final int highestThro = Thro.getHighest(thros).getNumBeats();

            final VanillaStateBuilder stateBuilder = new VanillaStateBuilder(highestThro,numObjects);

            final ArrayLoopingIterator<VanillaThro> throsLooper = new ArrayLoopingIterator<>(thros);

            while (stateBuilder.getGivenObjects() != numObjects)
            {
                throStack.push(throsLooper.peek());
                stateBuilder.thenThrow(throsLooper.next());
                VanillaState.validateSize(throStack.size());
            }

            VanillaState state = stateBuilder.getState();

            while (throStack.size() > 0)
            {
                final VanillaThro thro = throStack.pop();
                state = state.undo(thro);
            }

            return state;
        }
        catch (final BadThrowException | NumObjectsException cause)
        {
            throw new InvalidSiteswapException("Could not determine first state for thros " + Arrays.toString(thros) + ". Expecting [" + numObjects + "] objects.",
                    cause);
        }
        catch (final PeriodException cause)
        {
            throw new InvalidSiteswapException("After " + throStack.size() + " throws of " + Arrays.toString(thros) + " still had not thrown " + numObjects + " objects");
        }
    }

    /**
     * Convenient static method to get the first state from an array of thros.
     *
     * @param thros      The array of thros, assumed to loop.
     * @param numObjects The number of objects
     *
     * @return The first state.
     */
    public static VanillaState getFirstState(final VanillaThro[] thros, final Integer numObjects)
    {
        return get().apply(thros, numObjects);
    }
}
