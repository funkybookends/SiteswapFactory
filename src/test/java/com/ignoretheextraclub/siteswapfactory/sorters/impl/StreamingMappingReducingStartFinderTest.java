package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoStartingStrategy;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 09/10/17.
 */
public class StreamingMappingReducingStartFinderTest
{
    @Test
    public void testThrosExceptionWhenStateArrayEmpty() throws Exception
    {
        assertThatThrownBy(() -> StreamingMappingReducingStartFinder.get().sort(new State[0], NoStartingStrategy.get()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testReducing() throws Exception
    {
        final StartingStrategy startingStrategy = mock(StartingStrategy.class);
        final State[] states = {XXX__, XX_X_};
        final State[] rotated = {XX_X_, XXX__};

        when(startingStrategy.test(states, rotated)).thenReturn(false);

        final StartFinderResult sort = StreamingMappingReducingStartFinder.get().sort(states, startingStrategy);
        final StartFinderResult expected = new SimpleStartFinderResult(rotated, 1, startingStrategy);

        assertThat(sort).isEqualTo(expected);
        verify(startingStrategy).test(states, rotated);
    }
}