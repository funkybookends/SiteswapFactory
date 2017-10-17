package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 09/10/17.
 */
public class StatesToTwoHandedSiteswapConstructorTest
{

    @Test
    public void WHEN_noReduce_noSort_EXPECT_siteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = mock(SiteswapRequest.class);

        final VanillaState[] states = {XXXXXXX__};
        when(siteswapRequest.getConstructor()).thenReturn(states);
        when(siteswapRequest.getReducer()).thenReturn(Optional.empty());
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.empty());

        final TwoHandedSiteswap result = StatesToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedSiteswap expected = new TwoHandedSiteswap(states);

        Assertions.assertThat(result).isEqualTo(expected);

        verify(siteswapRequest).getConstructor();
        verify(siteswapRequest).getReducer();
        verify(siteswapRequest).getStartFinder();
        verifyNoMoreInteractions(siteswapRequest);
    }

    @Test
    public void WHEN_noReduce_sort_EXPECT_sortedSiteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = mock(SiteswapRequest.class);
        final StartFinder startFinder = mock(StartFinder.class);
        final StartingStrategy startingStrategy = mock(StartingStrategy.class);
        final StartFinderResult startFinderResult = mock(StartFinderResult.class);
        final State[] unsorted = new State[]{};
        final VanillaState[] states = {XXXXXXX__};

        when(siteswapRequest.getConstructor()).thenReturn(unsorted);
        when(siteswapRequest.getReducer()).thenReturn(Optional.empty());
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.of(startFinder));
        when(siteswapRequest.getStartingStrategy()).thenReturn(startingStrategy);
        when(startFinder.sort(unsorted, startingStrategy)).thenReturn(startFinderResult);
        when(startFinderResult.getSorted()).thenReturn(states);

        final TwoHandedSiteswap result = StatesToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedSiteswap expected = new TwoHandedSiteswap(states);

        Assertions.assertThat(result).isEqualTo(expected);

        verify(siteswapRequest).getConstructor();
        verify(siteswapRequest).getReducer();
        verify(siteswapRequest).getStartFinder();
        verify(siteswapRequest).getStartingStrategy();
        verify(startFinder).sort(unsorted, startingStrategy);
        verify(startFinderResult).getSorted();
        verifyNoMoreInteractions(siteswapRequest, startFinder, startFinderResult);
    }

    @Test
    public void WHEN_reduce_noSort_EXPECT_Reducedsiteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = mock(SiteswapRequest.class);
        final Reducer reducer = mock(Reducer.class);
        final State[] unreduced = new State[]{};

        final VanillaState[] states = {XXXXXXX__};
        when(siteswapRequest.getConstructor()).thenReturn(unreduced);
        when(siteswapRequest.getReducer()).thenReturn(Optional.of(reducer));
        when(reducer.reduce(unreduced)).thenReturn(states);
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.empty());

        final TwoHandedSiteswap result = StatesToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedSiteswap expected = new TwoHandedSiteswap(new VanillaState[]{XXXXXXX__});

        Assertions.assertThat(result).isEqualTo(expected);

        verify(siteswapRequest).getConstructor();
        verify(siteswapRequest).getReducer();
        verify(siteswapRequest).getStartFinder();
        verify(reducer).reduce(unreduced);
        verifyNoMoreInteractions(siteswapRequest, reducer);
    }

    @Test
    public void testAccepts() throws Exception
    {
        Assertions.assertThat(StatesToTwoHandedSiteswapConstructor.get().accepts(new State[]{})).isTrue();
        Assertions.assertThat(StatesToTwoHandedSiteswapConstructor.get().accepts(new VanillaState[]{})).isTrue();
        Assertions.assertThat(StatesToTwoHandedSiteswapConstructor.get().accepts(null)).isFalse();
        Assertions.assertThat(StatesToTwoHandedSiteswapConstructor.get().accepts(XXX__)).isFalse();
        Assertions.assertThat(StatesToTwoHandedSiteswapConstructor.get().accepts("sdfdf")).isFalse();
    }
}