package com.ignoretheextraclub.siteswapfactory.converter.vanilla.passing;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncStateTest.multiHandThro;

public class PassingStringToMultiHandThrosTest
{
	final PassingStringToMultiHandThros converter = PassingStringToMultiHandThros.get();

	@Test
	public void test6Ultimates() throws Exception
	{
		Assertions.assertThat(converter.apply("<3p|3p>")).isEqualTo(new MultiHandThro[]{
			multiHandThro(1, 3, 0, 3)
		});
	}

	@Test
	public void test6ClubTwoCount() throws Exception
	{
		Assertions.assertThat(converter.apply("<3p|3p><3|3>")).isEqualTo(new MultiHandThro[]{
			multiHandThro(1, 3, 0, 3),
			multiHandThro(0, 3, 1, 3),
		});
	}

	@Test
	public void testSingleThro() throws Exception
	{
		Assertions.assertThat(converter.apply("<3>")).isEqualTo(new MultiHandThro[]{
			multiHandThro(0, 3),
		});
	}

	@Test
	public void testTwoSingleThros() throws Exception
	{
		Assertions.assertThat(converter.apply("<5><3>")).isEqualTo(new MultiHandThro[]{
			multiHandThro(0, 5),
			multiHandThro(0, 3),
		});
	}

	@Test
	public void testWithThreeHands() throws Exception
	{
		Assertions.assertThat(converter.apply("<3p2|3p3|3p1>")).isEqualTo(new MultiHandThro[]{
			multiHandThro(1, 3, 2, 3, 0, 3),
		});
	}

	@Test
	public void testMultipleHandsAndMultipleBeats() throws Exception
	{
		Assertions.assertThat(converter.apply("<3p2|3p3|3p1><3p3|3p1|3p2>")).isEqualTo(new MultiHandThro[]{
			multiHandThro(1, 3, 2, 3, 0, 3),
			multiHandThro(2, 3, 0, 3, 1, 3),
		});
	}
}