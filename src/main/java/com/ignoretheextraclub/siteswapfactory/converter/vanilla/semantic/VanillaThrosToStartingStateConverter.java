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

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;

/**
 * Returns the first state from a set of {@link VanillaThro}s. Uses {@link VanillaThrosAndNumObjectsToFirstStateBiConverter} and
 * calculates the number of objects required by taking the average of all the throws.
 *
 * @author Caspar Nonclercq
 * @see VanillaThrosAndNumObjectsToFirstStateBiConverter Use this directly if you already know the number of objects
 */
public class VanillaThrosToStartingStateConverter implements Function<VanillaThro[], VanillaState>
{
    private static VanillaThrosToStartingStateConverter INSTANCE;

    private VanillaThrosToStartingStateConverter()
    {
        // Singleton
    }

    public static VanillaThrosToStartingStateConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToStartingStateConverter();
        }
        return INSTANCE;
    }

    /**
     * Gets the first state from an array of {@link VanillaThro}. Calculates the number of objects by taking the average
     * of the throws.
     *
     * @param thros The thros from which to calculate the first state
     *
     * @return The first state.
     */
    @Override
    public VanillaState apply(final VanillaThro[] thros)
    {
        return VanillaThrosAndNumObjectsToFirstStateBiConverter.get().apply(thros, VanillaThroUtils.numObjects(thros));
    }

    /**
     * Conveneint static method to get the first state from an array of {@link VanillaThro}.
     *
     * @param thros The thros from which to calculate the first state
     *
     * @return The first state
     *
     * @see #apply(VanillaThro[])
     */
    public static VanillaState getFirstState(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
