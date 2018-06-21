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

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.factory.impl.PassingSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;

import static org.assertj.core.api.Assertions.assertThat;

public class SimplePassingSiteswapDescriberTest
{
	private static final Locale LOCALE = Locale.forLanguageTag("en");

	private DescriptionContributor describer;
	private SimpleDescription.Builder builder;

	@Before
	public void setUp() throws Exception
	{
		describer = new SimplePassingSiteswapDescriber();
	}

	@Test
	public void test6ClubTwoCount() throws Exception
	{
		final PassingSiteswap siteswap = (PassingSiteswap) PassingSiteswapFactory.getPassingSiteswap("<3p|3p><3|3>");
		builder = new SimpleDescription.Builder(siteswap);
		builder.addSiteswapName("6 Club Two Count");
		builder.withLocale(LOCALE);
		describer.contribute(siteswap, LOCALE, builder);
		final SimpleDescription simpleDescription = builder.createSimpleDescription();

		assertThat(simpleDescription.getDescription()).isEqualTo(
			"6 Club Two Count is a period 2 pattern with 6 clubs for 2 jugglers.");

		assertThat(simpleDescription.getLongDescription()).isEqualTo(
			"6 Club Two Count is a period 2 pattern with 6 clubs for 2 jugglers. " +
				"Aidan has 2 clubs in their right hand and one club in their left hand, starts in their right hand and their sequence is Pass, Self. " +
				  "Bob has 2 clubs in their right hand and one club in their left hand, starts in their right hand and their sequence is Pass, Self.");
	}

	@Test
	public void test7ClubPassPassSelfTwice() throws Exception
	{
		final PassingSiteswap siteswap = (PassingSiteswap) PassingSiteswapFactory.getPassingSiteswap("<3|4p1|3|4p3><4|3|4|3><4p2|3|4p4|3>");
		builder = new SimpleDescription.Builder(siteswap);
		builder.addSiteswapName("7 Club Pass Pass Self Twice");
		builder.withLocale(LOCALE);
		describer.contribute(siteswap, LOCALE, builder);
		final SimpleDescription simpleDescription = builder.createSimpleDescription();

		assertThat(simpleDescription.getDescription()).isEqualTo(
			"7 Club Pass Pass Self Twice is a period 3 pattern with 14 clubs for 4 jugglers.");

		assertThat(simpleDescription.getLongDescription()).isEqualTo(
			"7 Club Pass Pass Self Twice is a period 3 pattern with 14 clubs for 4 jugglers. " +
				"Aidan has 2 clubs in their right hand and one club in their left hand, starts in their right hand and their sequence is Self, Heff, Double. " +
				  "Bob has 2 clubs in their right hand and 2 clubs in their left hand, starts in their right hand and their sequence is Double, Self, Self. " +
			   "Caspar has 2 clubs in their right hand and one club in their left hand, starts in their right hand and their sequence is Self, Heff, Double. " +
				"David has 2 clubs in their right hand and 2 clubs in their left hand, starts in their right hand and their sequence is Double, Self, Self."
		);
	}
}