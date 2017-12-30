package com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a vanilla throw to its hefflish name
 */
public class VanillaThroToHefflishConverter implements Function<VanillaThro, String>
{
	public static VanillaThroToHefflishConverter INSTANCE;

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
