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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import java.util.Arrays;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;

/**
 * Created by caspar on 26/07/17.
 */
public final class VanillaThroUtils
{
    private VanillaThroUtils() {}

    /**
     * Will determine the number of objects in a pattern.
     * <p>
     * It will not validate the pattern, provided the list is not empty it will return an int, otherwise it will throw
     * {@link java.util.NoSuchElementException}. If it is not a valid siteswap, the int may not be correct.
     * <p>
     * You are encourage to create a {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap}
     * to validate the siteswap.
     *
     * @param thros
     *
     * @return the average as an int.
     */
    public static int numObjects(VanillaThro[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        if (thros.length < 1)
        {
            throw new IllegalArgumentException("thros cannot be empty");
        }

        int sum = 0;

        for (int i = 0; i < thros.length; i++)
        {
            sum += thros[i].getNumBeats();
        }

        if (sum % thros.length != 0)
        {
            throw new BadThrowException("Throws " + Arrays.toString(thros) + " does not hava a valid average");
        }

        return sum / thros.length;
    }
}
