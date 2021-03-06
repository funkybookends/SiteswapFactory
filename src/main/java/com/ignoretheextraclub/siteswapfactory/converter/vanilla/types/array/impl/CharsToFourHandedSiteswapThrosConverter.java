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

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

/**
 * Converts a char[] to a FourHandedSiteswapThro[]
 *
 * @author Caspar Nonclercq
 */
public class CharsToFourHandedSiteswapThrosConverter implements Function<char[], FourHandedSiteswapThro[]>
{
    private static CharsToFourHandedSiteswapThrosConverter INSTANCE;

    private CharsToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static CharsToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharsToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char[] to a FourHandedSiteswapThro[]
     *
     * @param thros the thros to apply
     *
     * @return the converted thros
     */
    @Override
    public FourHandedSiteswapThro[] apply(final char[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        try
        {
            final FourHandedSiteswapThro[] fourHandedSiteswapThros = new FourHandedSiteswapThro[thros.length];

            for (int i = 0; i < thros.length; i++)
            {
                fourHandedSiteswapThros[i] = FourHandedSiteswapThro.get(thros[i]);
            }

            return fourHandedSiteswapThros;
        }
        catch (final BadThrowException cause)
        {
            throw new BadThrowException("thros " + Arrays.toString(thros) + " contained a bad throw", cause);
        }
    }

    /**
     * Convenient static method to apply thros
     *
     * @param thros the thros to apply
     *
     * @return the converted thros
     */
    public static FourHandedSiteswapThro[] convert(final char[] thros)
    {
        return get().apply(thros);
    }
}
