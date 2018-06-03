package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncState;

public class TwoHandedSyncSiteswap extends SyncSiteswap
{
	public static final String TWO_HANDED_SYNC_SITESWAP = "Two Handed Sync Siteswap";

	private static final int NUM_JUGGLERS = 1;

	public TwoHandedSyncSiteswap(final GeneralCircuit generalCircuit)
	{
		super(generalCircuit);

		if (((MultiHandedSyncState) generalCircuit.getStartingState()).getNumHands() != 2)
		{
			throw new IllegalArgumentException("Not a two handed general circuit");
		}
	}

	@Override
	public int getNumJugglers()
	{
		return NUM_JUGGLERS;
	}

	@Override
	public String getType()
	{
		return TWO_HANDED_SYNC_SITESWAP;
	}

	@Override
	public String toString()
	{
		return Arrays.stream(generalCircuit.getThros()).map(Object::toString).collect(Collectors.joining(""));
	}
}
