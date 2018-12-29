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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Created by caspar on 06/01/18.
 */
public class MultiHandThro implements Thro
{
	protected final HandSpecificThro[] thros;

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
			return String.format("(%s%s,%s%s)", 2 * thros[0].numBeats, thros[0].getToHand() == 0 ? "" : "x", 2 * thros[1].numBeats, thros[1].getToHand() == 1 ? "" : "x");
		}
		else
		{
			return Arrays.stream(thros)
				.map(thro -> "" + thro.getNumBeats() + ":" + thro.getToHand())
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

	public MultiHandThro onTheOtherSide()
	{
		if (getNumHands() != 2)
		{
			throw new BadThrowException("Can only get on other side when there are two hands");
		}

		final HandSpecificThro[] reversed = new HandSpecificThro[2];

		reversed[0] = thros[1].onTheOtherSide();
		reversed[1] = thros[0].onTheOtherSide();

		return new MultiHandThro(reversed);
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final MultiHandThro that = (MultiHandThro) o;

		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		return Arrays.equals(thros, that.thros);
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(thros);
	}

	public static class HandSpecificThro implements Serializable
	{
		private static final int[] ON_THE_OTHER_SIDE = {1, 0};
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
			catch (final IndexOutOfBoundsException beyondCache)
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

		@Override
		public boolean equals(final Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			final HandSpecificThro that = (HandSpecificThro) o;

			return toHand == that.toHand && numBeats == that.numBeats;
		}

		@Override
		public int hashCode()
		{
			int result = toHand;
			result = 31 * result + numBeats;
			return result;
		}

		public HandSpecificThro onTheOtherSide()
		{
			if (toHand > 1)
			{
				throw new BadThrowException("Cannot get on the other side");
			}
			return get(ON_THE_OTHER_SIDE[toHand], numBeats);
		}
	}
}
