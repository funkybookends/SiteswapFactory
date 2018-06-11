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
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralCircuitToTwoHandedVanillaSiteswapConstructorTest
{
	private static final VanillaThro _1 = get(1);
	private static final VanillaThro _2 = get(2);
	private static final VanillaThro _3 = get(3);
	private static final VanillaThro _4 = get(4);
	private static final VanillaThro _5 = get(5);

	private SiteswapConstructor<TwoHandedVanillaSiteswap> constructor = GeneralCircuitToTwoHandedSiteswapConstructor.get();

	@Test
	public void test3BallCascade() throws Exception
	{
		assertThat(constructor.get(GeneralPath.from(XXX__, _3).toGeneralCircuit()).toString()).isEqualTo("3");
	}
}