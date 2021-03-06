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

package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_X_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 09/10/17.
 */
public class StateSearcherTest
{
	private static final Logger LOG = LoggerFactory.getLogger(StateSearcherTest.class);

	@Test
	public void name() throws Exception
	{
		final Set<State> startingStates = new LinkedHashSet<>();

		startingStates.add(XXX__);
		startingStates.add(X_X_X);
		startingStates.add(__XXX);

		final StateSearcher<TwoHandedVanillaSiteswap> twoHandedSiteswapStateSearcher = new StateSearcher<>(startingStates, 5, (path) -> true, (circuit) -> true, GeneralCircuitToTwoHandedSiteswapConstructor.get(), null);

		final long count = twoHandedSiteswapStateSearcher.generate()
			.count();

		assertThat(count).isEqualTo(185);
	}
}