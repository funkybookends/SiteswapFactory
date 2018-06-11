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

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;

/**
 * Joins {@link GeneralPath}s and {@link GeneralCircuit}s together ensuring transitions are dealt with.
 *
 * @author Caspar Nonclercq
 */
public interface StateJoiner
{
	/**
	 * Returns a state array that contains the first {@code State[]), followed by the second {@code State[]} with transition throws if needed
	 * and the transition throws to return to the first state.
	 *
	 * @param first  The first sequence to include
	 * @param second The second sequence to include
	 * @return A full loop
	 * @throws TransitionException if they cannot be joined, or if no join was found.
	 */
	GeneralPath connectPath(GeneralPath first, GeneralPath second) throws TransitionException;

	/**
	 * Joins the two paths on both ends, creating a general circuit
	 *
	 * @param first  The first path
	 * @param second The second path
	 * @return The general circuit containing both paths.
	 * @throws TransitionException
	 */
	GeneralCircuit joinToCircuit(GeneralPath first, GeneralPath second) throws TransitionException;

	/**
	 * Joins the first general circuit to the second providing the intermediate throws that join
	 * the first to the second, and the throws that join the second to the first. Neither first
	 * nor second will be rotated, and the result will begin with the first.
	 *
	 * @param first  The first circuit
	 * @param second The second circuit
	 * @return The general circuit beginning with the first, connecting to the second, then the second and connecting to the first.
	 */
	default GeneralCircuit joinExactly(GeneralCircuit first, GeneralCircuit second)
	{
		return joinToCircuit(first.toGeneralPath(), second.toGeneralPath());
	}

	/**
	 * Joins the first circuit to the second where the resultant circuit is the shortest possible,
	 * or one of the shortest possible.
	 *
	 * @param first  The first circuit
	 * @param second The second circuit
	 * @return The shortest general circuit that contains the first and the second as sub paths.
	 */
	GeneralCircuit joinShortest(GeneralCircuit first, GeneralCircuit second);
}
