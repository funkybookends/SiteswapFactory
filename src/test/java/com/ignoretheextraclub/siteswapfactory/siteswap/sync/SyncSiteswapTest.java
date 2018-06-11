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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class SyncSiteswapTest
{

	@Before
	public void setUp() throws Exception
	{


	}

	@Test
	public void testStartingHands() throws Exception
	{
		assertThat(new SyncSiteswap(GeneralPath.from(new MultiHandedSyncState(new long[]{3, 3}), multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).getStartingNumberOfObjects(0)).isEqualTo(2);
		assertThat(new SyncSiteswap(GeneralPath.from(new MultiHandedSyncState(new long[]{3, 3}), multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).getStartingNumberOfObjects(1)).isEqualTo(2);
	}
}