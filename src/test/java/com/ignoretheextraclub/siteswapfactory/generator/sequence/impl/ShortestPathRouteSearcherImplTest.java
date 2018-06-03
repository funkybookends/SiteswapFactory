package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateTest._5;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortestPathRouteSearcherImplTest
{
	private RouteSearcher searcher;

	@Before
	public void setUp() throws Exception
	{
		searcher = new ShortestPathRouteSearcherImpl(10, generalPath -> true);

	}

	@Test
	public void testSimple() throws Exception
	{
		final GeneralPath route = searcher.findRoute(XXX__, __XXX);

		assertThat(route).isEqualTo(GeneralPath.from(XXX__, _5, _5, _5));

		// TODO add more
	}
}