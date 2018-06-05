package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.StateJoiner;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 22/10/17.
 */
public class SimpleStateJoinerTest
{
	private static final Logger LOG = LoggerFactory.getLogger(SimpleStateJoinerTest.class);
    private StateJoiner mockInjectedStateJoiner;
    private StateJoiner realStateJoiner;
    private RouteSearcher mockRouteSearcher;

    @Before
    public void setUp() throws Exception
    {
        mockRouteSearcher = mock(RouteSearcher.class);
        mockInjectedStateJoiner = new SimpleStateJoiner(mockRouteSearcher);
        realStateJoiner = new SimpleStateJoiner(new ShortestPathRouteSearcherImpl(5, (generalPath -> true)));
    }

    @Test
    public void WHEN_FirstOrSecondNullOrEmpty_EXPECT_IAE() throws Exception
    {
        assertThatThrownBy(() -> mockInjectedStateJoiner.connectPath(null, GeneralPath.from(XXX__))).isInstanceOf(NullPointerException.class).hasMessageContaining("first");
        assertThatThrownBy(() -> mockInjectedStateJoiner.connectPath(GeneralPath.from(XXX__), null)).isInstanceOf(NullPointerException.class).hasMessageContaining("second");
    }

    @Test
    public void WHEN_noJoinNeeded_EXPECT_arraysAreConcatenated() throws Exception
    {
        final GeneralPath result = mockInjectedStateJoiner.connectPath(GeneralPath.from(XXX__, get(3)), GeneralPath.from(XXX__, get(3)));
        final GeneralPath expected = GeneralPath.from(XXX__, get(3), get(3));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void WHEN_intermediateStatesAreNeeded_EXPECT_theyAreInResult() throws Exception
    {
        when(mockRouteSearcher.findRoute(XX_X_, __XXX)).thenReturn(GeneralPath.from(XX_X_, get(2), get(5), get(5), get(5)));

        final GeneralPath first = GeneralPath.from(XXX__, get(3), get(4)); // XX_X_
        final GeneralPath second = GeneralPath.from(__XXX, get(0), get(0)); // XXX__

        final GeneralPath actual = mockInjectedStateJoiner.connectPath(first, second);

        final GeneralPath expected = GeneralPath.from(XXX__, get(3), get(4), get(2), get(5), get(5), get(5), get(0), get(0));

        assertThat(actual).isEqualTo(expected);
    }

	@Test
	public void GIVEN_generalPaths_EXPECT_betterName() throws Exception
	{
		final TwoHandedSiteswap first = TwoHandedSiteswapFactory.getTwoHandedSiteswap("45574");
		final TwoHandedSiteswap second = TwoHandedSiteswapFactory.getTwoHandedSiteswap("77416");

		final GeneralCircuit resultCircuit = realStateJoiner.joinShortest(first.getGeneralCircuit(), second.getGeneralCircuit());

		final TwoHandedSiteswap result = new TwoHandedSiteswap(resultCircuit);

		final TwoHandedSiteswap expectedSiteswap = TwoHandedSiteswapFactory.getTwoHandedSiteswap("4557477416");

		final GeneralCircuit expectedGeneralCircuit = expectedSiteswap.getGeneralCircuit();

		Assertions.assertThat(expectedGeneralCircuit.getRotationsArray()).contains(resultCircuit);

		LOG.info("{}", result.toString());

	}
}