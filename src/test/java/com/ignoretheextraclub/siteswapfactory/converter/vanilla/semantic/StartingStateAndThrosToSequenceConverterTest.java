package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StartingStateAndThrosToSequenceConverterTest
{

    @Test
    @Parameters
    public void test(final State start, final Thro[] thros, final State[] expected) throws Exception
    {
        assertThat(StartingStateAndThrosToSequenceConverter.getSequence(start, thros)).isEqualTo(expected);
    }

    public Object parametersForTest()
    {
        return new Object[]{
            new Object[]{XXX__, new Thro[]{get(3)}, new State[]{XXX__, XXX__}},
            new Object[]{XXX__, new Thro[]{get(4)}, new State[]{XXX__, XX_X_}},
            new Object[]{XXX__, new Thro[]{get(4), get(2)}, new State[]{XXX__, XX_X_, XXX__}},

            // TODO add more
        };
    }

    // TODO add exception tests
}