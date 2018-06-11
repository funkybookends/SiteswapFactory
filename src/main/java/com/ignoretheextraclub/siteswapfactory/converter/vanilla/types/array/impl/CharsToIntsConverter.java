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

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.CharToIntConverter;

/**
 * Converts a char[] to an int[]
 *
 * @author Caspar Nonclercq
 */
public class CharsToIntsConverter implements Function<char[], int[]>
{
    private static CharsToIntsConverter INSTANCE;

    private CharsToIntsConverter()
    {
        // Singleton
    }

    public static CharsToIntsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharsToIntsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a char[] to an int[]
     *
     * @param thros an array of thros
     *
     * @return an array of ints
     */
    @Override
    public int[] apply(final char[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final int[] intThrows = new int[thros.length];

        final CharToIntConverter charToIntConverter = CharToIntConverter.get();

        for (int i = 0; i < thros.length; i++)
        {
            intThrows[i] = charToIntConverter.apply(thros[i]);
        }

        return intThrows;
    }

    /**
     * Convenient static method to apply chars to ints
     *
     * @param chars
     *
     * @return
     */
    public static int[] convert(final char[] chars)
    {
        return get().apply(chars);
    }
}
