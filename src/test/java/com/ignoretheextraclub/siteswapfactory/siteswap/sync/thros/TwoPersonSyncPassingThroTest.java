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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros;

import java.util.Iterator;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.TwoPersonSyncPassingThro;

import static org.assertj.core.api.Assertions.assertThat;

public class TwoPersonSyncPassingThroTest
{

	@Before
	public void setUp() throws Exception
	{


	}

	@Test
	public void testStartingHands() throws Exception
	{
		assertThat(new PassingSiteswap(GeneralPath.from(new MultiHandedSyncState(new long[]{3, 3}), multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).toString()).isEqualTo("<2|2>");
	}

	public static MultiHandThro multiHandThro(final int... hand_beat_pairs)
	{
		final Iterator<Integer> iterator = IteratorUtils.arrayIterator(hand_beat_pairs);

		final MultiHandThro.HandSpecificThro[] handSpecificThros = new MultiHandThro.HandSpecificThro[hand_beat_pairs.length / 2];

		int i = 0;

		while (i < handSpecificThros.length)
		{
			handSpecificThros[i] = MultiHandThro.HandSpecificThro.get(iterator.next(), iterator.next());
			i++;
		}

		return new TwoPersonSyncPassingThro(handSpecificThros);
	}
}