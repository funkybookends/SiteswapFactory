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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing;

import java.util.Arrays;
import java.util.Stack;
import java.util.function.BiFunction;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandSyncStateBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

public class MultiHandThrosAndNumObjectsToStartingStateConverter implements BiFunction<MultiHandThro[], Integer, MultiHandedSyncState>
{
	private static final int MAX_LENGTH = 100;

	private static MultiHandThrosAndNumObjectsToStartingStateConverter INSTANCE;

	private MultiHandThrosAndNumObjectsToStartingStateConverter()
	{
	    // Singleton
	}

	public static MultiHandThrosAndNumObjectsToStartingStateConverter get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new MultiHandThrosAndNumObjectsToStartingStateConverter();
	    }
	    return INSTANCE;
	}

	@Override
	public MultiHandedSyncState apply(final MultiHandThro[] multiHandThros, final Integer numObjects)
	{
		final MultiHandSyncStateBuilder builder = new MultiHandSyncStateBuilder(multiHandThros[0].getNumHands());

		final ArrayLoopingIterator<MultiHandThro> looper = new ArrayLoopingIterator<>(multiHandThros);

		final Stack<MultiHandThro> throStack = new Stack<>();

		while (builder.getNumObjects() != numObjects)
		{
			if (throStack.size() > MAX_LENGTH)
			{
				throw new InvalidSiteswapException(String.format("After %s throws of %s still had not thrown %s objects",
					throStack.size(), Arrays.toString(multiHandThros), numObjects));
			}

			throStack.push(looper.peek());
			builder.thenThrow(looper.next());
		}

		MultiHandedSyncState state = builder.getState();

		while (!throStack.isEmpty())
		{
			state = state.undo(throStack.pop());
		}

		return state;
	}

	public static MultiHandedSyncState getFirstState(final MultiHandThro[] multiHandThros, final Integer numObjects)
	{
		return get().apply(multiHandThros, numObjects);
	}
}
