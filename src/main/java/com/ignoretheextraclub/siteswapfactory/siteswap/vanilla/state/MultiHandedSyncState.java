package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.iterators.PermutationIterator;
import org.apache.commons.math3.util.Combinations;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

/**
 * Created by caspar on 06/01/18.
 */
public class MultiHandedSyncState implements State
{
	private static final int MAX_THROW = 15;

	private final long[] state;
	private final int numObjects;

	public MultiHandedSyncState(final long... state)
	{
		this.state = state;
		numObjects = calculateNumObjects(state);
	}

	@Override
	public Thro getMaxThrow()
	{
		final MultiHandThro.HandSpecificThro[] handSpecificThros = new MultiHandThro.HandSpecificThro[this.state.length];

		for (int hand = 0; hand < this.state.length; hand++)
		{
			handSpecificThros[hand] = MultiHandThro.HandSpecificThro.get(hand, MAX_THROW);
		}

		return new MultiHandThro(handSpecificThros);
	}

	@Override
	public long excitedness()
	{
		return Arrays.stream(state)
			.mapToInt(value -> (int) value)
			.sum();
	}

	@Override
	public Iterator<Thro> getAvailableThrows()
	{
		final List<MultiHandThro.HandSpecificThro> availableThros = new ArrayList<>();

		for (int hand = 0; hand < state.length; hand++)
		{
			for (int position = 1; position <= MAX_THROW; position++)
			{
				if (!VanillaState.isSet(state[hand], position))
				{
					availableThros.add(MultiHandThro.HandSpecificThro.get(hand, position));
				}
			}
		}

		final int objectsThrowing = (int) Arrays.stream(state)
			.filter(hand -> VanillaState.isSet(hand, 0))
			.count();

		final ZeroThrowInjector injectZeroThros = new ZeroThrowInjector(IntStream.range(0, state.length)
			.filter(handIndex -> !VanillaState.isSet(state[handIndex], 0))
			.toArray());

		final Combinations combinations = new Combinations(availableThros.size(), objectsThrowing);

		return StreamSupport.stream(Spliterators.spliterator(combinations.iterator(), 0, 0), false)
			.map(combination -> Arrays.stream(combination).mapToObj(availableThros::get).collect(Collectors.toList()))
			.flatMap(this::permute)
			.map(injectZeroThros)
			.map(thros -> new MultiHandThro(thros.toArray(new MultiHandThro.HandSpecificThro[thros.size()])))
			.map(multiHandThro -> (Thro) multiHandThro)
			.iterator();
	}

	private Stream<List<MultiHandThro.HandSpecificThro>> permute(final List<MultiHandThro.HandSpecificThro> handSpecificThros)
	{
		final PermutationIterator<MultiHandThro.HandSpecificThro> iterator = new PermutationIterator<>(handSpecificThros);

		return StreamSupport.stream(Spliterators.spliterator(iterator, handSpecificThros.size(), 0), false);
	}

	public MultiHandedSyncState undo(final MultiHandThro thro)
	{
		if (thro.getNumHands() != state.length)
		{
			throw new BadThrowException("Number of hands not equal");
		}

		final long[] previousState = Arrays.copyOf(state, state.length);

		// raise up
		for (int hand = 0; hand < state.length; hand++)
		{
			previousState[hand] <<= 1;
		}

		for (int fromHand = 0; fromHand < state.length; fromHand++)
		{
			final MultiHandThro.HandSpecificThro throwForHand = thro.getThrowForHand(fromHand);

			final int toHand = throwForHand.getToHand();
			final int numBeats = throwForHand.getNumBeats();

			if (numBeats != 0)
			{
				previousState[toHand] ^= 1 << numBeats;
				previousState[fromHand] |= 1;
			}
		}

		return new MultiHandedSyncState(previousState);
	}

	private static class ZeroThrowInjector implements Function<List<MultiHandThro.HandSpecificThro>, List<MultiHandThro.HandSpecificThro>>
	{
		private final int[] handsNotThrowing;

