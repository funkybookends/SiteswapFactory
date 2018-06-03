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
