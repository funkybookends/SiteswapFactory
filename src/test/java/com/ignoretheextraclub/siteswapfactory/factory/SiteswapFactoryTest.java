package com.ignoretheextraclub.siteswapfactory.factory;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.impl.DefaultSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 09/10/17.
 */
public class SiteswapFactoryTest
{

    private SiteswapRequestBuilder siteswapRequestBuilder;
    private SiteswapConstructor<Siteswap> constructor1;
    private SiteswapConstructor<Siteswap> constructor2;
    private Object siteswap;
    private Siteswap result;
    private SiteswapRequest expectedSiteswapRequest;
    private ArgumentCaptor<SiteswapRequest> siteswapRequestArgumentCaptor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception
    {
        // Prepare mocks
        siteswapRequestBuilder = mock(SiteswapRequestBuilder.class);
        constructor1 = mock(SiteswapConstructor.class);
        constructor2 = mock(SiteswapConstructor.class);
        siteswap = mock(Object.class);
        result = mock(TwoHandedSiteswap.class);

        // Prepare Expected
        expectedSiteswapRequest = new SiteswapRequest(siteswap);

        // Prepare Argument Captors
        siteswapRequestArgumentCaptor = ArgumentCaptor.forClass(SiteswapRequest.class);
    }

    @Test
    public void testGetUsingObject() throws Exception
    {
        // Prepare interactions
        when(siteswapRequestBuilder.createSiteswapRequest(siteswap)).thenReturn(expectedSiteswapRequest);
        when(constructor1.accepts(siteswap)).thenReturn(true);
        when(constructor2.accepts(siteswap)).thenReturn(true);

        when(constructor1.apply(expectedSiteswapRequest)).thenThrow(new InvalidSiteswapException("testing"));
        when(constructor2.apply(expectedSiteswapRequest)).thenReturn(result);

        // Prepare test class
        final DefaultSiteswapFactory factory = new DefaultSiteswapFactory(Arrays.asList(constructor1, constructor2), siteswapRequestBuilder);

        // Test
        final Siteswap actual = factory.get(siteswap);

        assertThat(actual).isSameAs(result);

        // Verify Interactions
        verify(constructor1).accepts(siteswap);
        verify(constructor2).accepts(siteswap);
        verify(constructor1).apply(siteswapRequestArgumentCaptor.capture());
        verify(constructor2).apply(siteswapRequestArgumentCaptor.capture());
        verify(siteswapRequestBuilder).createSiteswapRequest(siteswap);

        // Verify arguments
        final List<SiteswapRequest> siteswapRequests = siteswapRequestArgumentCaptor.getAllValues();

        assertThat(siteswapRequests).hasSize(2);
        assertThat(siteswapRequests.get(0)).isEqualTo(siteswapRequests.get(1));

        assertThat(siteswapRequests.get(0)).isEqualToComparingFieldByFieldRecursively(expectedSiteswapRequest);

        verifyNoMoreInteractions(siteswapRequestBuilder, constructor1, constructor2, siteswap, result);
    }

    @Test
    public void testGetUsingSiteswapRequest() throws Exception
    {
        final SiteswapRequest providedSiteswapRequest = mock(SiteswapRequest.class);

        // Prepare interactions
        when(providedSiteswapRequest.getConstructor()).thenReturn(siteswap);
        when(constructor1.accepts(siteswap)).thenReturn(true);
        when(constructor2.accepts(siteswap)).thenReturn(true);

        when(constructor1.apply(providedSiteswapRequest)).thenThrow(new InvalidSiteswapException("testing"));
        when(constructor2.apply(providedSiteswapRequest)).thenReturn(result);

        // Prepare test class
        final DefaultSiteswapFactory factory = new DefaultSiteswapFactory(Arrays.asList(constructor1, constructor2), siteswapRequestBuilder);

        // Test
        final Siteswap actual = factory.apply(providedSiteswapRequest);

        assertThat(actual).isSameAs(result);

        // Verify Interactions
        verify(providedSiteswapRequest, times(3)).getConstructor();
        verify(constructor1).accepts(siteswap);
        verify(constructor2).accepts(siteswap);
        verify(constructor1).apply(siteswapRequestArgumentCaptor.capture());
        verify(constructor2).apply(siteswapRequestArgumentCaptor.capture());
        verify(siteswapRequestBuilder, never()).createSiteswapRequest(siteswap);

        // Verify arguments
        final List<SiteswapRequest> siteswapRequests = siteswapRequestArgumentCaptor.getAllValues();

        assertThat(siteswapRequests).hasSize(2);
        assertThat(siteswapRequests.get(0)).isEqualTo(siteswapRequests.get(1));

        assertThat(siteswapRequests.get(0)).isEqualToComparingFieldByFieldRecursively(providedSiteswapRequest);

        verifyNoMoreInteractions(siteswapRequestBuilder, constructor1, constructor2, siteswap, result);
    }

