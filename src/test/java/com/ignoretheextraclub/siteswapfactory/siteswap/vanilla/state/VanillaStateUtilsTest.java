package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.state;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils.getFirstState;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils.stringToVanillaThrowArray;
import static org.junit.Assert.fail;

/**
 Created by caspar on 29/07/17.
 */
public class VanillaStateUtilsTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testGetFirstState() throws Exception
    {
        softly.assertThat(getFirstState(stringToVanillaThrowArray("3"))).isEqualTo(state(true, true, true));
        softly.assertThat(getFirstState(stringToVanillaThrowArray("441"))).isEqualTo(state(true, true, true, false));
        softly.assertThat(getFirstState(stringToVanillaThrowArray("441355203"))).isEqualTo(XXX__);
        softly.assertThat(getFirstState(stringToVanillaThrowArray("55500"))).isEqualTo(XXX__);
        softly.assertThat(getFirstState(stringToVanillaThrowArray("555")))
              .isEqualTo(state(true, true, true, true, true));
        softly.assertThat(getFirstState(stringToVanillaThrowArray("555"), 3)).isEqualTo(XXX__);
        softly.assertThatThrownBy(() -> getFirstState(stringToVanillaThrowArray("555"), 6))
              .isInstanceOf(InvalidSiteswapException.class);
    }

    @Test
    @Ignore("Not implemented yet")
    public void testgetGroundState() throws Exception
    {
        fail("Test not yet implemented");
    }

    @Test
    @Ignore("Not implemented yet")
    public void testtestToString() throws Exception
    {
        fail("Test not yet implemented");
    }

    @Test
    @Ignore("Not implemented yet")
    public void testgetNumTrue() throws Exception
    {
        fail("Test not yet implemented");
    }
}