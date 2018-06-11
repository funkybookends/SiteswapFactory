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

package com.ignoretheextraclub.siteswapfactory.sorters.impl;

/**
 Created by caspar on 12/08/17.
 */
public final class SortingTestUtils
{
    private SortingTestUtils(){}

    public static String[] getRotations(final String siteswap)
    {
        final int period = siteswap.length();

        final String doubleSiteswap = siteswap + siteswap;

        final String[] rotations = new String[period];

        for (int i = 0; i < period; i++)
        {
            rotations[i] = doubleSiteswap.substring(i, period + i);
        }

        return rotations;
    }
}
