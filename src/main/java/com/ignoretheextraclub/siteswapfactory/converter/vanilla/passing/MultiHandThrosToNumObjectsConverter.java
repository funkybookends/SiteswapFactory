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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class MultiHandThrosToNumObjectsConverter implements Function<MultiHandThro[], Integer>
{
	private static MultiHandThrosToNumObjectsConverter INSTANCE;

	private MultiHandThrosToNumObjectsConverter()
	{
		// Singleton
	}

	public static MultiHandThrosToNumObjectsConverter get()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new MultiHandThrosToNumObjectsConverter();
		}
		return INSTANCE;
	}

	@Override
	public Integer apply(final MultiHandThro[] multiHandThros)
	{
		int beats = 0;
		int total = 0;

		for (final MultiHandThro thro : multiHandThros)
		{
			for (int hand = 0; hand < thro.getNumHands(); hand++)
			{
				total += thro.getThrowForHand(hand).getNumBeats() * thro.getNumHands();
				beats++;
			}
		}

		return total / beats;
	}

	public static Integer getNumObjects(final MultiHandThro[] multiHandThros)
	{
		return get().apply(multiHandThros);
	}
}
