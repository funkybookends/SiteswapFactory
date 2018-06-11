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

package com.ignoretheextraclub.siteswapfactory.describer.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;

import static org.mockito.Mockito.mock;

public class FromCanonicalNameDescriberTest
{
    private DescriptionContributor<TwoHandedVanillaSiteswap> contributor;
    private Collection<Locale> availableLocales = Arrays.asList(Locale.UK, Locale.GERMAN);
    private Map<String, List<String>> nameMap = new HashMap<>();

    @Before
    public void setUp() throws Exception
    {
        nameMap.put("55500", Arrays.asList("Flash", "Bang"));
        nameMap.put("3", Collections.singletonList("3 Ball Cascade"));

        contributor = new FromCanonicalNameDescriber<>(availableLocales, nameMap, FromCanonicalNameDescriber.vanillaSiteswapToCanonicalNameMapper());
    }

    @Test
    public void testLocales() throws Exception
    {
        Assertions.assertThat(contributor.getAvailableLocales()).containsExactlyInAnyOrder(Locale.UK, Locale.GERMAN);
    }

    @Test
    public void testPutsBothNamesForKnownLocale() throws Exception
    {
        final SimpleDescription.Builder builder = mock(SimpleDescription.Builder.class);

        contributor.contribute(TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap("55500"), Locale.UK, builder);

        final InOrder inOrder = Mockito.inOrder(builder);

        inOrder.verify(builder).addSiteswapName("Flash");
        inOrder.verify(builder).addSiteswapName("Bang");
    }


}