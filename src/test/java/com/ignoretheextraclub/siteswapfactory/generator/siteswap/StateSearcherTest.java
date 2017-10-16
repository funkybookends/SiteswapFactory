package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 09/10/17.
 */
public class StateSearcherTest
{
    @Test
    public void testBehaviour() throws Exception
    {
        // Prepare spys
        final Set<State> startingStates = spy(new HashSet<>());
        startingStates.add(XXX__);

        // Prepare mocks
        final Predicate<State[]> intermediatePredicate = mock(Predicate.class);
        final Predicate<State[]> resultPredicate = mock(Predicate.class);
        final SiteswapConstructor<TwoHandedSiteswap> siteswapConstructor = mock(SiteswapConstructor.class);
        final SiteswapRequestBuilder siteswapRequestBuilder = mock(SiteswapRequestBuilder.class);
        final SiteswapRequest siteswapRequest = mock(SiteswapRequest.class);
        final TwoHandedSiteswap siteswap = mock(TwoHandedSiteswap.class);

        // Prepare interactions
        final State[] expected = {XXX__};
        when(siteswapConstructor.accepts(new State[0])).thenReturn(true);
        when(intermediatePredicate.test(expected)).thenReturn(true);
        when(resultPredicate.test(expected)).thenReturn(true);
        when(siteswapRequestBuilder.createSiteswapRequest(expected)).thenReturn(siteswapRequest);
        when(siteswapConstructor.apply(siteswapRequest)).thenReturn(siteswap);

        // Construct
        final StateSearcher<TwoHandedSiteswap> stateSearcher = new StateSearcher<>(startingStates,
            1,
            intermediatePredicate,
            resultPredicate,
            siteswapConstructor,
            siteswapRequestBuilder);

        // Test
        assertThat(stateSearcher.hasNext()).isTrue();
        assertThat(stateSearcher.next()).isSameAs(siteswap);

        // Verify interactions
        verify(intermediatePredicate).test(expected);
        verify(resultPredicate).test(expected);
        verify(siteswapRequestBuilder).createSiteswapRequest(expected);
        verify(siteswapConstructor).apply(siteswapRequest);
        verify(siteswapConstructor).accepts(new State[0]);

        // Test no more
        assertThat(stateSearcher.hasNext()).isFalse();
        assertThatThrownBy(stateSearcher::next)
            .isInstanceOf(NoSuchElementException.class);

        verifyNoMoreInteractions(intermediatePredicate, resultPredicate, siteswapRequestBuilder, siteswapConstructor);
    }

    @Test
    public void WHEN_siteswapConstructor_doesNotAcceptStateArray_EXPECT_throwsException() throws Exception
    {
        final SiteswapConstructor<TwoHandedSiteswap> siteswapConstructor = mock(SiteswapConstructor.class);

        when(siteswapConstructor.accepts(new State[0])).thenReturn(false);

        assertThatThrownBy(() -> new StateSearcher<>(null,
            1,
            null,
            null,
            siteswapConstructor,
            null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(State.class.getCanonicalName());

        verify(siteswapConstructor).accepts(new State[0]);
    }

    // TODO add more
}