package com.ignoretheextraclub.siteswapfactory.generator;

import java.util.Iterator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.NoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;

public class RouteIteratorTest
{
	private static final Logger LOG = LoggerFactory.getLogger(RouteIteratorTest.class);

	@Test
	public void name() throws Exception
	{

		final Iterator<GeneralPath> routeIterator = new RouteIterator(XXX__, new NoThroHigherThanPredicate(VanillaThro.get(5)), 15, 1);

		final int count = 1000;

		for (int i = 0; i < count; i++)
		{
			final GeneralPath next = routeIterator.next();
			LOG.info("{} : {}", i, next);
		}

	}
}