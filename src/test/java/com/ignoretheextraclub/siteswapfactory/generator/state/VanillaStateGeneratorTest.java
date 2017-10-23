package com.ignoretheextraclub.siteswapfactory.generator.state;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

import junitparams.JUnitParamsRunner;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_XX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_X_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X__XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._X_XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 09/10/17.
 */
@RunWith(JUnitParamsRunner.class)
public class VanillaStateGeneratorTest
{
    @Test
    public void testGenerator() throws Exception
    {
        final Stream<VanillaState> allStatesStream = VanillaStateGenerator.getAllStates(3, 5);
        final List<VanillaState> allStates = allStatesStream.collect(Collectors.toList());

        assertThat(allStates).contains(
            XXX__,
            XX_X_,
            XX__X,
            X_XX_,
            X_X_X,
            X__XX,
            _XXX_,
            _XX_X,
            _X_XX,
            __XXX // TODO add more
        );
    }

    @Test
    public void testGroundState() throws Exception
    {
        assertThat(VanillaStateGenerator.getGroundState(3)).isEqualTo(XXX__);
        assertThat(VanillaStateGenerator.getGroundState(4)).isEqualTo(XXXX_);
    }
}