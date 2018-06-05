package com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.SyncSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralCircuitToTwoHandedSyncSiteswapConstructorTest
{
	private SiteswapConstructor<SyncSiteswap> siteswapConstructor = GeneralCircuitToTwoHandedSyncSiteswapConstructor.get();

	@Test
	public void test4BallFountain() throws Exception
	{
		assertThat(siteswapConstructor.get(GeneralPath.from(new MultiHandedSyncState(3, 3)
			, multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).toString()).isEqualTo("(4,4)");
	}
}