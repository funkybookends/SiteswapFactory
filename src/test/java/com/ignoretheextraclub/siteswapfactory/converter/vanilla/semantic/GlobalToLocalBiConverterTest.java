package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

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
public class GlobalToLocalBiConverterTest
{
    private static final VanillaThro _1 = getUnchecked(1);
    private static final VanillaThro _2 = getUnchecked(2);
    private static final VanillaThro _3 = getUnchecked(3);
    private static final VanillaThro _4 = getUnchecked(4);
    private static final VanillaThro _5 = getUnchecked(5);

    @Test
    @Parameters
    public void testApply(final VanillaThro[] input, final Integer start, final VanillaThro[] expected) throws Exception
    {
        assertThat(new GlobalToLocalBiConverter<VanillaThro>().apply(input, start)).isEqualTo(expected);
        assertThat(GlobalToLocalBiConverter.convertToLocal(input, start)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
                new Object[]{thros(_1, _2, _3), 0, thros(_1, _3, _2)},
                new Object[]{thros(_1, _2, _3), 1, thros(_2, _1, _3)},
                new Object[]{thros(_1, _2, _3), 2, thros(_3, _2, _1)},
                new Object[]{thros(_1, _2, _3), 3, thros(_1, _3, _2)},
                new Object[]{thros(_1, _2, _3), 4, thros(_2, _1, _3)},
                new Object[]{thros(_1, _2, _3, _4, _5), 0, thros(_1, _3, _5, _2, _4)},
                new Object[]{thros(_1, _2, _3, _4, _5), 1, thros(_2, _4, _1, _3, _5)},
                new Object[]{thros(_1, _2, _3, _4, _5), 2, thros(_3, _5, _2, _4, _1)},
                new Object[]{thros(_1, _2, _3, _4, _5), 3, thros(_4, _1, _3, _5, _2)},
                new Object[]{thros(_1, _2, _3, _4, _5), 4, thros(_5, _2, _4, _1, _3)},
                new Object[]{thros(_1, _2, _3, _4, _5), 5, thros(_1, _3, _5, _2, _4)},
                new Object[]{thros(_1, _2, _3, _4), 0, thros(_1, _3)},
                new Object[]{thros(_1, _2, _3, _4), 1, thros(_2, _4)},
                new Object[]{thros(_1, _2, _3, _4), 2, thros(_3, _1)},
                new Object[]{thros(_1, _2, _3, _4), 3, thros(_4, _2)},
                new Object[]{thros(_1), 0, thros(_1)},
                new Object[]{thros(_1), 1, thros(_1)},
                new Object[]{thros(_1, _2), 0, thros(_1)},
                new Object[]{thros(_1, _2), 1, thros(_2)},
                };
    }

    @Test
    @Parameters
    public void testApplyInt(final int[] input, final Integer start, final int[] expected) throws Exception
    {
        assertThat(GlobalToLocalBiConverter.IntConverter.get().apply(input, start)).isEqualTo(expected);
        assertThat(GlobalToLocalBiConverter.convertToLocal(input, start)).isEqualTo(expected);
    }

    private Object parametersForTestApplyInt()
    {
        return new Object[]{
                new Object[]{thros(1, 2, 3), 0, thros(1, 3, 2)},
                new Object[]{thros(1, 2, 3), 1, thros(2, 1, 3)},
                new Object[]{thros(1, 2, 3), 2, thros(3, 2, 1)},
                new Object[]{thros(1, 2, 3), 3, thros(1, 3, 2)},
                new Object[]{thros(1, 2, 3), 4, thros(2, 1, 3)},
                new Object[]{thros(1, 2, 3, 4, 5), 0, thros(1, 3, 5, 2, 4)},
                new Object[]{thros(1, 2, 3, 4, 5), 1, thros(2, 4, 1, 3, 5)},
                new Object[]{thros(1, 2, 3, 4, 5), 2, thros(3, 5, 2, 4, 1)},
                new Object[]{thros(1, 2, 3, 4, 5), 3, thros(4, 1, 3, 5, 2)},
                new Object[]{thros(1, 2, 3, 4, 5), 4, thros(5, 2, 4, 1, 3)},
                new Object[]{thros(1, 2, 3, 4, 5), 5, thros(1, 3, 5, 2, 4)},
                new Object[]{thros(1, 2, 3, 4), 0, thros(1, 3)},
                new Object[]{thros(1, 2, 3, 4), 1, thros(2, 4)},
                new Object[]{thros(1, 2, 3, 4), 2, thros(3, 1)},
                new Object[]{thros(1, 2, 3, 4), 3, thros(4, 2)},
                new Object[]{thros(1), 0, thros(1)},
                new Object[]{thros(1), 1, thros(1)},
                new Object[]{thros(1, 2), 0, thros(1)},
                new Object[]{thros(1, 2), 1, thros(2)},
                };
    }

    @Test
    @Parameters
    public void testException(final VanillaThro[] input,
                              final Integer start,
                              final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> new GlobalToLocalBiConverter<VanillaThro>().apply(input,
                start)).isInstanceOf(exception.getClass())
                       .hasMessageContaining(exception.getMessage());
        assertThatThrownBy(() -> GlobalToLocalBiConverter.convertToLocal(input,
                start)).isInstanceOf(exception.getClass())
                       .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
                new Object[]{thros(_1, _2, _3), -1, new IllegalArgumentException("negative")},
                new Object[]{null, 3, new NullPointerException("global")},
                new Object[]{thros(_1, _2, _3), null, new NullPointerException("start")},
                new Object[]{new VanillaThro[]{}, 3, new IllegalArgumentException("global")},
                };
    }

    @Test
    @Parameters
    public void testExceptionInt(final int[] input,
                                 final Integer start,
                                 final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> GlobalToLocalBiConverter.IntConverter.get().apply(input,
                start)).isInstanceOf(exception.getClass())
                       .hasMessageContaining(exception.getMessage());
        assertThatThrownBy(() -> GlobalToLocalBiConverter.convertToLocal(input,
                start)).isInstanceOf(exception.getClass())
                       .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestExceptionInt()
    {
        return new Object[]{
                new Object[]{thros(1, 2, 3), -1, new IllegalArgumentException("negative")},
                new Object[]{null, 3, new NullPointerException("global")},
                new Object[]{thros(1, 2, 3), null, new NullPointerException("start")},
                new Object[]{new int[]{}, 3, new IllegalArgumentException("global")},
                };
    }
}