/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
