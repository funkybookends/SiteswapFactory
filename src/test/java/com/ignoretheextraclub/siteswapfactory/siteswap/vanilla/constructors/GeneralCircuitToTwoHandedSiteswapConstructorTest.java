package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralCircuitToTwoHandedSiteswapConstructorTest
{
	private static final VanillaThro _1 = get(1);
	private static final VanillaThro _2 = get(2);
	private static final VanillaThro _3 = get(3);
	private static final VanillaThro _4 = get(4);
	private static final VanillaThro _5 = get(5);

	private SiteswapConstructor<TwoHandedSiteswap> constructor = GeneralCircuitToTwoHandedSiteswapConstructor.get();

	@Test
	public void test3BallCascade() throws Exception
	{
		assertThat(constructor.get(GeneralPath.from(XXX__, _3).toGeneralCircuit()).toString()).isEqualTo("3");
	}
}