package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_X_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;

/**
 * Created by caspar on 09/10/17.
 */
public class StateSearcherTest
{
	private static final Logger LOG = LoggerFactory.getLogger(StateSearcherTest.class);

	@Test
	public void name() throws Exception
	{
		final Set<State> startingStates = new LinkedHashSet<>();

		// startingStates.add(XXX__);
		startingStates.add(X_X_X);
		startingStates.add(__XXX);

		final StateSearcher<TwoHandedVanillaSiteswap> twoHandedSiteswapStateSearcher = new StateSearcher<>(startingStates, 5, (path) -> true, (circuit) -> true, GeneralCircuitToTwoHandedSiteswapConstructor.get(), null);

		final int count = 100;

		for (int i = 0; i < count && twoHandedSiteswapStateSearcher.hasNext(); i++)
		{
			final TwoHandedVanillaSiteswap next = twoHandedSiteswapStateSearcher.next();
			LOG.info("{} : {}", i, next);

		}
	}
}