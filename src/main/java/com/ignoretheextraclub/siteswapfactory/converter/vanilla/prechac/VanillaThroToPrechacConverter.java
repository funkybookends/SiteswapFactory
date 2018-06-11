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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.prechac;

import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a {@link com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro} to its prechac representation
 */
public class VanillaThroToPrechacConverter implements Function<VanillaThro, String>
{
	private static VanillaThroToPrechacConverter INSTANCE;

	private VanillaThroToPrechacConverter()
	{
		// Singleton
	}

	public static VanillaThroToPrechacConverter get()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new VanillaThroToPrechacConverter();
		}
		return INSTANCE;
	}

	@Override
	public String apply(final VanillaThro vanillaThro)
	{
		Objects.requireNonNull(vanillaThro, "thro cannot be null");

		final String thro = String.valueOf(vanillaThro.getNumBeats() / 2);

		if (isPass(vanillaThro))
		{
			return thro + ".5p";
		}
		else
		{
			return thro;
		}
	}

	private boolean isPass(final VanillaThro vanillaThro)
	{
		return vanillaThro.getNumBeats() % 2 == 1;
	}
}
