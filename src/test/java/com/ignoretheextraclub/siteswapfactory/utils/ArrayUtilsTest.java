package com.ignoretheextraclub.siteswapfactory.utils;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;

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
        final boolean[] positions = {true, false};
        final boolean[] result = ArrayUtils.copy(positions);
        softly.assertThat(positions).isEqualTo(result);
        softly.assertThat(positions).isNotSameAs(result);
    }

    @Test
    public void drop() throws Exception
    {
        softly.assertThat(ArrayUtils.drop(new boolean[]{true, false, true, true, false}, true))
            .isEqualTo(new boolean[]{false, true, true, false, true});
    }

    @Test
    public void getCopy() throws Exception
    {
        softly.assertThat(ArrayUtils.getRotatedCopy(StateTestUtils.states(XXX__, XX_X_, XXX__), 1))
              .isEqualTo(StateTestUtils.states(XX_X_, XXX__, XXX__));
    }

    @Test
    public void getRotation() throws Exception
    {
        softly.assertThat(ArrayUtils.getRotatedCopy(new State[]{XXX__, XX_X_}, 1)).isEqualTo(new State[]{XX_X_, XXX__});
        // todo add more
    }
}