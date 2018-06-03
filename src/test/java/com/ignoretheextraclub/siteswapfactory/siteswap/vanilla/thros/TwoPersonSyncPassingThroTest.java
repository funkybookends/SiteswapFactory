package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import java.util.Iterator;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncState;

import static org.assertj.core.api.Assertions.assertThat;

public class TwoPersonSyncPassingThroTest
{

	@Before
	public void setUp() throws Exception
	{


	}

	@Test
	public void testStartingHands() throws Exception
	{
		assertThat(new PassingSiteswap(GeneralPath.from(new MultiHandedSyncState(new long[]{3, 3}), multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).toString()).isEqualTo("<2|2>");
	}

	public static MultiHandThro multiHandThro(final int... hand_beat_pairs)
	{
		final Iterator<Integer> iterator = IteratorUtils.arrayIterator(hand_beat_pairs);

		final MultiHandThro.HandSpecificThro[] handSpecificThros = new MultiHandThro.HandSpecificThro[hand_beat_pairs.length / 2];

		int i = 0;

		while (i < handSpecificThros.length)
		{
			handSpecificThros[i] = MultiHandThro.HandSpecificThro.get(iterator.next(), iterator.next());
			i++;
		}

		return new TwoPersonSyncPassingThro(handSpecificThros);
	}
}