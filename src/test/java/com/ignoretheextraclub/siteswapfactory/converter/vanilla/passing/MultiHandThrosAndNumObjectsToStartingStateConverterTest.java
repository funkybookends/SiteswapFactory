package com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing;

import java.util.function.BiFunction;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiHandThrosAndNumObjectsToStartingStateConverterTest
{
	private BiFunction<MultiHandThro[], Integer, MultiHandedSyncState> converter = MultiHandThrosAndNumObjectsToStartingStateConverter.get();

	@Test
	public void fourBallFountain() throws Exception
	{
		assertThat(converter.apply(new MultiHandThro[]{
			multiHandThro(0, 2, 1, 2)
		}, 4)).isEqualTo(new MultiHandedSyncState(new long[]{3, 3}));
	}
	@Test
	public void sixX4() throws Exception
	{
		assertThat(converter.apply(new MultiHandThro[]{
			multiHandThro(0, 2, 0, 3),
			multiHandThro(1, 3, 1, 2)
		}, 5)).isEqualTo(new MultiHandedSyncState(new long[]{3, 7}));
	}
}