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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.MultiHandThrosToPassingStringConverter;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.SyncSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;

public class PassingSiteswap extends SyncSiteswap
{
	public static final String TYPE = "Synchronous Passing Siteswap";

	public PassingSiteswap(final GeneralCircuit generalCircuit)
	{
		super(generalCircuit);
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

	@Override
	public int getNumJugglers()
	{
		return ((MultiHandedSyncState) generalCircuit.getStartingState()).getNumHands();
	}

	@Override
	public String toString()
	{
		return MultiHandThrosToPassingStringConverter.get().apply(this.getThrows());
	}
}
