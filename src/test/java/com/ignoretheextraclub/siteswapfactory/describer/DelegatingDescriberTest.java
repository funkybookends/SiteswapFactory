package com.ignoretheextraclub.siteswapfactory.describer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InOrder;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 29/10/17.
 */
public class DelegatingDescriberTest
{

    @Test
    public void WHEN_constructed_EXPECT_availableLocalsCalculated() throws Exception
    {
        final DescriptionContributor<TwoHandedSiteswap> first = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> second = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> third = mock(DescriptionContributor.class);

        when(first.getAvailableLocales()).thenReturn(Arrays.asList(Locale.CANADA, Locale.CANADA_FRENCH, Locale.CHINA));
        when(second.getAvailableLocales()).thenReturn(Arrays.asList(Locale.GERMAN, Locale.CANADA));
        when(third.getAvailableLocales()).thenReturn(Arrays.asList(Locale.CANADA, Locale.US, Locale.ITALIAN));

        final DelegatingDescriber<TwoHandedSiteswap> delegatingDescriber = new DelegatingDescriber<>(Arrays.asList(first, second, third));

        // Verify called during construction and only once
        verify(first).getAvailableLocales();
        verify(second).getAvailableLocales();
        verify(third).getAvailableLocales();

        final Collection<Locale> result = delegatingDescriber.getAvailableLocales();
        final Collection<Locale> expected = Collections.singletonList(Locale.CANADA);

        Assertions.assertThat(result).containsAll(expected);
    }

    @Test
    public void GIVEN_threeDelegates_WHEN_describe_EXEPCT_calledInOrder() throws Exception
    {
        final DescriptionContributor<TwoHandedSiteswap> first = spy(new DescriptionContributor<TwoHandedSiteswap>()
        {
            @Override
            public Collection<Locale> getAvailableLocales()
            {
                return Arrays.asList(Locale.CANADA, Locale.CANADA_FRENCH, Locale.CHINA);
            }

            @Override
            public void contribute(final TwoHandedSiteswap siteswap, final Locale locale, final SimpleDescription.Builder<TwoHandedSiteswap> builder)
            {
                builder.addSiteswapName("title"); // needed to avoid NPE
            }
        });

        final DescriptionContributor<TwoHandedSiteswap> second = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> third = mock(DescriptionContributor.class);
        final InOrder inOrder = inOrder(first, second, third);

        when(first.getAvailableLocales()).thenReturn(Arrays.asList(Locale.CANADA, Locale.CANADA_FRENCH, Locale.CHINA));
        when(second.getAvailableLocales()).thenReturn(Arrays.asList(Locale.GERMAN, Locale.CANADA));
        when(third.getAvailableLocales()).thenReturn(Arrays.asList(Locale.CANADA, Locale.US, Locale.ITALIAN));

        final DelegatingDescriber<TwoHandedSiteswap> delegatingDescriber = new DelegatingDescriber<>(Arrays.asList(first, second, third));

        // Verify called during construction and only once
        verify(first).getAvailableLocales();
        verify(second).getAvailableLocales();
        verify(third).getAvailableLocales();

        final TwoHandedSiteswap siteswap = SiteswapFactory.getTwoHandedSiteswap("3");
        final Description<TwoHandedSiteswap> describe = delegatingDescriber.describe(siteswap, Locale.CANADA);

        Assertions.assertThat(describe.getLocale()).isEqualTo(Locale.CANADA);
        Assertions.assertThat(describe.getSiteswap()).isSameAs(siteswap);

        inOrder.verify(first).contribute(eq(siteswap), eq(Locale.CANADA), any());
        inOrder.verify(second).contribute(eq(siteswap), eq(Locale.CANADA), any());
        inOrder.verify(third).contribute(eq(siteswap), eq(Locale.CANADA), any());
    }

    @Test
    public void WHEN_localeNotAvailbale_EXPECT_returnsNull() throws Exception
    {
        final DescriptionContributor<TwoHandedSiteswap> first = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> second = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> third = mock(DescriptionContributor.class);

        when(first.getAvailableLocales()).thenReturn(Arrays.asList(Locale.CANADA, Locale.CANADA_FRENCH, Locale.CHINA));
        when(second.getAvailableLocales()).thenReturn(Arrays.asList(Locale.GERMAN, Locale.CANADA));
        when(third.getAvailableLocales()).thenReturn(Arrays.asList(Locale.CANADA, Locale.US, Locale.ITALIAN));

        final DelegatingDescriber<TwoHandedSiteswap> delegatingDescriber = new DelegatingDescriber<>(Arrays.asList(first, second, third));

        // Verify called during construction and only once
        verify(first).getAvailableLocales();
        verify(second).getAvailableLocales();
        verify(third).getAvailableLocales();

        final TwoHandedSiteswap siteswap = SiteswapFactory.getTwoHandedSiteswap("3");
        final Description<TwoHandedSiteswap> describe = delegatingDescriber.describe(siteswap, Locale.ITALIAN);

        Assertions.assertThat(describe).isNull();

        verifyNoMoreInteractions(first, second, third);
    }
}