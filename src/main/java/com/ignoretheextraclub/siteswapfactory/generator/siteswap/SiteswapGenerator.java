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

package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.stream.Stream;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Class used to generate {@link Siteswap}s. May be typed to generate specific subclasses of {@link Siteswap}.
 * @param <T> The type of siteswap to generate.
 * @author Caspar Nonclercq
 */
public interface SiteswapGenerator<T extends Siteswap>
{
    /**
     * Creates a stream of {@link Siteswap}s. The characteristics of the stream will be definied by the implementation.
     * This interface provides no guarantees of order or uniqueness.
     * @return A stream of {@link Siteswap}s.
     */
    Stream<T> generate();
}
