package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class NameToTwoHandedSiteswapConstructorTest
{
    private SiteswapConstructor<TwoHandedSiteswap> internalConstructor;
    private NameToTwoHandedSiteswapConstructor constructor;

    @Before
    public void setUp() throws Exception
    {
        internalConstructor = mock(SiteswapConstructor.class);
        final Map<String, String> names = new HashMap<>();
        names.put("Flash", "55500");
        names.put("3 Ball Cascade", "3");

        constructor = new NameToTwoHandedSiteswapConstructor<>(names, internalConstructor);
    }

    @Test
    public void WHEN_hasMapping_EXPECT_mapsAndUsesInternal_AND_usesSiteswapRequestCorrectly() throws Exception
    {
        final TwoHandedSiteswap result = SiteswapFactory.getTwoHandedSiteswap("55500");

        final SiteswapRequest inputRequest = mock(SiteswapRequest.class, "inputRequest");
        final SiteswapRequest newRequest = mock(SiteswapRequest.class, "newRequest");
        final SiteswapRequestBuilder mockBuilder = mock(SiteswapRequestBuilder.class, "mockBuilder");

        when(inputRequest.getConstructor()).thenReturn("Flash");
        when(inputRequest.toBuilder()).thenReturn(mockBuilder);
        when(mockBuilder.createSiteswapRequest("55500")).thenReturn(newRequest);
        when(internalConstructor.apply(newRequest)).thenReturn(result);

        final Siteswap flash = constructor.apply(inputRequest);

        verify(inputRequest).getConstructor();
        verify(inputRequest).toBuilder();
        verify(mockBuilder).createSiteswapRequest("55500");
        verify(internalConstructor).apply(newRequest);
        verifyNoMoreInteractions(inputRequest, newRequest, mockBuilder, internalConstructor);

        Assertions.assertThat(result).isSameAs(flash);
    }

    @Test
    public void WHEN_mappingIsBad_EXPECT_deletesMapping() throws Exception
    {
        final TwoHandedSiteswap result = SiteswapFactory.getTwoHandedSiteswap("55500");

        final SiteswapRequest inputRequest = mock(SiteswapRequest.class, "inputRequest");
        final SiteswapRequest newRequest = mock(SiteswapRequest.class, "newRequest");
        final SiteswapRequestBuilder mockBuilder = mock(SiteswapRequestBuilder.class, "mockBuilder");

        when(inputRequest.getConstructor()).thenReturn("Flash");
        when(inputRequest.toBuilder()).thenReturn(mockBuilder);
        when(mockBuilder.createSiteswapRequest("55500")).thenReturn(newRequest);
        final InvalidSiteswapException firstException = new InvalidSiteswapException("firstException");
        when(internalConstructor.apply(newRequest)).thenThrow(firstException);

        assertThatThrownBy(() -> constructor.apply(inputRequest)).hasCause(firstException);
        assertThatThrownBy(() -> constructor.apply(inputRequest))
            .hasMessageContaining("[Flash]")
            .hasMessageContaining("not a known");

        verify(inputRequest, times(2)).getConstructor();
        verify(inputRequest).toBuilder();
        verify(mockBuilder).createSiteswapRequest("55500");
        verify(internalConstructor).apply(newRequest);
        verify(newRequest).getConstructor();
        verifyNoMoreInteractions(inputRequest, newRequest, mockBuilder, internalConstructor);
    }

}