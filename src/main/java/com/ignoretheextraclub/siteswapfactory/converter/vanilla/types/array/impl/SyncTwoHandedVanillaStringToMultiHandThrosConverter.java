package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.function.Function;
import java.util.stream.Stream;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

public class SyncTwoHandedVanillaStringToMultiHandThrosConverter implements Function<String, MultiHandThro[]>
{
	private static SyncTwoHandedVanillaStringToMultiHandThrosConverter INSTANCE;

	private SyncTwoHandedVanillaStringToMultiHandThrosConverter()
	{
	    // Singleton
	}

	public static SyncTwoHandedVanillaStringToMultiHandThrosConverter get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new SyncTwoHandedVanillaStringToMultiHandThrosConverter();
	    }
	    return INSTANCE;
	}

	@Override
	public MultiHandThro[] apply(String syncVanillaString)
	{
		final boolean isRepeatOnOtherSide = syncVanillaString.endsWith("*");

		if (isRepeatOnOtherSide)
		{
			syncVanillaString = syncVanillaString.substring(0, syncVanillaString.length() - 1);
		}

		final MultiHandThro[] thros = new MulipleThrowsString(syncVanillaString).getThros();

		if (!isRepeatOnOtherSide)
		{
			return thros;
		}

		final MultiHandThro[] allThros = new MultiHandThro[thros.length * 2];

		for (int i = 0; i < thros.length; i++)
		{
			allThros[i] = thros[i];
			allThros[i + thros.length] = thros[i].onTheOtherSide();
		}

		return allThros;
	}

	private static class MulipleThrowsString
	{
		private String thros;

		private MulipleThrowsString(final String thros)
		{
			this.thros = thros;
		}

		private MultiHandThro[] getThros()
		{
			if (thros.substring(0, 1).equals("("))
			{
				thros = thros.substring(1);
			}

			if (thros.endsWith(")"))
			{
				thros = thros.substring(0, thros.length() - 1);
			}

			return Stream.of(thros.split("\\)\\("))
				.map(ThrowString::new)
				.map(ThrowString::getMultiHandThro)
				.toArray(MultiHandThro[]::new);
		}
	}


	private static class ThrowString
	{
		private String thros;

		private ThrowString(final String thro)
		{
			this.thros = thro;
		}

		private MultiHandThro getMultiHandThro()
		{
			final String[] split = thros.split(",");

			if (split.length != 2)
			{
				throw new BadThrowException("Expected two throws, got " + split.length);
			}

			final MultiHandThro.HandSpecificThro[] thros = new MultiHandThro.HandSpecificThro[2];

			for (int i = 0; i < 2; i++)
			{
				final SingleThrow singleThrow = new SingleThrow(split[i], i);
				thros[i] = singleThrow.getHandSpecificThro();
			}

			return new MultiHandThro(thros);
		}
	}

	private static class SingleThrow
	{
		private static final int[] OTHER_HAND_FOR_HAND = {1, 0};

		private String singleThrow;
		private int hand;

		public SingleThrow(final String singleThrow, final int hand)
		{
			this.singleThrow = singleThrow;
			this.hand = hand;
		}

		private MultiHandThro.HandSpecificThro getHandSpecificThro()
		{
			final int toHand;

			if (singleThrow.endsWith("x"))
			{
				toHand = OTHER_HAND_FOR_HAND[hand];
				singleThrow = singleThrow.substring(0, singleThrow.length() -1);
			}
			else
			{
				toHand = hand;
			}

			final int numBeats = Integer.parseInt(singleThrow);

			return MultiHandThro.HandSpecificThro.get(toHand, numBeats/2);
		}
	}
}
