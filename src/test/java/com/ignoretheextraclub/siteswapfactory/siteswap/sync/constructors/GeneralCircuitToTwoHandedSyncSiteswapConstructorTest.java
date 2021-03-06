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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.SyncSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralCircuitToTwoHandedSyncSiteswapConstructorTest
{
	private SiteswapConstructor<SyncSiteswap> siteswapConstructor = GeneralCircuitToTwoHandedSyncSiteswapConstructor.get();

	@Test
	public void test4BallFountain() throws Exception
	{
		assertThat(siteswapConstructor.get(GeneralPath.from(new MultiHandedSyncState(3, 3)
			, multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).toString()).isEqualTo("(4,4)");
	}
}