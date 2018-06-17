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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.MultiHandThrosToPassingStringConverter;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.SyncSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

public class PassingSiteswap extends SyncSiteswap
{
	public static final String TYPE = "Synchronous Passing Siteswap";

	public PassingSiteswap(final GeneralCircuit generalCircuit)
	{
		super(generalCircuit);
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

	@Override
	public int getNumJugglers()
	{
		return ((MultiHandedSyncState) generalCircuit.getStartingState()).getNumHands();
	}

	@Override
	public int getNumHands()
	{
		return getNumJugglers() * 2;
	}

	@Override
	public String toString()
	{
		return MultiHandThrosToPassingStringConverter.get().apply(this.getThrows());
	}

	@Override
	protected int[] getHands()
	{
		final int numHands = getNumHands() / 2;

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

		final int[] hands = new int[getNumHands()];

		int numObjectsSeen = 0;
		final int numObjects = getNumObjects();

		for (int beat = 0; beat < landings.length; beat++)
		{
			for (int juggler = 0; juggler < landings[0].length; juggler++)
			{
				if (!landings[beat][juggler])
				{
					final int hand;
					if (beat % 2 == 0)
					{
						hand = juggler;
					}
					else
					{
						hand = juggler + getNumJugglers();
					}
					hands[hand]++;
					numObjectsSeen++;
				}
				if (numObjectsSeen >= numObjects)
				{
					break;
				}
			}
		}
		return hands;
	}

	private static class JugglerBeat
	{
	}
}
