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

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

/**
 * Maps a {@link MultiHandThro} in the standard passing format.
 * {@code <3p|3p>}, {@code <3p2|3p3|3p1>}
 *
 * Jugglers are indexed starting with 1.
 */
public class MultiHandThroToStringConverter implements Function<MultiHandThro, String>
{
	private static MultiHandThroToStringConverter INSTANCE;

	private MultiHandThroToStringConverter()
	{
	    // Singleton
	}

	public static MultiHandThroToStringConverter get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new MultiHandThroToStringConverter();
	    }
	    return INSTANCE;
	}

	@Override
	public String apply(final MultiHandThro multiHandThro)
	{
		return multiHandThro.toString();
	}
}
