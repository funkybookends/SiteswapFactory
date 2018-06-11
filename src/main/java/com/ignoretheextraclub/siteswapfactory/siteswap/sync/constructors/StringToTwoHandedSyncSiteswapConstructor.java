/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
