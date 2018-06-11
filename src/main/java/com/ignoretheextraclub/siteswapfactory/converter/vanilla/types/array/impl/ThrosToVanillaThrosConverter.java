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

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Casts {@link Thro} to {@link VanillaThro}.
 *
 * @author Caspar Nonclercq
 */
public class ThrosToVanillaThrosConverter implements Function<Thro[], VanillaThro[]>
{
    private static ThrosToVanillaThrosConverter INSTANCE;

    private ThrosToVanillaThrosConverter()
    {
        // Singleton
    }

    public static ThrosToVanillaThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ThrosToVanillaThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an array of throws to an array of VanillaThros
     *
     * @param thros the thros array
     *
     * @return the VanillaThros array
     */
    @Override
    public VanillaThro[] apply(final Thro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final VanillaThro[] result = new VanillaThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = VanillaThro.get(thros[i].getNumBeats());
        }

        return result;
    }

    /**
     * Convenient static method to apply an array of throws to an array of VanillaThros
     *
     * @param thros the thros array
     *
     * @return the VanillaThros array
     */
    public static VanillaThro[] convert(final Thro[] thros)
    {
        return get().apply(thros);
    }
}
