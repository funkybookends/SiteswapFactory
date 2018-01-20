package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Created by caspar on 06/01/18.
 */
public class MultiHandThro implements Thro
{
	private final HandSpecificThro[] thros;

	public MultiHandThro(final HandSpecificThro[] thros)
	{
		if (thros == null || thros.length < 1)
		{
			throw new IllegalArgumentException("Must contain at least one throw");
		}

		final int maxHand = thros.length;

		Arrays.stream(thros)
			.mapToInt(HandSpecificThro::getToHand)
			.forEach(toHand ->
			{
				if (toHand >= maxHand)
				{
					throw new IllegalArgumentException("Thros contain throw to hand out of range");
				}
			});

		this.thros = thros;
	}

	@Override
	public int getNumBeats()
	{
		return Arrays.stream(thros)
			.mapToInt(HandSpecificThro::getNumBeats)
			.max()
			.orElse(0);
	}

	@Override
	public int getNumObjectsThrown()
	{
		return Arrays.stream(thros)
			.mapToInt(HandSpecificThro::numObjects)
			.sum();
	}

	@Override
	public int compareTo(final Object other)
	{
		return Comparator.comparing(Thro::getNumBeats).compare(this, (Thro) other);
	}

	@Override
	public String toString()
	{
		if (thros.length == 1)
		{
			return String.valueOf(thros[0].getNumBeats());
		}
		else if (thros.length == 2)
		{
			return String.format("(%s%s,%s%s)", thros[0].numBeats, thros[0].getToHand() == 0 ? "" : "x", thros[1].numBeats, thros[1].getToHand() == 1 ? "" : "x");
		}
		else
		{
			return Arrays.stream(thros)
				.map(thro -> "" + thro.getNumBeats() + ":" + thro.toHand)
				.collect(Collectors.joining(",", "(", ")"));
		}
	}

	public int getNumHands()
	{
		return thros.length;
	}

	public HandSpecificThro getThrowForHand(final int hand)
	{
		return thros[hand];
	}

	public static class HandSpecificThro
	{
		private static final HandSpecificThro[][] CACHE = new HandSpecificThro[8][15];

		private final int toHand;
		private final int numBeats;

		private HandSpecificThro(final int toHand,
		                         final int numBeats)
		{
			if (numBeats < 0 || toHand < 0)
			{
				throw new IllegalArgumentException("Hand and beats cant be less than 0");
			}
			this.toHand = toHand;
			this.numBeats = numBeats;
		}

		public static HandSpecificThro get(final int toHand, final int numBeats)
		{
			try
			{
				if (CACHE[toHand][numBeats] == null)
				{
					CACHE[toHand][numBeats] = new HandSpecificThro(toHand, numBeats);
				}
				return CACHE[toHand][numBeats];
			}
			catch (IndexOutOfBoundsException beyondCache)
			{
				return new HandSpecificThro(toHand, numBeats);
			}
		}

		public int getToHand()
		{
			return toHand;
		}

		public int getNumBeats()
		{
			return numBeats;
		}

		public int numObjects()
		{
			return numBeats == 0 ? 0 : 1;
		}

		@Override
		public String toString()
		{
			return "HandSpecificThro{" +
				"toHand=" + toHand +
				", numBeats=" + numBeats +
				'}';
		}
	}
}
