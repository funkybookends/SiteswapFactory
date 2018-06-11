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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToStringConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.VanillaThrosToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a VanillaThro[] to a String
 *
 * @author Caspar Nonclercq
 */
public class VanillaThrosToStringConverter implements Function<VanillaThro[], String>
{
    private static VanillaThrosToStringConverter INSTANCE;

    private VanillaThrosToStringConverter()
    {
        // Singleton
    }

    public static VanillaThrosToStringConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToStringConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a VanillaThro[] to a String
     *
     * @param thros The throws
     *
     * @return The string representation
     */
    @Override
    public String apply(final VanillaThro[] thros)
    {
        return convertToInts().andThen(convertToString()).apply(thros);
    }

    private IntsToStringConverter convertToString()
    {
        return IntsToStringConverter.get();
    }

    private VanillaThrosToIntsConverter convertToInts()
    {
        return VanillaThrosToIntsConverter.get();
    }

    /**
     * Convenient static method to apply thros to a string
     *
     * @param thros The throws
     *
     * @return A string representation
     */
    public static String toString(final VanillaThro[] thros)
    {
        return get().apply(thros);
    }
}
