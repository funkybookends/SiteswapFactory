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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a vanilla throw to its hefflish name
 */
public class VanillaThroToHefflishConverter implements Function<VanillaThro, String>
{
	private static VanillaThroToHefflishConverter INSTANCE;

	private VanillaThroToHefflishConverter()
	{
		// Singleton
	}

	public static VanillaThroToHefflishConverter get()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new VanillaThroToHefflishConverter();
		}
		return INSTANCE;
	}

	private static final String[] HEFFLISH =
		{
			"Gap",
			"One",
			"Zip",
			"Three",
			"Hold",
			"Zap",
			"Self",
			"Pass",
			"Heff",
			"Double",
			"Trelf",
			"Triple"
		};

	private static final String UNKNOWN = "unknown";

	@Override
	public String apply(final VanillaThro vanillaThro)
	{
		try
		{
			return HEFFLISH[vanillaThro.getNumBeats()];
		}
		catch (final ArrayIndexOutOfBoundsException unknownThow)
		{
			return UNKNOWN;
		}
	}
}
