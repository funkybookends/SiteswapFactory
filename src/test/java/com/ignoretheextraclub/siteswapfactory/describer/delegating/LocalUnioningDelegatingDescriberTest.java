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
import java.util.Locale;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.VanillaSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocalUnioningDelegatingDescriberTest
{
    @Test
    public void test() throws Exception
    {
        final DescriptionContributor contributor1 = mock(DescriptionContributor.class);
        final DescriptionContributor contributor2 = mock(DescriptionContributor.class);

        when(contributor1.getAvailableLocales()).thenReturn(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
        when(contributor2.getAvailableLocales()).thenReturn(Arrays.asList(Locale.ENGLISH, Locale.CHINESE));

        final DescriptionContributor result = new LocalUnioningDelegatingDescriber(Arrays.asList(contributor1, contributor2));

        verify(contributor1).getAvailableLocales();
        verify(contributor2).getAvailableLocales();

        assertThat(result.getAvailableLocales()).containsExactlyInAnyOrder(Locale.ENGLISH, Locale.GERMAN, Locale.CHINESE);
    }
}