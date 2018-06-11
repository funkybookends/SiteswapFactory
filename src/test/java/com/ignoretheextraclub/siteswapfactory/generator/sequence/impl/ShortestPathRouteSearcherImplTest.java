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

package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._5;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortestPathRouteSearcherImplTest
{
	private RouteSearcher searcher;

	@Before
	public void setUp() throws Exception
	{
		searcher = new ShortestPathRouteSearcherImpl(10, generalPath -> true);

	}

	@Test
	public void testSimple() throws Exception
	{
		final GeneralPath route = searcher.findRoute(XXX__, __XXX);

		assertThat(route).isEqualTo(GeneralPath.from(XXX__, _5, _5, _5));

		// TODO add more
	}
}