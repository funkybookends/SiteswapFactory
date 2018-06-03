package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXX___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro.get;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralCircuitToFourHandedSiteswapConstructorTest
{
	private SiteswapConstructor<FourHandedSiteswap> siteswapConstructor = GeneralCircuitToFourHandedSiteswapConstructor.get();

	@Test
	public void nineSevenFive() throws Exception
	{
		assertThat(siteswapConstructor.apply(new SiteswapRequest(GeneralPath.from(XXXXXXX___, get(9), get(7), get(5)).toGeneralCircuit())).toString()).isEqualTo("975");
	}
}