package com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

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
