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
 * Converts an int to a char. Guaranteed to not throw an exception, returns '?' if not valid.
 * @author Caspar Nonclercq
 */
public class IntToCharConverter implements Function<Integer, Character>
{
    private static IntToCharConverter INSTANCE;

    private IntToCharConverter()
    {
        // Singleton
    }

    public static IntToCharConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new IntToCharConverter();
        }
        return INSTANCE;
    }

    /**
     *
     * @param thro
     * @return
     */
    @Override
    public Character apply(final Integer thro)
    {
        if (thro == null || thro < 0)
        {
            return VanillaThro.INVALID_CHAR;
        }
        else if (thro < 10)
        {
            return (char) (thro + '0');
        }
        else if (thro < 36)
        {
            return (char) (thro + 'A' - 10);
        }
        else
        {
            return VanillaThro.INVALID_CHAR;
        }
    }
}