    @Test
    public void when_constructorDoesNotAccept_isNotAsked() throws Exception
    {
        final SiteswapRequest providedSiteswapRequest = mock(SiteswapRequest.class);

        // Prepare interactions
        when(providedSiteswapRequest.getConstructor()).thenReturn(siteswap);
        when(constructor1.accepts(siteswap)).thenReturn(false);
        when(constructor2.accepts(siteswap)).thenReturn(true);

        when(constructor2.apply(providedSiteswapRequest)).thenReturn(result);

        // Prepare test class
        final DefaultSiteswapFactory factory = new DefaultSiteswapFactory(Arrays.asList(constructor1, constructor2), siteswapRequestBuilder);

        // Test
        final Siteswap actual = factory.apply(providedSiteswapRequest);

        assertThat(actual).isSameAs(result);

        // Verify Interactions
        verify(providedSiteswapRequest, times(3)).getConstructor();
        verify(constructor1).accepts(siteswap);
        verify(constructor2).accepts(siteswap);
        verify(constructor1, never()).apply(any());
        verify(constructor2).apply(siteswapRequestArgumentCaptor.capture());
        verify(siteswapRequestBuilder, never()).createSiteswapRequest(siteswap);

        // Verify arguments
        final List<SiteswapRequest> siteswapRequests = siteswapRequestArgumentCaptor.getAllValues();

        assertThat(siteswapRequests).hasSize(1);
        assertThat(siteswapRequests.get(0)).isEqualToComparingFieldByFieldRecursively(providedSiteswapRequest);

        verifyNoMoreInteractions(siteswapRequestBuilder, constructor1, constructor2, siteswap, result);
    }

    @Test
    public void when_allThrowException_exceptionsAreVisibile() throws Exception
    {
        final SiteswapRequest providedSiteswapRequest = mock(SiteswapRequest.class);
        final InvalidSiteswapException exception1 = new InvalidSiteswapException("message1");
        final InvalidSiteswapException exception2 = new InvalidSiteswapException("message2");
        final String siteswap = "mangaledSiteswap";

        // Prepare interactions
        when(providedSiteswapRequest.getConstructor()).thenReturn(siteswap);
        when(constructor1.accepts(siteswap)).thenReturn(true);
        when(constructor2.accepts(siteswap)).thenReturn(true);

        when(constructor1.apply(providedSiteswapRequest)).thenThrow(exception1);
        when(constructor2.apply(providedSiteswapRequest)).thenThrow(exception2);

        // Prepare test class
        final DefaultSiteswapFactory factory = new DefaultSiteswapFactory(Arrays.asList(constructor1, constructor2), siteswapRequestBuilder);

        // Test
        assertThatThrownBy(() -> factory.apply(providedSiteswapRequest))
            .isInstanceOf(InvalidSiteswapException.class)
            .hasMessageContaining(siteswap)
            .hasCause(exception1)
            .hasSuppressedException(exception2);

        // Verify Interactions
        verify(providedSiteswapRequest, times(3)).getConstructor();
        verify(constructor1).accepts(siteswap);
        verify(constructor2).accepts(siteswap);
        verify(constructor1).apply(siteswapRequestArgumentCaptor.capture());
        verify(constructor2).apply(siteswapRequestArgumentCaptor.capture());
        verify(siteswapRequestBuilder, never()).createSiteswapRequest(siteswap);

        // Verify arguments
        final List<SiteswapRequest> siteswapRequests = siteswapRequestArgumentCaptor.getAllValues();

        assertThat(siteswapRequests).hasSize(2);
        assertThat(siteswapRequests.get(0)).isEqualTo(siteswapRequests.get(1));
        assertThat(siteswapRequests.get(0)).isEqualToComparingFieldByFieldRecursively(providedSiteswapRequest);

        verifyNoMoreInteractions(siteswapRequestBuilder, constructor1, constructor2, result);
    }

    // TODO static method testing
}