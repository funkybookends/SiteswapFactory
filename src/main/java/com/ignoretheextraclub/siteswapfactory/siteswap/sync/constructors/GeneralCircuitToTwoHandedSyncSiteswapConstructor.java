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

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.SyncSiteswap;

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
