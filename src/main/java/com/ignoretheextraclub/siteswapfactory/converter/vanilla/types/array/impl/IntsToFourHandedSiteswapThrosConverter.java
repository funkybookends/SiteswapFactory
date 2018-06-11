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

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

/**
 * Converts an int[] to a FourHandedSiteswapThro[]
 *
 * @author Caspar Nonclercq
 */
public class IntsToFourHandedSiteswapThrosConverter implements Function<int[], FourHandedSiteswapThro[]>
{
    private static IntsToFourHandedSiteswapThrosConverter INSTANCE;

    private IntsToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static IntsToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntsToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an int[] to a FourHandedSiteswapThro[]
     *
     * @param thros an array of ints
     *
     * @return an array of FourHandedSiteswapThros
     */
    @Override
    public FourHandedSiteswapThro[] apply(final int[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final FourHandedSiteswapThro[] fourHandedSiteswapThros = new FourHandedSiteswapThro[thros.length];

        for (int i = 0; i < thros.length; i++)
        {
            fourHandedSiteswapThros[i] = FourHandedSiteswapThro.get(thros[i]);
        }

        return fourHandedSiteswapThros;
    }

    /**
     * Convenient static method to apply ints to {@link FourHandedSiteswapThro}s
     *
     * @param thros ints
     *
     * @return FourHandedSiteswapThros
     */
    public static FourHandedSiteswapThro[] convert(final int[] thros)
    {
        return get().apply(thros);
    }
}
