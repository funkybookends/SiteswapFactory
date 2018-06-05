package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncStateTest;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SyncTwoHandedVanillaStringToMultiHandThrosConverterTest
{
	final SyncTwoHandedVanillaStringToMultiHandThrosConverter converter = SyncTwoHandedVanillaStringToMultiHandThrosConverter.get();

	@Test
	public void testFourFour() throws Exception
	{
		final MultiHandThro[] actual = converter.apply("(4,4)");
		assertThat(actual).isEqualTo(new MultiHandThro[]{MultiHandedSyncStateTest.multiHandThro(0, 2, 1, 2)});
	}

	@Test
	public void testSixXFourFour6X() throws Exception
	{
		final MultiHandThro[] actual = converter.apply("(6x,4)(4,6x)");
		assertThat(actual).isEqualTo(new MultiHandThro[]{
			MultiHandedSyncStateTest.multiHandThro(1, 3, 1, 2),
			MultiHandedSyncStateTest.multiHandThro(0, 2, 0, 3)
		});
	}

	@Test
	public void testSixXFourStar() throws Exception
	{
		final MultiHandThro[] actual = converter.apply("(6x,4)*");
		assertThat(actual).isEqualTo(new MultiHandThro[]{
			MultiHandedSyncStateTest.multiHandThro(1, 3, 1, 2),
			MultiHandedSyncStateTest.multiHandThro(0, 2, 0, 3)
		});
	}

	@Test
	public void testWithTooManyThros() throws Exception
	{
		assertThatThrownBy(() -> converter.apply("(4,4,4)"))
			.isInstanceOf(BadThrowException.class);
	}
}