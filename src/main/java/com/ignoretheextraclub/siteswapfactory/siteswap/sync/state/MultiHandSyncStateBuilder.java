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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class MultiHandSyncStateBuilder
{
	private long[] state;

	public MultiHandSyncStateBuilder(final int numHands)
	{
		state = new long[numHands];
	}

	public MultiHandSyncStateBuilder thenThrow(final MultiHandThro multiHandThro)
	{
		if (multiHandThro.getNumHands() != state.length)
		{
			throw new BadThrowException(String.format("Expected throw with %s hands, not %s", state.length, multiHandThro.getNumHands()));
		}

		for (int hand = 0; hand < state.length; hand++)
		{
			final MultiHandThro.HandSpecificThro throwForHand = multiHandThro.getThrowForHand(hand);

			final int toHand = throwForHand.getToHand();
			final int beats = throwForHand.getNumBeats();

			state[toHand] |= (1 << beats);
		}

		for (int hand = 0; hand < state.length; hand++)
		{
			state[hand] >>= 1;
		}

		return this;
	}

	public int getNumObjects()
	{
		return MultiHandedSyncState.calculateNumObjects(this.state);
	}

	public MultiHandedSyncState getState()
	{
		return new MultiHandedSyncState(state);
	}
}
