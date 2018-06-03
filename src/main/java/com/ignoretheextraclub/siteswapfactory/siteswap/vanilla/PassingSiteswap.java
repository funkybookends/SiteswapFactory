package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.MultiHandThrosToPassingStringConverter;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncState;

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
	public String toString()
	{
		return MultiHandThrosToPassingStringConverter.get().apply(this.getThrows());
	}
}
