package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 09/10/17.
 */
public class StringToTwoHandedSiteswapConstructorTest
{
    @Test
    public void WHEN_noReduce_noSort_EXPECT_siteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = mock(SiteswapRequest.class);
        final String string = "3";
        final VanillaState[] states = {XXX};

        when(siteswapRequest.getConstructor()).thenReturn(string);
        when(siteswapRequest.getReducer()).thenReturn(Optional.empty());
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.empty());

        final TwoHandedSiteswap result = StringToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
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
        final String string = "3";
        final VanillaThro[] thros = {VanillaThro.get(3)};
        final VanillaState[] unsorted = {XXX};

        when(siteswapRequest.getConstructor()).thenReturn(string);
        when(siteswapRequest.getReducer()).thenReturn(Optional.empty());
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.of(startFinder));
        when(siteswapRequest.getStartingStrategy()).thenReturn(startingStrategy);
        when(startFinder.sort(unsorted, startingStrategy)).thenReturn(startFinderResult);
        when(startFinderResult.getSorted()).thenReturn(unsorted);

        final TwoHandedSiteswap result = StringToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedSiteswap expected = new TwoHandedSiteswap(unsorted);

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
        final String string = "3";
        final VanillaState[] states = {XXX};
        final VanillaThro[] thros = {VanillaThro.get(3)};

        when(siteswapRequest.getConstructor()).thenReturn(string);
        when(siteswapRequest.getReducer()).thenReturn(Optional.of(reducer));
        when(reducer.reduce(thros)).thenReturn(thros);
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.empty());

        final TwoHandedSiteswap result = StringToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedSiteswap expected = new TwoHandedSiteswap(new VanillaState[]{XXX});

        Assertions.assertThat(result).isEqualTo(expected);

        verify(siteswapRequest).getConstructor();
        verify(siteswapRequest).getReducer();
        verify(siteswapRequest).getStartFinder();
        verify(reducer).reduce(thros);
        verifyNoMoreInteractions(siteswapRequest, reducer);
    }

    @Test
    public void testAccepts() throws Exception
    {
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(new State[]{})).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(new VanillaState[]{})).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(null)).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(XXX__)).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts("sdfdf")).isTrue();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts("")).isTrue();
    }
}