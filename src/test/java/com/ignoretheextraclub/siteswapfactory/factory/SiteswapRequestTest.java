package com.ignoretheextraclub.siteswapfactory.factory;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.StreamingMappingReducingStartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoStartingStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * Created by caspar on 05/10/17.
 */
public class SiteswapRequestTest
{
    @Test
    public void WHEN_reduceIsFalse_EXPECT_noReducerIsReturned() throws Exception
    {
        final Object constructor = mock(Object.class);
        final Reducer reducer = mock(Reducer.class);
        final StartingStrategy startingStrategy = mock(StartingStrategy.class);
        final StartFinder startFinder = mock(StartFinder.class);

        final SiteswapRequest siteswapRequest = new SiteswapRequest(constructor, false, reducer, startFinder, startingStrategy);

        assertThat(siteswapRequest.getConstructor()).isSameAs(constructor);
        assertThat(siteswapRequest.getReducer()).isEmpty();
        assertThat(siteswapRequest.getStartFinder()).containsSame(startFinder);
        assertThat(siteswapRequest.getStartingStrategy()).isSameAs(startingStrategy);
    }

    @Test
    public void WHEN_reduceIsTrue_EXPECT_defaultReducerIsReturned() throws Exception
    {
        final Object constructor = mock(Object.class);
        final StartingStrategy startingStrategy = mock(StartingStrategy.class);
        final StartFinder startFinder = mock(StartFinder.class);

        final SiteswapRequest siteswapRequest = new SiteswapRequest(constructor, true, null, startFinder, startingStrategy);

        assertThat(siteswapRequest.getConstructor()).isSameAs(constructor);
        assertThat(siteswapRequest.getReducer()).contains(StreamingFilteringReducer.get());
        assertThat(siteswapRequest.getStartFinder()).containsSame(startFinder);
        assertThat(siteswapRequest.getStartingStrategy()).isSameAs(startingStrategy);
    }

    @Test
    public void WHEN_noStartingStrategy_AND_noStartFinder_EXPECT_NoOpStrategy_AND_noStartFinder() throws Exception
    {
        final Object constructor = mock(Object.class);
        final Reducer reducer = mock(Reducer.class);

        final SiteswapRequest siteswapRequest = new SiteswapRequest(constructor, true, reducer, null, null);

        assertThat(siteswapRequest.getConstructor()).isSameAs(constructor);
        assertThat(siteswapRequest.getReducer()).containsSame(reducer);
        assertThat(siteswapRequest.getStartFinder()).isEmpty();
        assertThat(siteswapRequest.getStartingStrategy()).isSameAs(NoStartingStrategy.get());
    }

    @Test
    public void WHEN_noStartFinder_AND_startStrategy_EXPECT_startFinder_AND_startStrategy() throws Exception
    {
        final Object constructor = mock(Object.class);
        final Reducer reducer = mock(Reducer.class);
        final StartingStrategy startingStrategy = mock(StartingStrategy.class);

        final SiteswapRequest siteswapRequest = new SiteswapRequest(constructor, true, reducer, null, startingStrategy);

        assertThat(siteswapRequest.getConstructor()).isSameAs(constructor);
        assertThat(siteswapRequest.getReducer()).containsSame(reducer);
        assertThat(siteswapRequest.getStartFinder()).containsSame(StreamingMappingReducingStartFinder.get());
        assertThat(siteswapRequest.getStartingStrategy()).isSameAs(startingStrategy);
    }

    @Test
    public void WHEN_constructorIsNull_EXPECT_nullPointerException() throws Exception
    {
        assertThatThrownBy(() -> new SiteswapRequest(null, false, null, null, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("constructor");
    }


}