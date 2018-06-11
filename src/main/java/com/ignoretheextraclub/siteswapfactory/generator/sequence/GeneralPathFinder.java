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

package com.ignoretheextraclub.siteswapfactory.generator.sequence;

import java.util.Iterator;
import java.util.stream.Stream;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;

/**
 * Finds all {@link GeneralPath}s.
 */
public interface GeneralPathFinder extends Iterable<GeneralPath>
{
	/**
	 * Create a new stream of general paths.
	 * @return A new stream of general paths
	 */
	Stream<GeneralPath> stream();

	/**
	 * Returns an iterator of general paths.
	 * @return A new iterator of general paths.
	 */
	Iterator<GeneralPath> iterator();
}
