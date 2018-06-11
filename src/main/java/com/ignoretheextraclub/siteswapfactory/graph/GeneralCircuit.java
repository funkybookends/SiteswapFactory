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

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.collections4.iterators.ArrayIterator;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Represents a loop through a graph. Can handle graphs where there are multiple
 * edges connecting any two given nodes.
 */
public class GeneralCircuit
{
	/**
	 * The starting state i.e. node
	 */
	private final State startingState;

	/**
	 * The throws i.e. edges through the graph.
	 */
	private final Thro[] thros;

	/**
	 * Create a {@link GeneralCircuit} using the starting state and the provided throws.
	 *
	 * @param startingState The first state i.e. node
	 * @param thros         The throws i.e. edges through the graph.
	 *
	 * @throws IllegalArgumentException if the path is not a loop.
	 */
	public GeneralCircuit(final State startingState, final Thro... thros)
	{
		Objects.requireNonNull(startingState, "startingState cannot be null");

		State state = startingState;

		for (final Thro thro : thros)
		{
			state = state.thro(thro);
		}

		if (!state.equals(startingState))
		{
			throw new IllegalArgumentException("Thros do not create a circuit: " + startingState.toString() + ", " + Arrays.toString(thros));
		}

		this.startingState = startingState;
		this.thros = thros;
	}

	/**
	 * Returns the starting state of this General Circuit
	 * @return The starting state.
	 */
	public State getStartingState()
	{
		return startingState;
	}

	/**
	 * Returns all the states in this general circuit.
	 * @return All the nodes visited by this circuit.
	 */
	public State[] getAllStates()
	{
		final State[] states = new State[size()];

		states[0] = startingState;

		for (int i = 1; i < size(); i++)
		{
			states[i] = states[i - 1].thro(thros[i - 1]);
		}

		return states;
	}

	/**
	 * 
	 * @return
	 */
	public Thro[] getThros()
	{
		return thros;
	}

	public GeneralCircuit rotate()
	{
		final Thro[] thros = new Thro[this.thros.length];

		System.arraycopy(this.thros, 1, thros, 0, this.thros.length - 1);
		thros[this.thros.length - 1] = this.thros[0];

		return new GeneralCircuit(startingState.thro(this.thros[0]), thros);
	}

	public GeneralCircuit[] getRotationsArray()
	{
		final GeneralCircuit[] rotations = new GeneralCircuit[size()];

		rotations[0] = this;

		for (int i = 1; i < size(); i++)
		{
			rotations[i] = rotations[i - 1].rotate();
		}

		return rotations;
	}

	public int size()
	{
		return thros.length;
	}

	public Iterator<GeneralCircuit> getRotationIteator()
	{
		return new ArrayIterator<>(getRotationsArray());
	}

	public Stream<GeneralCircuit> getRotationStream()
	{
		return Stream.of(getRotationsArray());
	}

	public GeneralPath toGeneralPath()
	{
		return GeneralPath.from(startingState, thros);
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final GeneralCircuit that = (GeneralCircuit) o;

		if (!startingState.equals(that.startingState)) return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		return Arrays.equals(thros, that.thros);
	}

	@Override
	public int hashCode()
	{
		int result = startingState.hashCode();
		result = 31 * result + Arrays.hashCode(thros);
		return result;
	}

	@Override
	public String toString()
	{
		return "GeneralCircuit{" + startingState + ", " + Arrays.toString(thros) + '}';
	}
}
