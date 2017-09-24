package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class LocalToGlobalConverterTest
{
    private static final VanillaThro _5 = getUnchecked(5);
    private static final VanillaThro _6 = getUnchecked(6);
    private static final VanillaThro _7 = getUnchecked(7);
    private static final VanillaThro _8 = getUnchecked(8);
    private static final VanillaThro _9 = getUnchecked(9);
    private static final VanillaThro _10 = getUnchecked(10);

    @Test
    @Parameters
    public void testApply(final VanillaThro[] input, final VanillaThro[] expected) throws Exception
    {
        assertThat(new LocalToGlobalConverter<VanillaThro>().apply(input)).isEqualTo(expected);
        assertThat(LocalToGlobalConverter.convertToGlobal(input)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{thros(_7), thros(_7)},
                new Object[]{thros(_9, _5, _7), thros(_9, _7, _5)},
                new Object[]{thros(_7, _9, _6, _8, _10), thros(_7, _8, _9, _10, _6)},
                new Object[]{thros(_7, _6, _6, _6, _8, _8, _8), thros(_7, _8, _6, _8, _6, _8, _6)},
                };
    }

    @Test
    @Parameters
    public void testApplyInt(final int[] input, final int[] expected) throws Exception
    {
        assertThat(LocalToGlobalConverter.IntConverter.get().apply(input)).isEqualTo(expected);
        assertThat(LocalToGlobalConverter.convertToGlobal(input)).isEqualTo(expected);
    }

    private Object parametersForTestApplyInt()
    {
        return new Object[]{
                new Object[]{StateTestUtils.thros(7), StateTestUtils.thros(7)},
                new Object[]{StateTestUtils.thros(9, 5, 7), StateTestUtils.thros(9, 7, 5)},
                new Object[]{StateTestUtils.thros(7, 9, 6, 8, 10), StateTestUtils.thros(7, 8, 9, 10, 6)},
                new Object[]{StateTestUtils.thros(7, 6, 6, 6, 8, 8, 8), StateTestUtils.thros(7, 8, 6, 8, 6, 8, 6)},
                };
    }

    @Test
    @Parameters
    public void testException(final VanillaThro[] input, final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> LocalToGlobalConverter.convertToGlobal(input)).isInstanceOf(exception.getClass())
                                                                               .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
                new Object[]{thros(_6, _7), new IllegalArgumentException("local must have odd length")},
                new Object[]{thros(_6, _7, _8, _9), new IllegalArgumentException("local must have odd length")},
                new Object[]{null, new NullPointerException("local")},
                new Object[]{new VanillaThro[]{}, new IllegalArgumentException("local")},
        };
    }

    @Test
    @Parameters
    public void testExceptionInt(final int[] input, final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> LocalToGlobalConverter.convertToGlobal(input)).isInstanceOf(exception.getClass())
                                                                               .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestExceptionInt()
    {
        return new Object[]{
                new Object[]{StateTestUtils.thros(6, 7), new IllegalArgumentException("local must have odd length")},
                new Object[]{StateTestUtils.thros(6, 7, 8, 9), new IllegalArgumentException("local must have odd length")},
                new Object[]{null, new NullPointerException("local")},
                new Object[]{new int[]{}, new IllegalArgumentException("local")},
        };
    }
}