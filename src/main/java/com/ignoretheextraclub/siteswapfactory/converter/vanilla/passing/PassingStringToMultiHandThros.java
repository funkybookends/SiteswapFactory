package com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class PassingStringToMultiHandThros implements Function<String, MultiHandThro[]>
{
	private static PassingStringToMultiHandThros INSTANCE;

	private PassingStringToMultiHandThros()
	{
	    // Singleton
	}

	public static PassingStringToMultiHandThros get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new PassingStringToMultiHandThros();
	    }
	    return INSTANCE;
	}

	@Override
	public MultiHandThro[] apply(final String string)
	{
		final MultipleThrowsString multipleThrowsString = new MultipleThrowsString(string);
		return multipleThrowsString.getThrows();
	}

	public static MultiHandThro[] convert(final String siteswap)
	{
		return get().apply(siteswap);
	}

	private static class MultipleThrowsString
	{
		private String thros;

		private MultipleThrowsString(final String thros)
		{
			this.thros = thros;
		}

		private MultiHandThro[] getThrows()
		{
			if (thros.substring(0, 1).equals("<"))
			{
				thros = thros.substring(1);
			}

			if (thros.endsWith(">"))
			{
				thros = thros.substring(0, thros.length() - 1);
			}

			final String[] throStrings = thros.split("><");
			final MultiHandThro[] thros = new MultiHandThro[throStrings.length];

			for (int i = 0; i < throStrings.length; i++)
			{
				final MultiHandThrowString multiHandThrowString = new MultiHandThrowString(throStrings[i]);
				thros[i] = multiHandThrowString.getMultiHandThrow();
			}

			return thros;
		}
	}

	private static class MultiHandThrowString
	{
		private String thros;

		private MultiHandThrowString(final String thros)
		{
			this.thros = thros;
		}

		private MultiHandThro getMultiHandThrow()
		{
			final String[] stringThros = thros.split("\\|");

			final MultiHandThro.HandSpecificThro[] thros = new MultiHandThro.HandSpecificThro[stringThros.length];

			for (int i = 0; i < stringThros.length; i++)
			{
				final HandSpecificThrowString handSpecificThrowString = new HandSpecificThrowString(stringThros[i], i);
				thros[i] = handSpecificThrowString.getHandSpecificThrow();
			}

			return new MultiHandThro(thros);
		}
	}

	private static class HandSpecificThrowString
	{
		final String thro;
		final int hand;

		private HandSpecificThrowString(final String thro, final int hand)
		{
			this.thro = thro;
			this.hand = hand;
		}

		private MultiHandThro.HandSpecificThro getHandSpecificThrow()
		{
			int toHand = hand;
			int numBeats;

			if (isPass())
			{
				final String[] split = thro.split("p");

				if (split.length > 1)
				{
					toHand = Integer.parseInt(split[1]) - 1;
				}
				else if (hand == 0)
				{
					toHand = 1;
				}
				else
				{
					toHand = 0;
				}

				numBeats = Integer.parseInt(split[0]);
			}
			else
			{
				numBeats = Integer.parseInt(thro);
			}

			return MultiHandThro.HandSpecificThro.get(toHand, numBeats);
		}

		private boolean isPass()
		{
			return thro.contains("p");
		}
	}
}
