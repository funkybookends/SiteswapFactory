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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a char to an int. Guaranteed to not throwan exception, returns -1 if not a valid char.
 *
 * @author Caspar Nonclercq
 */
public class CharToIntConverter implements Function<Character, Integer>
{
    private static CharToIntConverter INSTANCE;

    private CharToIntConverter()
    {
        // Singleton
    }

    public static CharToIntConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharToIntConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a character to an int
     *
     * @param thro a thro
     *
     * @return an int, -1 if invalid.
     */
    @Override
    public Integer apply(final Character thro)
    {
        if (thro >= '0' && thro <= '9')
        {
            return thro - '0';
        }
        else if (thro >= 'A' && thro <= 'Z')
        {
            return thro - 'A' + 10;
        }
        else if (thro >= 'a' && thro <= 'z')
        {
            return thro - 'a' + 10;
        }
        else
        {
            return VanillaThro.INVALID_INT;
        }
    }

    public static Integer convert(final Character thro)
    {
        return get().apply(thro);
    }
}
