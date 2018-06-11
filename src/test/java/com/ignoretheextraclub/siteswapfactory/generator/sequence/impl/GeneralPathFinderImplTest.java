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

import java.util.Iterator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.NoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._0;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._2;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._3;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._4;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._5;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralPathFinderImplTest
{
	private static final Logger LOG = LoggerFactory.getLogger(GeneralPathFinderImplTest.class);

	@Test
	public void test3() throws Exception
	{
		final GeneralPathFinderImpl generalPaths = new GeneralPathFinderImpl(1, 5, XXX__, new NoThroHigherThanPredicate(VanillaThro.get(7)));

		assertThat(generalPaths.stream()).contains(
			GeneralPath.from(XXX__, _3),
			GeneralPath.from(XXX__, _3, _4, _2),
			GeneralPath.from(XXX__, _3, _5, _5, _5),
			GeneralPath.from(XXX__, _5, _5, _5, _0, _0),
			GeneralPath.from(XXX__, _5)
		);
	}

	@Test
	public void test3Long() throws Exception
	{
		final GeneralPathFinderImpl generalPaths = new GeneralPathFinderImpl(1, 10, XXX__, new NoThroHigherThanPredicate(VanillaThro.get(7)));

		generalPaths.stream().count();
	}

	@Test
	public void testSync4Balls() throws Exception
	{
		final GeneralPathFinderImpl generalPaths = new GeneralPathFinderImpl(1, 5, new MultiHandedSyncState(3, 3),
			new NoThroHigherThanPredicate(MultiHandedSyncStateTest.multiHandThro(0, 3, 1, 3)));

		final Iterator<GeneralPath> iterator = generalPaths.iterator();

		int i = 0;
		int count = 800;

		while (iterator.hasNext() && i++ < count)
		{
			final GeneralPath next = iterator.next();
			LOG.info("{}", next);
		}
	}
}