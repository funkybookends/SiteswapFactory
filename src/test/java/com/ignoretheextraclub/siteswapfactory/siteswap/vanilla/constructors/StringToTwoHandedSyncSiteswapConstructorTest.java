package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.SyncSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class StringToTwoHandedSyncSiteswapConstructorTest
{
	private StringToTwoHandedSyncSiteswapConstructor constructor = new StringToTwoHandedSyncSiteswapConstructor();

	@Test
	public void name() throws Exception
	{
		final SyncSiteswap siteswap = constructor.apply(new SiteswapRequest("(4,4)"));

		assertThat(siteswap.toString()).isEqualTo("(4,4)");
	}
}