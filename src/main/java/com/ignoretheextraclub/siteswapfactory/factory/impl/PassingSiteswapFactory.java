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
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToPassingSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToPassingSiteswapConstructor;

public class PassingSiteswapFactory extends SiteswapFactoryImpl
{
	private static final List<SiteswapConstructor<? extends Siteswap>> PASSING_SITESWAP_CONSTRUCTORS = Arrays.asList(
		GeneralCircuitToPassingSiteswapConstructor.get(),
		StringToPassingSiteswapConstructor.get(),
		GeneralCircuitToFourHandedSiteswapConstructor.get(),
		StringToFourHandedSiteswapConstructor.get()
	);

	private static final PassingSiteswapFactory DEFAULT = new PassingSiteswapFactory();

	private PassingSiteswapFactory()
	{
		super(PASSING_SITESWAP_CONSTRUCTORS);
	}

	public PassingSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(PASSING_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static PassingSiteswapFactory getDefault()
	{
		return DEFAULT;
	}

	public static Siteswap getPassingSiteswap(final Object object)
	{
		return DEFAULT.get(object);
	}

	public static Siteswap getPassingSiteswap(final SiteswapRequest siteswapRequest)
	{
		return DEFAULT.apply(siteswapRequest);
	}
}
