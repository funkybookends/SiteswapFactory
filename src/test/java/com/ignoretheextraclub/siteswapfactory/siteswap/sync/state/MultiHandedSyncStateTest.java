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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync.state;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by caspar on 06/01/18.
 */
public class MultiHandedSyncStateTest
{

	public static final MultiHandedSyncState GROUND_2HANDS_4OBJECTS = MultiHandedSyncState.groundState(2, 4);

	@Test
	public void testGetGroundState()
	{
		assertThat(GROUND_2HANDS_4OBJECTS)
			.isEqualToComparingFieldByField(new MultiHandedSyncState(3, 3));
		assertThat(GROUND_2HANDS_4OBJECTS)
			.isEqualTo(new MultiHandedSyncState(3, 3));

		assertThat(MultiHandedSyncState.groundState(2, 5))
			.isEqualToComparingFieldByField(new MultiHandedSyncState(7, 3));
		assertThat(MultiHandedSyncState.groundState(2, 5))
			.isEqualTo(new MultiHandedSyncState(7, 3));
	}

	@Test
	public void testNumObjects()
	{
		final MultiHandedSyncState state = GROUND_2HANDS_4OBJECTS;
		assertThat(state.getNumObjects()).isEqualTo(4);
	}

	@Test
	public void testNumHands()
	{
		assertThat(GROUND_2HANDS_4OBJECTS.getNumHands()).isEqualTo(2);
	}

	@Test
	public void testThro()
	{
		assertThat(new MultiHandedSyncState(13, 2)
			.thro(new MultiHandThro(new MultiHandThro.HandSpecificThro[]{
				MultiHandThro.HandSpecificThro.get(1, 4),
				MultiHandThro.HandSpecificThro.get(1, 0)})))
			.isEqualTo(new MultiHandedSyncState(6, 9));

		assertThat(new MultiHandedSyncState(7, 7)
			.thro(new MultiHandThro(new MultiHandThro.HandSpecificThro[]{
				MultiHandThro.HandSpecificThro.get(1, 3),
				MultiHandThro.HandSpecificThro.get(0, 3)})))
			.isEqualTo(new MultiHandedSyncState(7, 7));

		assertThat(new MultiHandedSyncState(7, 7)
			.thro(new MultiHandThro(new MultiHandThro.HandSpecificThro[]{
				MultiHandThro.HandSpecificThro.get(0, 3),
				MultiHandThro.HandSpecificThro.get(1, 3)})))
			.isEqualTo(new MultiHandedSyncState(7, 7));
	}

	@Test
	public void GIVEN_twoHands_WHEN_getThrows_LIMITIING_toThrowsLessThanThree_EXPECT_twoThros()
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(7, 7);

		final List<Thro> availableThrows = IteratorUtils.toList(state.getAvailableThrows());

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(2);
	}

	@Test
	public void GIVEN_3hands_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_6throws()
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(7, 7, 7);

		final List<Thro> availableThrows = IteratorUtils.toList(state.getAvailableThrows());

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(6);
	}

	@Test
	public void GIVEN_onlyOneHandCanThrow_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_6thros()
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(1, 0);

		final List<Thro> availableThrows = IteratorUtils.toList(state.getAvailableThrows());

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(6);
	}

	@Test
	public void GIVEN_onlyOneHandCanThrow_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_9thros()
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(1, 0, 0);

		final List<Thro> availableThrows = IteratorUtils.toList(state.getAvailableThrows());

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(9);
	}

	@Test
	public void GIVEN_twoHandsCanThrow_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_9thros()
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(7, 7, 6);

		final List<Thro> availableThrows = IteratorUtils.toList(state.getAvailableThrows());

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(6);
	}

	@Test
	public void GIVEN_4ballGround_testAvailableThrows()
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(3, 3);

		final List<Thro> availableThrows = IteratorUtils.toList(state.getAvailableThrows());

		assertThat(availableThrows)
			.contains(multiHandThro(0, 2, 1, 2))
			.contains(multiHandThro(1, 2, 0, 2));
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

		return new MultiHandThro(handSpecificThros);
	}

	@Test
	public void testUndo()
	{
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				for (int k = 0; k < 5; k++)
				{
					final MultiHandedSyncState initialState = new MultiHandedSyncState(i, j, k);

					final Iterator<Thro> availableThrows = initialState.getAvailableThrows();

					while (availableThrows.hasNext())
					{
						final MultiHandThro availableThro = (MultiHandThro) availableThrows.next();

						final MultiHandedSyncState nextState = (MultiHandedSyncState) initialState.thro(availableThro);

						final MultiHandedSyncState actual = nextState.undo(availableThro);

						assertThat(actual)
							.as("%s %s %s : Initial State %s throw %s, next state %s, actual %s",
								i, j, k, initialState, availableThro, nextState, actual)
							.isEqualTo(initialState);
					}
				}
			}
		}
	}
}