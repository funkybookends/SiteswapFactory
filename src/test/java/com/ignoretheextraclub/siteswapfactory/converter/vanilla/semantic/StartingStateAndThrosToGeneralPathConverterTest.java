package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StartingStateAndThrosToGeneralPathConverterTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Parameters
    public void test(final State start, final Thro[] thros, final State[] expected) throws Exception
    {
        assertThat(StartingStateAndThrosToGeneralPathConverter.getSequence(start, thros).getStates()).isEqualTo(expected);
    }

    public Object parametersForTest()
    {
        return new Object[]{
            new Object[]{XXX__, new Thro[]{get(3)}, new State[]{XXX__, XXX__}},
            new Object[]{XXX__, new Thro[]{get(4)}, new State[]{XXX__, XX_X_}},
            new Object[]{XXX__, new Thro[]{get(4), get(2)}, new State[]{XXX__, XX_X_, XXX__}},
            new Object[]{XXX__, new Thro[]{}, new State[]{XXX__}},
            // TODO add more
        };
    }

    @Test
    public void testExceptions() throws Exception
    {
        softly.assertThatThrownBy(() -> StartingStateAndThrosToGeneralPathConverter.getSequence(null, new Thro[]{}))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("startingState");

        softly.assertThatThrownBy(() -> StartingStateAndThrosToGeneralPathConverter.getSequence(XXX__, new Thro[]{get(0)}))
            .isInstanceOf(BadThrowException.class)
            .hasMessageContaining("[0]");
    }
}