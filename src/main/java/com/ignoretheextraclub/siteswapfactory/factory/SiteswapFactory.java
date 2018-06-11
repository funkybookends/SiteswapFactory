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

package com.ignoretheextraclub.siteswapfactory.factory;

import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.impl.SiteswapFactoryImpl;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface SiteswapFactory extends SiteswapConstructor<Siteswap>
{
	/**
	 * Gets all the {@link Siteswap}s, in the same order as the {@link SiteswapFactoryImpl#constructors} could construct.
	 * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
	 * be shorter than the {@link SiteswapFactoryImpl#constructors} list, or empty.
	 *
	 * @param siteswapRequest The siteswapRequest to construct the siteswap with.
	 *
	 * @return A modifiable list of {@link Siteswap}s.
	 */
	List<Siteswap> getAll(SiteswapRequest siteswapRequest);

	/**
	 * Gets all the {@link Siteswap}s, in the same order as the {@link SiteswapFactoryImpl#constructors} could construct.
	 * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
	 * be shorter than the {@link SiteswapFactoryImpl#constructors} list, or empty.
	 *
	 * @param object The object to use to construct the siteswap with.
	 *
	 * @return A modifiable list of {@link Siteswap}s.
	 */
	default List<Siteswap> getAll(Object object)
	{
		return getAll(new SiteswapRequest(object));
	}
}
