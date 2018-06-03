package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncStateTest;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThroTest;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.MultiHandedSyncStateTest.multiHandThro;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThroTest.thro;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

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