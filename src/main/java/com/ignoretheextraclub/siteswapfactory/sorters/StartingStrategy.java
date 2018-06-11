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

package com.ignoretheextraclub.siteswapfactory.sorters;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.function.UnaryOperator;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;

/**
 * Starting strategies must be able to tell which of two rotations are better. It must return {@code true} if the first
 * is preferable to the {@code second}
 *
 * @author Caspar Nonclercq
 */
public interface StartingStrategy extends UnaryOperator<GeneralCircuit>
{

    /**
     * The main method that needs to be implemented. When sorting, the state sorter will be asked which two state sequences is preferred.
     * <p>
     * In a tie break, the implementer can return either true or false.
     *
     * @param first  the first candidate.
     * @param second the second candidate.
     *
     * @return if the first is preferred
     *
     * @throws InvalidSiteswapException      if the sorter does not think the siteswap is valid
     * @throws UnsupportedOperationException if the sorter is unable to sort for this siteswap type.
     */
    boolean test(GeneralCircuit first, GeneralCircuit second) throws InvalidSiteswapException, UnsupportedOperationException;

    /**
     * Returns the simple name for this sorting strategy.
     *
     * @return the name.
     */
    String getName();

    /**
     * A human friendly description of how this sorter sorts.
     *
     * @return a description.
     */
    String getDescription(Locale locale);

    @Override
    default GeneralCircuit apply(GeneralCircuit generalCircuit)
    {
        return generalCircuit.getRotationStream()
            .reduce((first, second) -> this.test(first, second) ? first : second)
            .orElseThrow(NoSuchElementException::new);
    }
}
