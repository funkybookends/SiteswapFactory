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

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Convert a VanillaThrow array to a hefflish sequence
 */
public class VanillaThrosToHefflishConverter implements Function<VanillaThro[], String>
{
	private static VanillaThrosToHefflishConverter INSTANCE;

	private static final String DELIMETER = ", ";

	private VanillaThrosToHefflishConverter()
	{
		// Singleton
	}

	public static VanillaThrosToHefflishConverter get()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new VanillaThrosToHefflishConverter();
		}
		return INSTANCE;
	}

	@Override
	public String apply(VanillaThro[] vanillaThros)
	{
		return Arrays.stream(vanillaThros)
			.map(VanillaThroToHefflishConverter.get())
			.collect(Collectors.joining(DELIMETER));
	}
}
