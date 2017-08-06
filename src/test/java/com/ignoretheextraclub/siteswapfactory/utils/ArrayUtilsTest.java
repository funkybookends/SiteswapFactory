package com.ignoretheextraclub.siteswapfactory.utils;

import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.state;
import static org.junit.Assert.fail;

/**
 Created by caspar on 29/07/17.
 */
public class ArrayUtilsTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void copy() throws Exception
    {
        softly.assertThat(ArrayUtils.copy(new boolean[]{true, false})).isEqualTo(new boolean[]{true, false});
    }

    @Test
    public void drop() throws Exception
    {
        softly.assertThat(ArrayUtils.drop(new boolean[]{true, false, true, true, false}, true)).isEqualTo(new boolean[]{false, true, true, false, true});
    }

    @Test
    @Ignore("still need implementing")
    public void getFirstMinIndex() throws Exception
    {
        fail("Test not yet implemented");
    }

    @Test
    @Ignore("still need implementing")
    public void getFirstMinIndex1() throws Exception
    {
        fail("Test not yet implemented");
    }

    @Test
    @Ignore("still need implementing")
    public void getFirstMaxIndex() throws Exception
    {
        fail("Test not yet implemented");
    }

    @Test
    @Ignore("still need implementing")
    public void getFirstMaxIndex1() throws Exception
    {
        fail("Test not yet implemented");
    }

    @Test
    public void getCopy() throws Exception
    {
        softly.assertThat(ArrayUtils.getCopy(StateTestUtils.states(state(true, true, false), state(true, false, true), state(true, true, false)), 1))
              .isEqualTo(StateTestUtils.states(state(true, false, true), state(true, true, false), state(true, true, false)));
    }


}