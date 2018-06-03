package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;

public class GeneralCircuitToPassingSiteswapConstructor implements SiteswapConstructor<PassingSiteswap>
{
	private static GeneralCircuitToPassingSiteswapConstructor INSTANCE;

	private GeneralCircuitToPassingSiteswapConstructor()
	{
	    // Singleton
	}

	public static GeneralCircuitToPassingSiteswapConstructor get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new GeneralCircuitToPassingSiteswapConstructor();
	    }
	    return INSTANCE;
	}

	@Override
	public PassingSiteswap apply(final SiteswapRequest siteswapRequest)
	{
		return siteswapRequest.getReducer()
			.andThen(siteswapRequest.getStartingStrategy())
			.andThen(PassingSiteswap::new)
			.apply((GeneralCircuit) siteswapRequest.getConstructor());
	}

	@Override
	public boolean accepts(final Object object)
	{
		return GeneralCircuit.class.isInstance(object);
	}
}
