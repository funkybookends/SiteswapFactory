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
