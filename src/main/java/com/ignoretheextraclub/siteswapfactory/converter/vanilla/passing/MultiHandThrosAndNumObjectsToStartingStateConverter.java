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
