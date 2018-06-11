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

package com.ignoretheextraclub.siteswapfactory.comparators;

import java.util.Comparator;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Compares two siteswaps by the number of jugglers. Siteswaps with less jugglers will come first.
 *
 * @author Caspar Nonclercq
 */
public class NumJugglersComparator implements Comparator<Siteswap>
{
    private static NumJugglersComparator INSTANCE;

    private NumJugglersComparator()
    {
        // Singleton
    }

    public static NumJugglersComparator get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new NumJugglersComparator();
        }
        return INSTANCE;
    }

    /**
     * Compares two siteswaps by the number of jugglers. Siteswaps with less jugglers will come first.
     * <p>
     * Note: this comparator imposes orderings that are inconsistent with equals.
     *
     * @param first  The first siteswap
     * @param second The second siteswap
     *
     * @return A negative number if {@code first} has less jugglers, 0 if they are the same, and positive if second has
     * less jugglers.
     */
    @Override
    public int compare(final Siteswap first, final Siteswap second)
    {
        Objects.requireNonNull(first, "first cannot be null");
        Objects.requireNonNull(second, "second cannot be null");
        return first.getNumJugglers() - second.getNumJugglers();
    }
}
