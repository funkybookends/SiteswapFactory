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

import org.apache.commons.lang.StringUtils;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StringToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a string of ints to an array of VanillaThros
 *
 * @author Caspar Nonclercq
 */
public class StringToVanillaThrosConverter implements Function<String, VanillaThro[]>
{
    private static StringToVanillaThrosConverter INSTANCE;

    private StringToVanillaThrosConverter()
    {
        // Singleton
    }

    public static StringToVanillaThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToVanillaThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a string of ints to an array of VanillaThros
     *
     * @param siteswap a string of ints
     *
     * @return An array of vanilla thros
     */
    @Override
    public VanillaThro[] apply(final String siteswap)
    {
        if (StringUtils.isEmpty(siteswap))
        {
            throw new IllegalArgumentException("siteswap cannot be null or empty");
        }
        try
        {
            return convertToInts().andThen(convertToVanillaThros()).apply(siteswap);
        }
        catch (final BadThrowException cause)
        {
            throw new BadThrowException("String [" + siteswap + "] contained a bad throw", cause);
        }
    }

    private IntsToVanillaThrosConverter convertToVanillaThros()
    {
        return IntsToVanillaThrosConverter.get();
    }

    private StringToIntsConverter convertToInts()
    {
        return StringToIntsConverter.get();
    }

    /**
     * Convenient static method to apply a string to an array of vanilla thros
     *
     * @param string A string of ints
     *
     * @return An array of Vanilla thros
     */
    public static VanillaThro[] convert(final String string)
    {
        return get().apply(string);
    }
}
