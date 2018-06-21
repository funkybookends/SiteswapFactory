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

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Determines the available locales by unioning the available locales of all the {@link DescriptionContributor}s provided.
 * @author Caspar Nonclercq
 */
public class LocalUnioningDelegatingDescriber extends DelegatingDescriber
{
    public LocalUnioningDelegatingDescriber(final List<DescriptionContributor> descriptionContributors)
    {
        super(descriptionContributors, getAvailableLocales(descriptionContributors));
    }

    private static Collection<Locale> getAvailableLocales(final List<DescriptionContributor> contributors)
    {
        return contributors.stream()
            .map(DescriptionContributor::getAvailableLocales)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }
}
