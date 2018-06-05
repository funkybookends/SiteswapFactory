package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing.MultiHandThrosAndNumObjectsToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing.MultiHandThrosToNumObjectsConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing.PassingStringToMultiHandThros;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;

public class StringToPassingSiteswapConstructor implements SiteswapConstructor<PassingSiteswap>
{
	private static StringToPassingSiteswapConstructor INSTANCE;

	private StringToPassingSiteswapConstructor()
	{
	    // Singleton
	}

	public static StringToPassingSiteswapConstructor get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new StringToPassingSiteswapConstructor();
	    }
	    return INSTANCE;
	}

	@Override
	public PassingSiteswap apply(final SiteswapRequest siteswapRequest)
	{
		final String siteswap = siteswapRequest.getConstructor().toString();
		final MultiHandThro[] multiHandThros = siteswapRequest.getReducer().reduce(PassingStringToMultiHandThros.convert(siteswap));
		final Integer numObjects = MultiHandThrosToNumObjectsConverter.getNumObjects(multiHandThros);
		final MultiHandedSyncState multiHandedSyncState = MultiHandThrosAndNumObjectsToStartingStateConverter.getFirstState(multiHandThros, numObjects);
		final GeneralPath generalPath = GeneralPath.from(multiHandedSyncState, multiHandThros);
		final GeneralCircuit generalCircuit = generalPath.toGeneralCircuit();
		return GeneralCircuitToPassingSiteswapConstructor.get().apply(siteswapRequest.toBuilder().createSiteswapRequest(generalCircuit));
	}

	@Override
	public boolean accepts(final Object object)
	{
		return String.class.isInstance(object);
	}
}
