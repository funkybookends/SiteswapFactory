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
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToFourHandedSiteswapConstructor;

/**
 * A factory just for creating {@link FourHandedSiteswap}.
 */
public class FourHandedSiteswapFactory extends SiteswapFactoryImpl
{
	/**
	 * A list of {@link TwoHandedVanillaSiteswap} {@link SiteswapConstructor}s.
	 */
	private static final List<SiteswapConstructor<? extends Siteswap>> FOUR_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
		StringToFourHandedSiteswapConstructor.get(),
		GeneralCircuitToFourHandedSiteswapConstructor.get()
	);

	private static final FourHandedSiteswapFactory DEFAULT = new FourHandedSiteswapFactory();

	private FourHandedSiteswapFactory()
	{
		super(FOUR_HANDED_SITESWAP_CONSTRUCTORS);
	}

	public FourHandedSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(FOUR_HANDED_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static FourHandedSiteswapFactory getDefault()
	{
		return DEFAULT;
	}

	/**
	 * Creates a new {@link FourHandedSiteswap} from the object provided.
	 * @param siteswap The object to use to construct the siteswap.
	 * @return A {@link FourHandedSiteswap} created from the constructor.
	 */
	public static FourHandedSiteswap getFourHandedSiteswap(final Object siteswap)
	{
		return (FourHandedSiteswap) DEFAULT.get(siteswap);
	}

	/**
	 * Creates a new {@link FourHandedSiteswap} from the {@link SiteswapRequest} provided.
	 * @param siteswapRequest The siteswap request object.
	 * @return A {@link FourHandedSiteswap} created from the constructor.
	 */
	public static FourHandedSiteswap getFourHandedSiteswap(final SiteswapRequest siteswapRequest)
	{
		return (FourHandedSiteswap) DEFAULT.apply(siteswapRequest);
	}

}
