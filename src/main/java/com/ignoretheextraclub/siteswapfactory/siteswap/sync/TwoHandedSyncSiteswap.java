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

import java.util.Arrays;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;

public class TwoHandedSyncSiteswap extends SyncSiteswap
{
	public static final String TWO_HANDED_SYNC_SITESWAP = "Two Handed Sync Siteswap";

	private static final int NUM_JUGGLERS = 1;

	public TwoHandedSyncSiteswap(final GeneralCircuit generalCircuit)
	{
		super(generalCircuit);

		if (((MultiHandedSyncState) generalCircuit.getStartingState()).getNumHands() != 2)
		{
			throw new IllegalArgumentException("Not a two handed general circuit");
		}
	}

	@Override
	public int getNumJugglers()
	{
		return NUM_JUGGLERS;
	}

	@Override
	public String getType()
	{
		return TWO_HANDED_SYNC_SITESWAP;
	}

	@Override
	public String toString()
	{
		return Arrays.stream(generalCircuit.getThros()).map(Object::toString).collect(Collectors.joining(""));
	}
}
