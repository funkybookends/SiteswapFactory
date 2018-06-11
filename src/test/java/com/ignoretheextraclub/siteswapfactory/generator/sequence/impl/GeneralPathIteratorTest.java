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

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.NoThroHigherThanPredicate;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_X___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._0;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._2;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._3;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._4;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._5;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralPathIteratorTest
{

	@Test
	public void test3Balls()
	{
		final GeneralPathIterator routeIterator = new GeneralPathIterator(1, 6, XXX__, new NoThroHigherThanPredicate(get(5)));

		assertThat(routeIterator)
			.contains(GeneralPath.from(XXX__, _3))
			.contains(GeneralPath.from(XXX__, _4))
			.contains(GeneralPath.from(XXX__, _5))
			.contains(GeneralPath.from(XXX__, _3, _3))
			.contains(GeneralPath.from(XXX__, _4, _2))
			.contains(GeneralPath.from(XXX__, _5, _5, _5, _0, _0))
			.contains(GeneralPath.from(XXX__, _5, _5, _5, _0, _0, _3))
			.contains(GeneralPath.from(XXX__, _5, _5, _5, _0, _0, _4))
			.contains(GeneralPath.from(XXX__, _5, _5, _5, _0, _0, _5))
			.hasSize(706)
			;
	}

	@Test
	public void test5() throws Exception
	{
		final GeneralPathIterator routeIterator = new GeneralPathIterator(1, 3, XXXX_X___, (generalPath -> true));

		assertThat(routeIterator)
			.contains(GeneralPath.from(XXXX_X___, get(7), get(7)))
		;
	}

	// TODO add more
}