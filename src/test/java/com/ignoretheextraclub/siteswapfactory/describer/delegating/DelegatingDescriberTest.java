package com.ignoretheextraclub.siteswapfactory.describer.delegating;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.VanillaSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DelegatingDescriberTest
{
    private DescriptionContributor describer1;
    private DescriptionContributor describer2;
    private DelegatingDescriber<VanillaSiteswap> delegatingDescriber;
    private VanillaSiteswap siteswap = mock(VanillaSiteswap.class);

    @Before
    public void setUp() throws Exception
    {
        describer1 = mock(DescriptionContributor.class);
        describer2 = mock(DescriptionContributor.class);

        delegatingDescriber = new DelegatingDescriber<>(Arrays.asList(describer1, describer2),
            Arrays.asList(Locale.ENGLISH, Locale.GERMAN));

    }

    @Test
    public void GIVEN_localeIsNotAvailable_WHEN_describe_EXPECT_null() throws Exception
    {
        assertThat(delegatingDescriber.describe(siteswap, Locale.FRENCH)).isNull();
        assertThat(delegatingDescriber.describe(siteswap, Locale.CHINESE)).isNull();

        verifyZeroInteractions(describer1, describer2);
    }

    @Test
    public void GIVEN_localeIsAvailable_WHEN_describe_EXPECT_bothGetCalled() throws Exception
    {
        doAnswer(invocation ->
        {
            final SimpleDescription.Builder builder = (SimpleDescription.Builder) invocation.getArguments()[2];
            builder.addSiteswapName("foo");
            return null;
        })
            .when(describer1).contribute(same(siteswap), same(Locale.ENGLISH), any(SimpleDescription.Builder.class));

        final Description<VanillaSiteswap> describe = delegatingDescriber.describe(siteswap, Locale.ENGLISH);

        verify(describer1).contribute(same(siteswap), same(Locale.ENGLISH), any(SimpleDescription.Builder.class));
        verify(describer2).contribute(same(siteswap), same(Locale.ENGLISH), any(SimpleDescription.Builder.class));
    }

    @Test
    public void GIVEN_localeIsAvailable_WHEN_contribute_EXPECT_bothGetCalled() throws Exception
    {
        final SimpleDescription.Builder<VanillaSiteswap> builder = mock(SimpleDescription.Builder.class);

        delegatingDescriber.contribute(siteswap, Locale.ENGLISH, builder);

        verify(describer1).contribute(same(siteswap), same(Locale.ENGLISH), same(builder));
        verify(describer2).contribute(same(siteswap), same(Locale.ENGLISH), same(builder));
    }

    @Test
    public void WHEN_getAvailableLocales_EXPECT_injectedLocales() throws Exception
    {
        Assertions.assertThat(delegatingDescriber.getAvailableLocales()).containsExactlyInAnyOrder(Locale.ENGLISH, Locale.GERMAN);
    }

    @Test
    public void WHEN_constructed_EXPECT_availableLocalsCalculated() throws Exception
    {
        final DescriptionContributor<TwoHandedSiteswap> first = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> second = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> third = mock(DescriptionContributor.class);

        final DelegatingDescriber<TwoHandedSiteswap> delegatingDescriber = new DelegatingDescriber<>(Arrays.asList(first, second, third), Arrays.asList(Locale.CANADA));

        final Collection<Locale> result = delegatingDescriber.getAvailableLocales();
        final Collection<Locale> expected = Collections.singletonList(Locale.CANADA);

        Assertions.assertThat(result).containsAll(expected);
    }

    @Test
    public void GIVEN_threeDelegates_WHEN_describe_EXEPCT_calledInOrder() throws Exception
    {
        final DescriptionContributor<TwoHandedSiteswap> first = Mockito.spy(new DescriptionContributor<TwoHandedSiteswap>()
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
        final InOrder inOrder = Mockito.inOrder(first, second, third);

        final DelegatingDescriber<TwoHandedSiteswap> delegatingDescriber = new DelegatingDescriber<>(Arrays.asList(first, second, third), Collections.singletonList(Locale.CANADA));

        final TwoHandedSiteswap siteswap = TwoHandedSiteswapFactory.getTwoHandedSiteswap("3");
        final Description<TwoHandedSiteswap> describe = delegatingDescriber.describe(siteswap, Locale.CANADA);

        Assertions.assertThat(describe.getLocale()).isEqualTo(Locale.CANADA);
        Assertions.assertThat(describe.getSiteswap()).isSameAs(siteswap);

        inOrder.verify(first).contribute(Matchers.eq(siteswap), Matchers.eq(Locale.CANADA), Matchers.any());
        inOrder.verify(second).contribute(Matchers.eq(siteswap), Matchers.eq(Locale.CANADA), Matchers.any());
        inOrder.verify(third).contribute(Matchers.eq(siteswap), Matchers.eq(Locale.CANADA), Matchers.any());
    }

    @Test
    public void WHEN_localeNotAvailbale_EXPECT_returnsNull() throws Exception
    {
        final DescriptionContributor<TwoHandedSiteswap> first = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> second = mock(DescriptionContributor.class);
        final DescriptionContributor<TwoHandedSiteswap> third = mock(DescriptionContributor.class);
        final DelegatingDescriber<TwoHandedSiteswap> delegatingDescriber = new DelegatingDescriber<>(Arrays.asList(first, second, third), Collections.singletonList(Locale.CANADA));

        final TwoHandedSiteswap siteswap = TwoHandedSiteswapFactory.getTwoHandedSiteswap("3");
        final Description<TwoHandedSiteswap> describe = delegatingDescriber.describe(siteswap, Locale.ITALIAN);

        Assertions.assertThat(describe).isNull();

        Mockito.verifyNoMoreInteractions(first, second, third);
    }
}