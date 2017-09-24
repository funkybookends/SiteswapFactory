package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class VanillaThrosToFirstStateConverterTest
{
    @Test
    @Parameters
    public void testApply(final VanillaThro[] input,
                          final VanillaState expected) throws Exception
    {
        assertThat(VanillaThrosToFirstStateConverter.get().apply(input)).isEqualTo(expected);
        assertThat(VanillaThrosToFirstStateConverter.getFirstState(input)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{thros(getUnchecked(3)), XXX},
                new Object[]{thros(getUnchecked(4), getUnchecked(2)), XXX_},
                new Object[]{
                        thros(getUnchecked(8),
                                getUnchecked(9),
                                getUnchecked(10),
                                getUnchecked(6),
                                getUnchecked(7)), XXXXXXXX__
                },
                new Object[]{
                        thros(getUnchecked(9),
                                getUnchecked(10),
                                getUnchecked(6),
                                getUnchecked(7),
                                getUnchecked(8)), XXXXXXXX__
                },
                };
        // TODO add more
    }

    @Test
    @Parameters
    public void testException(final VanillaThro[] input,
                              final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> VanillaThrosToFirstStateConverter.get()
                                                                  .apply(input)).isInstanceOf(exception.getClass())
                                                                                .hasMessageContaining(exception.getMessage());
        assertThatThrownBy(() -> VanillaThrosToFirstStateConverter.getFirstState(input)).isInstanceOf(exception.getClass())
                                                                                        .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
                new Object[]{
                        thros(getUnchecked(3), getUnchecked(4), getUnchecked(3)), new InvalidSiteswapException(
                        "[3, 4, 3]")
                },
                new Object[]{
                        thros(getUnchecked(3), getUnchecked(4), getUnchecked(3)), new InvalidSiteswapException(
                        "[3] objects")
                },
                new Object[]{
                        null, new NullPointerException("thros cannot be null")
                },
                };
        // TODO add more
    }
}