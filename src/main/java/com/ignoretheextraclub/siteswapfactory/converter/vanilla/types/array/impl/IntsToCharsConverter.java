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

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.IntToCharConverter;

/**
 * Converts an array of ints to an array of chars. Guaranteed to not throw an exception
 *
 * @author Caspar Nonclercq
 */
public class IntsToCharsConverter implements Function<int[], char[]>
{
    private static IntsToCharsConverter INSTANCE;

    private IntsToCharsConverter()
    {
        // Singleton
    }

    public static IntsToCharsConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntsToCharsConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts an array of ints to an array of chars. Guaranteed to not throw an exception
     *
     * @param thros the thros to apply
     *
     * @return the chars
     */
    @Override
    public char[] apply(final int[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        final char[] charThrows = new char[thros.length];

        final IntToCharConverter intToCharConverter = IntToCharConverter.get();

        for (int i = 0; i < thros.length; i++)
        {
            charThrows[i] = intToCharConverter.apply(thros[i]);
        }

        return charThrows;
    }

    /**
     * Convenient static method to apply throws to chars
     *
     * @param thros the throws to apply
     *
     * @return the converted chars
     */
    public static char[] convert(final int[] thros)
    {
        return get().apply(thros);
    }
}
