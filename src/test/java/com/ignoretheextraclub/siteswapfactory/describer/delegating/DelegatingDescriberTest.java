/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
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
    private DelegatingDescriber delegatingDescriber;
    private VanillaSiteswap siteswap = mock(VanillaSiteswap.class);

    @Before
    public void setUp() throws Exception
    {
        describer1 = mock(DescriptionContributor.class);
        describer2 = mock(DescriptionContributor.class);

        delegatingDescriber = new DelegatingDescriber(Arrays.asList(describer1, describer2),
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

        final Description describe = delegatingDescriber.describe(siteswap, Locale.ENGLISH);

        verify(describer1).contribute(same(siteswap), same(Locale.ENGLISH), any(SimpleDescription.Builder.class));
        verify(describer2).contribute(same(siteswap), same(Locale.ENGLISH), any(SimpleDescription.Builder.class));
    }

    @Test
    public void GIVEN_localeIsAvailable_WHEN_contribute_EXPECT_bothGetCalled() throws Exception
    {
        final SimpleDescription.Builder builder = mock(SimpleDescription.Builder.class);

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
        final DescriptionContributor first = mock(DescriptionContributor.class);
        final DescriptionContributor second = mock(DescriptionContributor.class);
        final DescriptionContributor third = mock(DescriptionContributor.class);

        final DelegatingDescriber delegatingDescriber = new DelegatingDescriber(Arrays.asList(first, second, third), Arrays.asList(Locale.CANADA));

        final Collection<Locale> result = delegatingDescriber.getAvailableLocales();
        final Collection<Locale> expected = Collections.singletonList(Locale.CANADA);

        Assertions.assertThat(result).containsAll(expected);
    }

    @Test
    public void GIVEN_threeDelegates_WHEN_describe_EXEPCT_calledInOrder() throws Exception
    {
        final DescriptionContributor first = Mockito.spy(new DescriptionContributor()
        {
            @Override
            public Collection<Locale> getAvailableLocales()
            {
                return Arrays.asList(Locale.CANADA, Locale.CANADA_FRENCH, Locale.CHINA);
            }

            @Override
            public void contribute(final Siteswap siteswap, final Locale locale, final SimpleDescription.Builder builder)
            {
                builder.addSiteswapName("title"); // needed to avoid NPE
            }
        });

        final DescriptionContributor second = mock(DescriptionContributor.class);
        final DescriptionContributor third = mock(DescriptionContributor.class);
        final InOrder inOrder = Mockito.inOrder(first, second, third);

        final DelegatingDescriber delegatingDescriber = new DelegatingDescriber(Arrays.asList(first, second, third), Collections.singletonList(Locale.CANADA));

        final TwoHandedVanillaSiteswap siteswap = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("3");
        final Description describe = delegatingDescriber.describe(siteswap, Locale.CANADA);

        Assertions.assertThat(describe.getLocale()).isEqualTo(Locale.CANADA);
        Assertions.assertThat(describe.getSiteswap()).isSameAs(siteswap);

        inOrder.verify(first).contribute(Matchers.eq(siteswap), Matchers.eq(Locale.CANADA), Matchers.any());
        inOrder.verify(second).contribute(Matchers.eq(siteswap), Matchers.eq(Locale.CANADA), Matchers.any());
        inOrder.verify(third).contribute(Matchers.eq(siteswap), Matchers.eq(Locale.CANADA), Matchers.any());
    }

    @Test
    public void WHEN_localeNotAvailbale_EXPECT_returnsNull() throws Exception
    {
        final DescriptionContributor first = mock(DescriptionContributor.class);
        final DescriptionContributor second = mock(DescriptionContributor.class);
        final DescriptionContributor third = mock(DescriptionContributor.class);
        final DelegatingDescriber delegatingDescriber = new DelegatingDescriber(Arrays.asList(first, second, third), Collections.singletonList(Locale.CANADA));

        final TwoHandedVanillaSiteswap siteswap = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("3");
        final Description describe = delegatingDescriber.describe(siteswap, Locale.ITALIAN);

        Assertions.assertThat(describe).isNull();

        Mockito.verifyNoMoreInteractions(first, second, third);
    }
}