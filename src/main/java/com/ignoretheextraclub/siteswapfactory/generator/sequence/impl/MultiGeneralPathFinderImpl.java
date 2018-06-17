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
 *
 *
 */

package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import java.util.Spliterator;
import java.util.function.Consumer;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;

public class MultiGeneralPathFinderImpl implements Spliterator<GeneralPath>
{

	@Override
	public boolean tryAdvance(final Consumer<? super GeneralPath> action)
	{
		throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
	}

	@Override
	public Spliterator<GeneralPath> trySplit()
	{
		throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
	}

	@Override
	public long estimateSize()
	{
		throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
	}

	@Override
	public int characteristics()
	{
		throw new UnsupportedOperationException("This method has not been implemented yet"); // TODO implement
	}
}
