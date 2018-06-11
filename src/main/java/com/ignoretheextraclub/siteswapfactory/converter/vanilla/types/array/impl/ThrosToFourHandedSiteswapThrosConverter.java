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
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

/**
 * Downcasts a Thro[] to a FourHandedSiteswapThro[]
 *
 * @author Caspar Nonclercq
 */
public class ThrosToFourHandedSiteswapThrosConverter implements Function<Thro[], FourHandedSiteswapThro[]>
{
    private static ThrosToFourHandedSiteswapThrosConverter INSTANCE;

    private ThrosToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static ThrosToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ThrosToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Downcasts a Thro[] to a FourHandedSiteswapThro[]
     *
     * @param thros a Thro array
     *
     * @return a FourHandedSiteswapThro array
     */
    @Override
    public FourHandedSiteswapThro[] apply(final Thro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final FourHandedSiteswapThro[] result = new FourHandedSiteswapThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            result[i] = FourHandedSiteswapThro.get(thros[i].getNumBeats());
        }

        return result;
    }

    /**
     * Convenient static method to conver thros to FourHandedSiteswapThros
     *
     * @param thros the throw array
     *
     * @return the FourHandedSiteswap array
     */
    public static FourHandedSiteswapThro[] convert(final Thro[] thros)
    {
        return get().apply(thros);
    }
}
