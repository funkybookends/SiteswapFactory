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

package com.ignoretheextraclub.siteswapfactory.graph;

import java.util.Stack;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Represents a path through a graph. Unlike a {@link GeneralCircuit} This path
 * does not have to loop, is mutable, and provides easy funcatility to turn into
 * a {@link GeneralCircuit}.
 * <p>
 * The implementation can handle paths where there may be multiple edges
 * connecting nodes.
 */
public class GeneralPath extends Stack<Thro>
{
	private Stack<State> states = new Stack<>();

	/**
	 * Build a new path
	 *
	 * @param startingState
	 */
	public GeneralPath(final State startingState)
	{
		states.push(startingState);
	}

	/**
	 * Returns the first state of the path
	 *
	 * @return The first state
	 */
	public State getStartingState()
	{
		return states.get(0);
	}

	/**
	 * Add a new edge to the path.
	 *
	 * @param thro The new edge
	 *
	 * @return the <code>thro</code> argument.
	 */
	@Override
	public Thro push(final Thro thro)
	{
		states.push(states.lastElement().thro(thro));
		return super.push(thro);
	}

	/**
	 * Remove and return the last element from the path
	 *
	 * @return The last element
	 */
	@Override
	public synchronized Thro pop()
	{
		states.pop();
		return super.pop();
	}

	/**
	 * Returns the last state. Does not modify the path.
	 *
	 * @return The last state.
	 */
	public State getLastState()
	{
		return this.states.lastElement();
	}

	/**
	 * Returns this general path as a {@link GeneralCircuit}.
	 *
	 * @return This path as a general circuit.
	 */
	public GeneralCircuit toGeneralCircuit()
	{
		return new GeneralCircuit(states.get(0), this.toArray(new Thro[this.size()]));
	}

	/**
	 * Returns true if this general path is a valid general circuit
	 *
	 * @return if this is a loop.
	 */
	public boolean isGeneralCircuit()
	{
		return getStartingState().equals(getLastState());
	}

	/**
	 * Returns an array of all the nodes of the path.
	 *
	 * @return An array of states.
	 */
	public State[] getStates()
	{
		return states.toArray(new State[states.size()]);
	}

	/**
	 * A static constructor for creating a general path.
	 *
	 * @param startingState The starting states.
	 * @param thros         An array of throws to be added in order.
	 *
	 * @return The general path represented by the starting state and throws.
	 */
	public static GeneralPath from(final State startingState, final Thro... thros)
	{
		final GeneralPath generalPath = new GeneralPath(startingState);

		for (final Thro thro : thros)
		{
			generalPath.push(thro);
		}

		return generalPath;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		final GeneralPath that = (GeneralPath) o;

		return states.equals(that.states);
	}

	@Override
	public int hashCode()
	{
		int result = super.hashCode();
		result = 31 * result + states.hashCode();
		return result;
	}
}
