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

package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors.GeneralCircuitToTwoHandedSyncSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors.StringToTwoHandedSyncSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

/**
 * A siteswap factory dedicated to creating solo siteswaps.
 */
public final class SoloJugglingSiteswapFactory extends SiteswapFactoryImpl
{
	private static final List<SiteswapConstructor<? extends Siteswap>> SOLO_JUGGLING_SITESWAP_CONSTRUCTORS =
		Arrays.asList(
			StringToTwoHandedSiteswapConstructor.get(),
			GeneralCircuitToTwoHandedSiteswapConstructor.get(),
			StringToTwoHandedSyncSiteswapConstructor.get(),
			GeneralCircuitToTwoHandedSyncSiteswapConstructor.get()
		);

	private static final SoloJugglingSiteswapFactory DEFAULT = new SoloJugglingSiteswapFactory();

	private SoloJugglingSiteswapFactory()
	{
		super(SOLO_JUGGLING_SITESWAP_CONSTRUCTORS);
	}

	public SoloJugglingSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(SOLO_JUGGLING_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static SoloJugglingSiteswapFactory getDefault()
	{
		return getDefault();
	}

	/**
	 * Creates a solo Siteswap from the object provided.
	 * @param object The object to create a solo siteswap with.
	 * @return The solo siteswap.
	 */
	public static Siteswap getSoloSiteswap(final Object object)
	{
		return DEFAULT.get(object);
	}


	/**
	 * Creates a solo siteswap from the siteswap request provided.
	 * @param siteswapRequest The siteswap request
	 * @return The solo siteswap.
	 */
	public static Siteswap getSoloSiteswap(final SiteswapRequest siteswapRequest)
	{
		return DEFAULT.apply(siteswapRequest);
	}
}
