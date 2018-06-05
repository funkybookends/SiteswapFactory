package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X____X;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 14/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class StatesToVanillaStatesConverterTest
{

    @Test
    @Parameters
    public void testApply(final State[] states, final VanillaState[] expected) throws Exception
    {
        assertThat(StatesToVanillaStatesConverter.get().apply(states)).isEqualTo(expected);
        assertThat(StatesToVanillaStatesConverter.convert(states)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{new State[]{XXX__, XXXXXXXX__, XXX_, X____X}, new VanillaState[]{XXX__, XXXXXXXX__, XXX_, X____X}},
                new Object[]{new State[]{}, new VanillaState[]{}},
                // TODO add more
        };
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> StatesToVanillaStatesConverter.get().apply(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("states");
    }
}