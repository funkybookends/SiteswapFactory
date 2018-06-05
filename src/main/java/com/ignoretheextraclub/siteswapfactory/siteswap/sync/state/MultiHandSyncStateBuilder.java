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
