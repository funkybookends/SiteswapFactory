package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class StreamingFilteringReducerTest
{
    @Test
    @Parameters
    public void testApply(final String[] input, final String[] expected) throws Exception
    {
        assertThat(StreamingFilteringReducer.reducing(input)).isEqualTo(expected);
        assertThat(StreamingFilteringReducer.get().reduce(input)).isEqualTo(expected);
    }

    private Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{new String[]{}, new String[]{}},
            new Object[]{new String[]{"7"}, new String[]{"7"}},
            new Object[]{new String[]{"7", "7"}, new String[]{"7"}},
            new Object[]{new String[]{"7", "7", "7"}, new String[]{"7"}},
            new Object[]{new String[]{"7", "7", "7", "7"}, new String[]{"7"}},
            new Object[]{new String[]{"7", "5", "7", "5"}, new String[]{"7", "5"}},
            new Object[]{new String[]{"7", "5", "7", "6"}, new String[]{"7", "5", "7", "6"}},
            new Object[]{new String[]{"7", "5", "7", "6", "7", "5", "7", "6"}, new String[]{"7", "5", "7", "6"}},
            new Object[]{new String[]{"7", "5", "7", "6", "7", "5", "7", "6", "7", "5", "7", "6"}, new String[]{"7", "5", "7", "6"}},
            new Object[]{new String[]{"7", "5", "7", "6", "7", "5", "7", "6", "7", "5", "7"}, new String[]{"7", "5", "7", "6", "7", "5", "7", "6", "7", "5", "7"}},
        };
    }

    @Test
    @Parameters
    public void testApplyInt(final int[] input, final int[] expected) throws Exception
    {
        assertThat(StreamingFilteringReducer.IntReducer.get().apply(input)).isEqualTo(expected);
        assertThat(StreamingFilteringReducer.reduce(input)).isEqualTo(expected);
    }

    private Object parametersForTestApplyInt()
    {
        return new Object[]{
            new Object[]{new int[]{}, new int[]{}},
            new Object[]{thros(7), thros(7)},
            new Object[]{thros(7, 7), thros(7)},
            new Object[]{thros(7, 7, 7), thros(7)},
            new Object[]{thros(7, 7, 7, 7), thros(7)},
            new Object[]{thros(7, 5, 7, 5), thros(7, 5)},
            new Object[]{thros(7, 5, 7, 6), thros(7, 5, 7, 6)},
            new Object[]{thros(7, 5, 7, 6, 7, 5, 7, 6), thros(7, 5, 7, 6)},
            new Object[]{thros(7, 5, 7, 6, 7, 5, 7, 6, 7, 5, 7, 6), thros(7, 5, 7, 6)},
            new Object[]{thros(7, 5, 7, 6, 7, 5, 7, 6, 7, 5, 7), thros(7, 5, 7, 6, 7, 5, 7, 6, 7, 5, 7)},
        };
    }

    @Test
    @Parameters
    public void testException(final String[] input, final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> StreamingFilteringReducer.reducing(input))
            .isInstanceOf(exception.getClass())
            .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestException()
    {
        return new Object[]{
            new Object[]{null, new NullPointerException("duplicated must not be null")},
        };
    }

    @Test
    @Parameters
    public void testExceptionInt(final int[] input, final Exception exception) throws Exception
    {
        assertThatThrownBy(() -> StreamingFilteringReducer.reduce(input))
            .isInstanceOf(exception.getClass())
            .hasMessageContaining(exception.getMessage());
    }

    private Object parametersForTestExceptionInt()
    {
        return new Object[]{
            new Object[]{null, new NullPointerException("duplicated must not be null")},
        };
    }
}