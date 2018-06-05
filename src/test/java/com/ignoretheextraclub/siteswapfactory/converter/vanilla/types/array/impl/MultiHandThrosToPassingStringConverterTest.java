package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;

public class MultiHandThrosToPassingStringConverterTest
{
	private MultiHandThrosToPassingStringConverter converter = MultiHandThrosToPassingStringConverter.get();

	@Test
	public void test6ClubTwoCount() throws Exception
	{
		Assertions.assertThat(converter.apply(new Thro[]{
			multiHandThro(1, 3, 0, 3),
			multiHandThro(0, 3, 1, 3)
		})).isEqualTo("<3p|3p><3|3>");
	}

	@Test
	public void testMultipleJugglerPassesAndSelves() throws Exception
	{
		Assertions.assertThat(converter.apply(new Thro[]{
			multiHandThro(1, 3, 0, 3, 2, 4),
			multiHandThro(0, 3, 2, 3, 0, 4),
			multiHandThro(0, 4, 0, 3, 2, 4)
		})).isEqualTo("<3p2|3p1|4><3|3p3|4p1><4|3p1|4>");
	}
}