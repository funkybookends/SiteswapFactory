package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils.getFirstState;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils.stringToVanillaThrowArray;
import static org.junit.Assert.fail;

/**
 Created by caspar on 29/07/17.
 */
public class VanillaStateUtilsTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testgetFirstState() throws Exception
    {
        softly.assertThat(getFirstState(stringToVanillaThrowArray("3"))).isEqualTo(XXX__);
        softly.assertThat(getFirstState(stringToVanillaThrowArray("441"))).isEqualTo(XXX__);
        softly.assertThat(getFirstState(stringToVanillaThrowArray("441355203"))).isEqualTo(XXX__);
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