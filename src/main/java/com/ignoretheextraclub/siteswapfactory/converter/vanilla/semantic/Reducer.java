package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import java.util.function.UnaryOperator;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;

/**
 * Returns the shortest repeating unit that when repeated n times would return the input array.
 * <p>
 * <pre>
 *     [A, B, C, A, B, C, A, B, C] -> [A, B, C]
 *     [A, B]                      -> [A, B]
 * </pre>
 *
 *
 * @author Caspar Nonclercq
 * @see StreamingFilteringReducer.IntReducer A primitive int version
 */
public interface Reducer extends UnaryOperator<GeneralCircuit>
{
    /**
     * Returns the reduced version of an array.
     * <p>
     * <pre>
     *     [A, B, C, A, B, C, A, B, C] -> [A, B, C]
     *     [A, B]                      -> [A, B]
     * </pre>
     *
     * @param duplicated The repeating array
     *
     * @return An new array that does not repeat or the original array
     */
    <T> T[] reduce(T[] duplicated);

    interface IntReducer
    {
        int[] apply(int[] duplicated);
    }

    static Reducer identity()
    {
        return new Reducer()
        {
            @Override
            public <T> T[] reduce(final T[] duplicated)
            {
                return duplicated;
            }
        };
    }

    @Override
    default GeneralCircuit apply(GeneralCircuit generalCircuit)
    {
        return new GeneralCircuit(generalCircuit.getStartingState(), reduce(generalCircuit.getThros()));
    }

    default <T> UnaryOperator<T[]> asOp()
    {
        return this::reduce;
    }
}
