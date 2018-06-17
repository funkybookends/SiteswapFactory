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
 *
 *
 */

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * For a provided passing siteswap and a juggler, returns their sequence in hefflish.
 */
public class PassingSiteswapToHefflishSequence implements BiFunction<PassingSiteswap, Integer, String>
{
	protected static final String DELIMITER = ", ";
	private static PassingSiteswapToHefflishSequence INSTANCE;
	private static final VanillaThroToHefflishConverter VANILLA_THRO_TO_HEFFLISH_CONVERTER = VanillaThroToHefflishConverter.get();

	private PassingSiteswapToHefflishSequence()
	{
	    // Singleton
	}

	public static PassingSiteswapToHefflishSequence get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new PassingSiteswapToHefflishSequence();
	    }
	    return INSTANCE;
	}

	@Override
	public String apply(final PassingSiteswap passingSiteswap, final Integer juggler)
	{
		return Arrays.stream(passingSiteswap.getThrows())
			.map(thro -> (MultiHandThro) thro)
			.map(multiHandThro -> multiHandThro.getThrowForHand(juggler))
			.map(handSpecificThro -> convertToHefflishInt(juggler, handSpecificThro))
			.map(VanillaThro::get)
			.map(VANILLA_THRO_TO_HEFFLISH_CONVERTER)
			.collect(Collectors.joining(DELIMITER));
	}

	private int convertToHefflishInt(final Integer juggler, final MultiHandThro.HandSpecificThro handSpecificThro)
	{
		return handSpecificThro.getNumBeats() * 2 + (handSpecificThro.getToHand() == juggler ? 0 : 1);
	}
}
