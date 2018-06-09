package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;

public class GeneralCircuitToTwoHandedSiteswapConstructor implements SiteswapConstructor<TwoHandedVanillaSiteswap>
{
	private static GeneralCircuitToTwoHandedSiteswapConstructor INSTANCE;

	private GeneralCircuitToTwoHandedSiteswapConstructor()
	{
	    // Singleton
	}

	public static GeneralCircuitToTwoHandedSiteswapConstructor get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new GeneralCircuitToTwoHandedSiteswapConstructor();
	    }
	    return INSTANCE;
	}

	@Override
	public TwoHandedVanillaSiteswap apply(final SiteswapRequest siteswapRequest)
	{
		return siteswapRequest.getReducer()
			.andThen(siteswapRequest.getStartingStrategy())
			.andThen(TwoHandedVanillaSiteswap::new)
			.apply((GeneralCircuit) siteswapRequest.getConstructor());
	}

	@Override
	public boolean accepts(final Object object)
	{
		return GeneralCircuit.class.isInstance(object);
	}
}
