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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

/**
 * Maps a {@link MultiHandThro} in the standard passing format.
 * {@code <3p|3p>}, {@code <3p2|3p3|3p1>}
 *
 * Jugglers are indexed starting with 1.
 */
public class MultiHandThroToPassingStringConverter implements Function<MultiHandThro, String>
{
	private static final char PASS_MARKER = 'p';
	private static final String DELIMETER = "|";
	private static final String PREFIX = "<";
	private static final String SUFFIX = ">";

	private static MultiHandThroToPassingStringConverter INSTANCE;

	private MultiHandThroToPassingStringConverter()
	{
	    // Singleton
	}

	public static MultiHandThroToPassingStringConverter get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new MultiHandThroToPassingStringConverter();
	    }
	    return INSTANCE;
	}

	@Override
	public String apply(final MultiHandThro multiHandThro)
	{
		final boolean receiverNeedsToBeDecorated = multiHandThro.getNumHands() > 2;

		return IntStream.range(0, multiHandThro.getNumHands())
			.mapToObj(fromHand -> mapThrow(fromHand, multiHandThro, receiverNeedsToBeDecorated))
			.collect(Collectors.joining(DELIMETER, PREFIX, SUFFIX));
	}

	private String mapThrow(final int fromHand, final MultiHandThro multiHandThro, final boolean receiverNeedsToBeDecorated)
	{
		final StringBuilder stringBuilder = new StringBuilder();

		final MultiHandThro.HandSpecificThro throwForHand = multiHandThro.getThrowForHand(fromHand);

		stringBuilder.append(throwForHand.getNumBeats());

		final boolean isPass = throwForHand.getToHand() != fromHand;

		if (isPass)
		{
			stringBuilder.append(PASS_MARKER);

			if (receiverNeedsToBeDecorated)
			{
				stringBuilder.append(throwForHand.getToHand() + 1);
			}
		}

		return stringBuilder.toString();
	}
}
