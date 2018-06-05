package com.ignoretheextraclub.siteswapfactory.siteswap.sync;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncState;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class SyncSiteswapTest
{

	@Before
	public void setUp() throws Exception
	{


	}

	@Test
	public void testStartingHands() throws Exception
	{
		assertThat(new SyncSiteswap(GeneralPath.from(new MultiHandedSyncState(new long[]{3, 3}), multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).getStartingNumberOfObjects(0)).isEqualTo(2);
		assertThat(new SyncSiteswap(GeneralPath.from(new MultiHandedSyncState(new long[]{3, 3}), multiHandThro(0, 2, 1, 2)).toGeneralCircuit()).getStartingNumberOfObjects(1)).isEqualTo(2);
	}
}