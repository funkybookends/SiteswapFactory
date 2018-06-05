package com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing.MultiHandThrosAndNumObjectsToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing.MultiHandThrosToNumObjectsConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.SyncTwoHandedVanillaStringToMultiHandThrosConverter;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.SyncSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.TwoHandedSyncSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class StringToTwoHandedSyncSiteswapConstructor implements SiteswapConstructor<SyncSiteswap>
{
	private static StringToTwoHandedSyncSiteswapConstructor INSTANCE;

	private StringToTwoHandedSyncSiteswapConstructor()
	{
	    // Singleton
	}

	public static StringToTwoHandedSyncSiteswapConstructor get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new StringToTwoHandedSyncSiteswapConstructor();
	    }
	    return INSTANCE;
	}

	@Override
	public SyncSiteswap apply(final SiteswapRequest siteswapRequest)
	{
		final String constructor = siteswapRequest.getConstructor().toString();
		final MultiHandThro[] multiHandThros = SyncTwoHandedVanillaStringToMultiHandThrosConverter.get().apply(constructor);
		final Integer numberOfObjects = MultiHandThrosToNumObjectsConverter.getNumObjects(multiHandThros);
		final MultiHandedSyncState firstState = MultiHandThrosAndNumObjectsToStartingStateConverter.getFirstState(multiHandThros, numberOfObjects);
		final GeneralCircuit generalCircuit = GeneralPath.from(firstState, multiHandThros).toGeneralCircuit();

		return siteswapRequest.getReducer()
			.andThen(siteswapRequest.getStartingStrategy())
			.andThen(TwoHandedSyncSiteswap::new)
			.apply(generalCircuit);
	}

	@Override
	public boolean accepts(final Object object)
	{
		return String.class.isInstance(object);
	}
}
