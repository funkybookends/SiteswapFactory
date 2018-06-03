package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.SyncSiteswap;

public class GeneralCircuitToTwoHandedSyncSiteswapConstructor implements SiteswapConstructor<SyncSiteswap>
{
	private static GeneralCircuitToTwoHandedSyncSiteswapConstructor INSTANCE;

	private GeneralCircuitToTwoHandedSyncSiteswapConstructor()
	{
	    // Singleton
	}

	public static GeneralCircuitToTwoHandedSyncSiteswapConstructor get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new GeneralCircuitToTwoHandedSyncSiteswapConstructor();
	    }
	    return INSTANCE;
	}

	@Override
	public SyncSiteswap apply(final SiteswapRequest siteswapRequest)
	{
		return siteswapRequest.getReducer()
			.andThen(siteswapRequest.getStartingStrategy())
			.andThen(SyncSiteswap::new)
			.apply((GeneralCircuit) siteswapRequest.getConstructor());
	}

	@Override
	public boolean accepts(final Object object)
	{
		return GeneralCircuit.class.isInstance(object);
	}
}
