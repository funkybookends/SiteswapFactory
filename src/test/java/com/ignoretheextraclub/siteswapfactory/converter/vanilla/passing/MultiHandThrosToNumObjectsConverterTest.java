package com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiHandThrosToNumObjectsConverterTest
{
	private final MultiHandThrosToNumObjectsConverter converter = MultiHandThrosToNumObjectsConverter.get();

	@Test
	public void sixX4() throws Exception
	{
		assertThat(converter.apply(new MultiHandThro[]{
			multiHandThro(0, 2, 0, 3),
			multiHandThro(1, 3, 1, 2)
		})).isEqualTo(5);
	}

	@Test
	public void fourBallFountain() throws Exception
	{
		assertThat(converter.apply(new MultiHandThro[]{
			multiHandThro(0, 2, 1, 2)
		})).isEqualTo(4);
	}
}