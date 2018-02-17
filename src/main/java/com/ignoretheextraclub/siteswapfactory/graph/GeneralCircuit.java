package com.ignoretheextraclub.siteswapfactory.graph;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.collections4.iterators.ArrayIterator;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

public class GeneralCircuit
{
	private final State startingState;
	private final Thro[] thros;

	public GeneralCircuit(final State startingState, final Thro[] thros)
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

	public State getStartingState()
	{
		return startingState;
	}

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
		return "GeneralCircuit{" + startingState +", " + Arrays.toString(thros) + '}';
	}
}
