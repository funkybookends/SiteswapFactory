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

package com.ignoretheextraclub.siteswapfactory.siteswap;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by caspar on 26/07/17.
 */
public interface Thro extends Comparable
{
    /**
     * Returns the number of beats before all objects in the throw land
     *
     * @return
     */
    int getNumBeats();

    /**
     * Get the number of objects needed to make this throw.
     *
     * @return int number of objects
     */
    int getNumObjectsThrown();

    @SuppressWarnings("unchecked")
    static <T extends Comparable> T getHighest(final T[] thros)
    {
        Objects.requireNonNull(thros, "thros cannot be null");

        return Arrays.stream(thros)
                     .reduce((left, right) -> left.compareTo(right) < 0 ? right : left)
                     .orElseThrow(() -> new IllegalArgumentException("thros must have at least one element"));
    }
}
