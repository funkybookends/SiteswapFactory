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

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXX___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro.get;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralCircuitToFourHandedSiteswapConstructorTest
{
	private SiteswapConstructor<FourHandedSiteswap> siteswapConstructor = GeneralCircuitToFourHandedSiteswapConstructor.get();

	@Test
	public void nineSevenFive() throws Exception
	{
		assertThat(siteswapConstructor.apply(new SiteswapRequest(GeneralPath.from(XXXXXXX___, get(9), get(7), get(5)).toGeneralCircuit())).toString()).isEqualTo("975");
	}
}