package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiHandThroToPassingStringConverterTest
{
	private MultiHandThroToPassingStringConverter converter = MultiHandThroToPassingStringConverter.get();

	@Test
	public void testOneHand() throws Exception
	{
		assertThat(converter.apply(multiHandThro(0, 3))).isEqualTo("<3>");
	}

	@Test
	public void test6ClubUltimates() throws Exception
	{
		assertThat(converter.apply(multiHandThro(1, 3, 0, 3))).isEqualTo("<3p|3p>");
	}

	@Test
	public void testExchangeAndSelfer() throws Exception
	{
		assertThat(converter.apply(multiHandThro(2, 3, 1, 3, 0, 3))).isEqualTo("<3p3|3|3p1>");
	}

	@Test
	public void testDoublerZapperAndSelfer() throws Exception
	{
		assertThat(converter.apply(multiHandThro(2, 4, 1, 3, 0, 1))).isEqualTo("<4p3|3|1p1>");
	}

	@Test
	public void testDoubleZappWithTwoJugglers() throws Exception
	{
		assertThat(converter.apply(multiHandThro(1, 4, 0, 1))).isEqualTo("<4p|1p>");
	}
}