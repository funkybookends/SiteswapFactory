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
 *
 *
 */

package com.ignoretheextraclub.siteswapfactory.describer.impl;

import java.util.Locale;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleFourHandedSiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleFourHandedSiteswapDescriberTest
{
    private static final Locale LOCALE = Locale.forLanguageTag("en");

    private DescriptionContributor describer = new SimpleFourHandedSiteswapDescriber();
    private SimpleDescription.Builder builder;

    @Test
    public void testLoadsLocale() throws Exception
    {
        describer = new SimpleFourHandedSiteswapDescriber();
    }

    @Test
    public void test975() throws Exception
    {
        final FourHandedSiteswap siteswap = FourHandedSiteswapFactory.getFourHandedSiteswap("975");
        builder = new SimpleDescription.Builder(siteswap);
        builder.addSiteswapName("Holy Grail");
        builder.withLocale(LOCALE);
        describer.contribute(siteswap, LOCALE, builder);
        final SimpleDescription simpleDescription = builder.createSimpleDescription();

        assertThat(simpleDescription.getDescription()).isEqualTo(
            "Holy Grail is a period 3 pattern with 7 clubs for 2 jugglers. " +
                "Aidan begins with Double, Zap, Pass, " +
                "and Becky responds with Pass, Double, Zap.");

        assertThat(simpleDescription.getLongDescription()).isEqualTo(
            "Holy Grail is a period 3 pattern with 7 clubs for 2 jugglers. " +
                "Aidan has 2 clubs in their right hand and 2 clubs in the left hand. " +
                "Aidan begins with the right hand and his sequence is " +
                "Double, Zap, Pass. " +
                "Becky has 2 clubs in her right hand, one club in the left hand and begins half a beat later. " +
                "Becky begins with the right hand and her sequence is " +
                "Pass, Double, Zap.");
    }
}