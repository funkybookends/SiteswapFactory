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

/**
 * Converts a string to an int[]
 *
 * @author Caspar Nonclercq
 */
public class StringToIntsConverter implements Function<String, int[]>
{
    private static StringToIntsConverter INSTANCE;

    private StringToIntsConverter()
    {
        // Singleton
    }

    public static StringToIntsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToIntsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a string to an int[]
     *
     * @param thros a string to apply
     *
     * @return an array of ints
     */
    @Override
    public int[] apply(final String thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        return CharsToIntsConverter.get().apply(thros.toCharArray());
    }

    /**
     * Convenient static method to conver string to ints
     *
     * @param thros the string to apply
     *
     * @return the ints
     */
    public static int[] convert(final String thros)
    {
        return get().apply(thros);
    }
}
