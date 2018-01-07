package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import java.util.Set;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by caspar on 06/01/18.
 */
public class MultiHandedSyncStateTest
{

	public static final MultiHandedSyncState GROUND_2HANDS_4OBJECTS = MultiHandedSyncState.groundState(2, 4);

	@Test
	public void testGetGroundState() throws Exception
	{
		assertThat(GROUND_2HANDS_4OBJECTS)
			.isEqualToComparingFieldByField(new MultiHandedSyncState(new long[]{3, 3}));
		assertThat(GROUND_2HANDS_4OBJECTS)
			.isEqualTo(new MultiHandedSyncState(new long[]{3, 3}));

		assertThat(MultiHandedSyncState.groundState(2, 5))
			.isEqualToComparingFieldByField(new MultiHandedSyncState(new long[]{7, 3}));
		assertThat(MultiHandedSyncState.groundState(2, 5))
			.isEqualTo(new MultiHandedSyncState(new long[]{7, 3}));
	}

	@Test
	public void testNumObjects() throws Exception
	{
		final MultiHandedSyncState state = GROUND_2HANDS_4OBJECTS;
		assertThat(state.getNumObjects()).isEqualTo(4);
	}

	@Test
	public void testNumHands() throws Exception
	{
		assertThat(GROUND_2HANDS_4OBJECTS.getNumHands()).isEqualTo(2);
	}

	@Test
	public void testThro() throws Exception
	{
		assertThat(new MultiHandedSyncState(new long[]{13, 2})
			.thro(new MultiHandThro(new MultiHandThro.HandSpecificThro[]{
				MultiHandThro.HandSpecificThro.get(1, 4),
				MultiHandThro.HandSpecificThro.get(1,0)})))
			.isEqualTo(new MultiHandedSyncState(new long[]{6,9}));

		assertThat(new MultiHandedSyncState(new long[]{7, 7})
			.thro(new MultiHandThro(new MultiHandThro.HandSpecificThro[]{
				MultiHandThro.HandSpecificThro.get(1, 3),
				MultiHandThro.HandSpecificThro.get(0,3)})))
			.isEqualTo(new MultiHandedSyncState(new long[]{7,7}));

		assertThat(new MultiHandedSyncState(new long[]{7, 7})
			.thro(new MultiHandThro(new MultiHandThro.HandSpecificThro[]{
				MultiHandThro.HandSpecificThro.get(0, 3),
				MultiHandThro.HandSpecificThro.get(1,3)})))
			.isEqualTo(new MultiHandedSyncState(new long[]{7,7}));
	}

	@Test
	public void GIVEN_twoHands_WHEN_getThrows_LIMITIING_toThrowsLessThanThree_EXPECT_twoThros() throws Exception
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(new long[]{7, 7});

		final Set<Thro> availableThrows = state.getAvailableThrows();

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(2);
	}

	@Test
	public void GIVEN_3hands_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_6throws() throws Exception
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(new long[]{7, 7, 7});

		final Set<Thro> availableThrows = state.getAvailableThrows();

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(6);
	}

	@Test
	public void GIVEN_onlyOneHandCanThrow_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_6thros() throws Exception
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(new long[]{1, 0});

		final Set<Thro> availableThrows = state.getAvailableThrows();

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(6);
	}

	@Test
	public void GIVEN_onlyOneHandCanThrow_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_9thros() throws Exception
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(new long[]{1, 0, 0});

		final Set<Thro> availableThrows = state.getAvailableThrows();

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(9);
	}

	@Test
	public void GIVEN_twoHandsCanThrow_WHEN_getThrows_LIMITING_toThrowsLessThanThree_EXPECT_9thros() throws Exception
	{
		final MultiHandedSyncState state = new MultiHandedSyncState(new long[]{7, 7, 6});

		final Set<Thro> availableThrows = state.getAvailableThrows();

		availableThrows.removeIf(thro -> thro.getNumBeats() > 3);

		assertThat(availableThrows).hasSize(6);
	}
}