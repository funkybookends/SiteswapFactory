package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinder;
import com.ignoretheextraclub.siteswapfactory.sorters.StartFinderResult;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 09/10/17.
 */
public class StringToFourHandedSiteswapConstructorTest
{
    @Test
    public void WHEN_noReduce_noSort_EXPECT_siteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = mock(SiteswapRequest.class);
        final String string = "8";
        final VanillaState[] states = {XXXXXXXX__};

        when(siteswapRequest.getConstructor()).thenReturn(string);
        when(siteswapRequest.getReducer()).thenReturn(Optional.empty());
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.empty());

        final FourHandedSiteswap result = StringToFourHandedSiteswapConstructor.get().apply(siteswapRequest);
        final FourHandedSiteswap expected = new FourHandedSiteswap(states);

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
        final String string = "8";
        final VanillaState[] unsorted = {XXXXXXXX__};

        when(siteswapRequest.getConstructor()).thenReturn(string);
        when(siteswapRequest.getReducer()).thenReturn(Optional.empty());
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.of(startFinder));
        when(siteswapRequest.getStartingStrategy()).thenReturn(startingStrategy);
        when(startFinder.sort(unsorted, startingStrategy)).thenReturn(startFinderResult);
        when(startFinderResult.getSorted()).thenReturn(unsorted);

        final FourHandedSiteswap result = StringToFourHandedSiteswapConstructor.get().apply(siteswapRequest);
        final FourHandedSiteswap expected = new FourHandedSiteswap(unsorted);

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
        final String string = "8";
        final FourHandedSiteswapThro[] thros = {FourHandedSiteswapThro.get(8)};

        when(siteswapRequest.getConstructor()).thenReturn(string);
        when(siteswapRequest.getReducer()).thenReturn(Optional.of(reducer));
        when(reducer.reduce(thros)).thenReturn(thros);
        when(siteswapRequest.getStartFinder()).thenReturn(Optional.empty());

        final FourHandedSiteswap result = StringToFourHandedSiteswapConstructor.get().apply(siteswapRequest);
        final FourHandedSiteswap expected = new FourHandedSiteswap(new VanillaState[]{XXXXXXXX__});

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
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(new State[]{})).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(new VanillaState[]{})).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(null)).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(XXX__)).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts("sdfdf")).isTrue();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts("")).isTrue();
    }
}