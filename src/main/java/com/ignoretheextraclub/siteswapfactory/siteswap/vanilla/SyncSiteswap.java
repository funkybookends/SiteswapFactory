package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.MultiHandThrosToStringConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;
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

		for (int i = 0; i < landings.length; i++)
		{
			final MultiHandThro thro = (MultiHandThro) looper.next();

			for (int handIndex = 0; handIndex < numHands; handIndex++)
			{
				final MultiHandThro.HandSpecificThro throwForHand = thro.getThrowForHand(handIndex);
				final int landingBeat = i + thro.getNumBeats();

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