		private ZeroThrowInjector(final int[] handsNotThrowing)
		{
			this.handsNotThrowing = handsNotThrowing;
		}


		@Override
		public List<MultiHandThro.HandSpecificThro> apply(final List<MultiHandThro.HandSpecificThro> handSpecificThros)
		{
			for (final int hand : handsNotThrowing)
			{
				handSpecificThros.add(hand, MultiHandThro.HandSpecificThro.get(hand, 0));
			}
			return handSpecificThros;
		}
	}

	@Override
	public State thro(final Thro thro) throws BadThrowException
	{
		final MultiHandThro throwz = verifyCanThrow(thro);

		final long[] nextState = Arrays.copyOf(state, state.length);

		for (int hand = 0; hand < state.length; hand++)
		{
			if (throwz.getThrowForHand(hand).getNumBeats() != 0 &&
				VanillaState.isSet(nextState[throwz.getThrowForHand(hand).getToHand()], throwz.getThrowForHand(hand).getNumBeats()))
			{
				throw new BadThrowException("Throw from hand " + hand + " will collide");
			}

			nextState[throwz.getThrowForHand(hand).getToHand()] |= (1 << throwz.getThrowForHand(hand).getNumBeats());
		}

		for (int hand = 0; hand < state.length; hand++)
		{
			nextState[hand] >>= 1;
		}

		return new MultiHandedSyncState(nextState);
	}

	private MultiHandThro verifyCanThrow(final Thro thro)
	{
		if (!(thro instanceof MultiHandThro))
		{
			throw new BadThrowException("Incorrect type of thow, required a " + MultiHandThro.class.getCanonicalName() + " received " + thro.getClass().getCanonicalName());
		}

		final MultiHandThro multiHandThro = (MultiHandThro) thro;

		if (multiHandThro.getNumHands() != this.getNumHands())
		{
			throw new BadThrowException("MultiHandThrow(" + multiHandThro.getNumHands() + ") was not for the same number of hands: " + this.getNumHands());
		}

		for (int hand = 0; hand < state.length; hand++)
		{
			if (VanillaState.isSet(state[hand], 0) == (multiHandThro.getThrowForHand(hand).getNumBeats() == 0))
			{
				throw new BadThrowException("Throw for hand [" + hand +"] is not possible.");
			}
		}

		return multiHandThro;
	}

	@Override
	public int getNumObjects()
	{
		return this.numObjects;
	}

	static int calculateNumObjects(final long[] state)
	{
		return Arrays.stream(state).mapToInt(VanillaState::numBitsSet).sum();
	}

	@Override
	public boolean isGroundState()
	{
		return this.equals(groundState(getNumHands(), getNumObjects()));
	}

	public int getNumHands()
	{
		return state.length;
	}

	@Override
	public int compareTo(final Object o)
	{
		return 0;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final MultiHandedSyncState that = (MultiHandedSyncState) o;

		if (numObjects != that.numObjects) return false;
		return Arrays.equals(state, that.state);
	}

	@Override
	public int hashCode()
	{
		int result = Arrays.hashCode(state);
		result = 31 * result + numObjects;
		return result;
	}

	public static MultiHandedSyncState groundState(final int numHands, int numObjects)
	{
		final long[] state = new long[numHands];

		int currentHand = 0;
		int currentPosition = 1;

		while (numObjects-- > 0)
		{
			state[currentHand++] |= currentPosition;

			if (currentHand == numHands)
			{
				currentHand = 0;
				currentPosition <<= 1;
			}
		}

		return new MultiHandedSyncState(state);
	}

	@Override
	public String toString()
	{
		return Arrays.stream(state)
			.mapToObj(VanillaState::new)
			.map(VanillaState::toString)
			.collect(Collectors.joining(" : ", "MultiHandedSyncState{", "}"));
	}
}
