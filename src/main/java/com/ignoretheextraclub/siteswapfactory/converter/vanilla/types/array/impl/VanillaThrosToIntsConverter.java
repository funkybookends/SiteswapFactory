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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a {@link VanillaThro}[] to an {@code int[]}
 *
 * @author Caspar Nonclercq
 */
public class VanillaThrosToIntsConverter implements Function<VanillaThro[], int[]>
{
    private static VanillaThrosToIntsConverter INSTANCE;

    private VanillaThrosToIntsConverter()
    {
        // Singleton
    }

    public static VanillaThrosToIntsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToIntsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an array of vanilla thros to ints
     *
     * @param thros the vanilla thros
     *
     * @return the array of ints
     */
    @Override
    public int[] apply(final VanillaThro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final int[] intThros = new int[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            intThros[i] = thros[i].getNumBeats();
        }

        return intThros;
    }

    /**
     * Convenient static method to apply an array of vanilla thros to ints
     *
     * @param thros the vanilla thros
     *
     * @return the array of ints
     */
    public static int[] convert(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
