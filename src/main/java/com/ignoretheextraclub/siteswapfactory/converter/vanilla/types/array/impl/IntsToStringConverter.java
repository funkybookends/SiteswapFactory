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

import java.util.function.Function;

/**
 * Converts an int[] to a string
 *
 * @author Caspar Nonclercq
 */
public class IntsToStringConverter implements Function<int[], String>
{
    private static IntsToStringConverter INSTANCE;

    private IntsToStringConverter()
    {
        // Singleton
    }

    public static IntsToStringConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntsToStringConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an int[] to a string
     *
     * @param ints ints to apply
     *
     * @return a string
     */
    @Override
    public String apply(final int[] ints)
    {
        return new String(IntsToCharsConverter.get().apply(ints));
    }

    /**
     * Convenient static method to apply ints to string
     *
     * @param ints the ints to apply
     *
     * @return the string
     */
    public static String convert(final int[] ints)
    {
        return get().apply(ints);
    }
}
