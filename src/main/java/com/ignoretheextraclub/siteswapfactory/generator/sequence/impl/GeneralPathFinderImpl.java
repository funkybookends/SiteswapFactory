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
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.ignoretheextraclub.siteswapfactory.generator.sequence.GeneralPathFinder;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

public class GeneralPathFinderImpl implements GeneralPathFinder
{
	private final int startingDepth;
	private final int maxDepth;
	private final State startingState;
	private final Predicate<GeneralPath> predicate;

	public GeneralPathFinderImpl(final int startingDepth,
	                             final int maxDepth,
	                             final State startingState,
	                             final Predicate<GeneralPath> predicate)
	{
		this.predicate = predicate;
		this.maxDepth = maxDepth;
		this.startingState = startingState;
		this.startingDepth = startingDepth;
	}

	/**
	 * Create a new stream with the contents of the path
	 * @return
	 */
	@Override
	public Stream<GeneralPath> stream()
	{
		return StreamSupport.stream(spliterator(), true);
	}

	@Override
	public Iterator<GeneralPath> iterator()
	{
		return new GeneralPathIterator(startingDepth, maxDepth, startingState, predicate);
	}

	@Override
	public Spliterator<GeneralPath> spliterator()
	{
		return new GeneralPathSpliterator(startingDepth, maxDepth, startingState, predicate);
	}
}
