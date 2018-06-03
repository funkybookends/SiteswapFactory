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
