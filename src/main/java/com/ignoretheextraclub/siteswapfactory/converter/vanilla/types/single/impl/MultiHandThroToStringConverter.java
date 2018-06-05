package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

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
