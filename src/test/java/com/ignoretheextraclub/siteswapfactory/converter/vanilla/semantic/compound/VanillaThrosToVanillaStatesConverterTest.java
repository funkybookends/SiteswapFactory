package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.compound;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(Parameterized.class)
public class VanillaThrosToVanillaStatesConverterTest
{
    private final VanillaThro[] input;
    private final VanillaState[] expected;
    private final Class<? extends Exception> exception;

    @Parameterized.Parameters()
    public static Collection<Object[]> data()
    {
        final ArrayList<Object[]> tests = new ArrayList<>();

        tests.add(new Object[]{new VanillaThro[]{getUnchecked(4), getUnchecked(2), getUnchecked(3)}, new VanillaState[]{XXX__, XX_X_, XXX__}, null});
        tests.add(new Object[]{new VanillaThro[]{getUnchecked(5), getUnchecked(3), getUnchecked(4)}, new VanillaState[]{XXXX__, XXX_X_, XXXX__}, null});
        tests.add(new Object[]{new VanillaThro[]{getUnchecked(5), getUnchecked(4), getUnchecked(3)}, null, InvalidSiteswapException.class});
        // TODO add more test cases

        return tests;
    }

    public VanillaThrosToVanillaStatesConverterTest(final VanillaThro[] input, final VanillaState[] expected, final Class<? extends Exception> exception)
    {
        this.input = input;
        this.expected = expected;
        this.exception = exception;
    }

    @Test
    public void testApply() throws Exception
    {
        if (expected != null)
        {
            assertThat(VanillaThrosToVanillaStatesConverter.get().apply(input)).isEqualTo(expected);
        }
    }

    @Test
    public void testConvert() throws Exception
    {
        if (expected != null)
        {
            assertThat(VanillaThrosToVanillaStatesConverter.convertThrosToStates(input)).isEqualTo(expected);
        }
    }

    @Test
    public void testException() throws Exception
    {
        if (exception != null)
        {
            assertThatThrownBy(() -> VanillaThrosToVanillaStatesConverter.convertThrosToStates(input)).isInstanceOf(exception);
        }
    }
}