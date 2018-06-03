package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class StringToPassingSiteswapConstructorTest
{
	private SiteswapConstructor<PassingSiteswap> siteswapConstructor = StringToPassingSiteswapConstructor.get();

	@Test
	public void test6clubUltimates() throws Exception
	{
		final PassingSiteswap actual = siteswapConstructor.get("<3p|3p>");
		assertThat(actual.toString()).isEqualTo("<3p|3p>");
	}
}