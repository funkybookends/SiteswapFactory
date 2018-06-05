package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

public class GeneralCircuitToFourHandedSiteswapConstructor implements SiteswapConstructor<FourHandedSiteswap>
{
	private static GeneralCircuitToFourHandedSiteswapConstructor INSTANCE;

	private GeneralCircuitToFourHandedSiteswapConstructor()
	{
	    // Singleton
	}

	public static GeneralCircuitToFourHandedSiteswapConstructor get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new GeneralCircuitToFourHandedSiteswapConstructor();
	    }
	    return INSTANCE;
	}

	@Override
	public FourHandedSiteswap apply(final SiteswapRequest siteswapRequest)
	{
		return siteswapRequest.getReducer()
			.andThen(siteswapRequest.getStartingStrategy())
			.andThen(FourHandedSiteswap::new)
			.apply((GeneralCircuit) siteswapRequest.getConstructor());
	}

	@Override
	public boolean accepts(final Object object)
	{
		return GeneralCircuit.class.isInstance(object);
	}
}
