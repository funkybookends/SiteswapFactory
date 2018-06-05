package com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.SyncSiteswap;

import static org.assertj.core.api.Assertions.assertThat;

public class StringToTwoHandedSyncSiteswapConstructorTest
{
	private StringToTwoHandedSyncSiteswapConstructor constructor = StringToTwoHandedSyncSiteswapConstructor.get();

	@Test
	public void name() throws Exception
	{
		final SyncSiteswap siteswap = constructor.apply(new SiteswapRequest("(4,4)"));

		assertThat(siteswap.toString()).isEqualTo("(4,4)");
	}
}