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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.MultiHandThrosToStringConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

public class SyncSiteswap implements Siteswap
{
	public static final String TYPE = "Synchronous Siteswap";

	protected final GeneralCircuit generalCircuit;

	public SyncSiteswap(final GeneralCircuit generalCircuit)
	{
		if (!(generalCircuit.getStartingState() instanceof MultiHandedSyncState))
		{
			throw new InvalidSiteswapException("Must be a circuit of type" + MultiHandedSyncState.class.getCanonicalName());
		}
		this.generalCircuit = generalCircuit;
	}

	@Override
	public int getNumJugglers()
	{
		throw new UnsupportedOperationException("Not defined for this type of pattern.");
	}

	@Override
	public int getNumHands()
	{
		return ((MultiHandThro) generalCircuit.getThros()[0]).getNumHands();
	}

	@Override
	public GeneralCircuit getGeneralCircuit()
	{
		return generalCircuit;
	}

	@Override
	public int getStartingNumberOfObjects(final int forHand) throws IndexOutOfBoundsException
	{
		return getHands()[forHand];
	}

	protected int[] getHands()
	{
		final int numHands = getNumHands();

		final boolean[][] landings = new boolean[getPeriod() + getHighestThro().getNumBeats()][numHands];

		final ArrayLoopingIterator<Thro> looper = new ArrayLoopingIterator<>(getThrows());

		for (int beat = 0; beat < landings.length; beat++)
		{
			final MultiHandThro thro = (MultiHandThro) looper.next();

			for (int handIndex = 0; handIndex < numHands; handIndex++)
			{
				final MultiHandThro.HandSpecificThro throwForHand = thro.getThrowForHand(handIndex);
				final int landingBeat = beat + throwForHand.getNumBeats();

				if (landingBeat < landings.length)
				{
					landings[landingBeat][throwForHand.getToHand()] = true;
				}
			}
		}

		int seenObjects = 0;
		int landingPosition = 0;

		final int[] hands = new int[numHands];
		final int numObjects = getNumObjects();

		while (seenObjects < numObjects)
		{
			for (int handIndex = 0; handIndex < numHands; handIndex++)
			{
				if (!landings[landingPosition][handIndex])
				{
					hands[landingPosition % numHands]++;
					seenObjects++;
				}
			}
			landingPosition++;
		}
		return hands;
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

	@Override
	public boolean same(final Siteswap other)
	{
		return generalCircuit.getRotationStream().anyMatch(rotation -> rotation.equals(other));
	}

	@Override
	public String toString()
	{
		return MultiHandThrosToStringConverter.get().apply(this.getThrows());
	}
}
