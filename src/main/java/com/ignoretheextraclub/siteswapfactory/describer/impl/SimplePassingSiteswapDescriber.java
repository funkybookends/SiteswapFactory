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

import java.text.ChoiceFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.text.StrSubstitutor;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish.PassingSiteswapToHefflishSequence;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;

public class SimplePassingSiteswapDescriber implements DescriptionContributor
{
	private static final String DEFAULT_BASE_NAME = "i18n/simplePassingSiteswapDescriber/simplePassingSiteswapDescriber";
	private static final List<String> DEFAULT_JUGGLER_NAMES = Arrays.asList("Aidan", "Bob", "Caspar", "David", "Ewan", "Fredrick", "George", "Hugo", "Ian", "John", "Ken", "Leon");
	private static final List<Hand> DEFAULT_STARTING_HANDS = Arrays.asList(Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT, Hand.RIGHT);
	private static final BiFunction<PassingSiteswap, Integer, String> DEFAULT_SEQUENCE_MAPPER = PassingSiteswapToHefflishSequence.get();

	private static final String NAME = "name";
	private static final String PERIOD = "period";
	private static final String NUM_CLUBS = "num_clubs";
	private static final String NUM_JUGGLERS = "num_jugglers";
	private static final String JUGGLER_RIGHT_HAND_CLUBS = "juggler_right_hand_clubs";
	private static final String JUGGLER_LEFT_HAND_CLUBS = "juggler_left_hand_clubs";
	private static final String NUMBER = "number";
	private static final String JUGGLER_SEQUENCE = "juggler_sequence";
	private static final String JUGGLER_STARTING_HAND = "juggler_starting_hand";
	private static final String LONG_DESCRIPTION = "longDescription";
	private static final String SHORT_DESCRIPTION = "shortDescription";
	private static final String PERSON_DESCRIPTION = "personDescription";
	private static final String PREFIX = "{";
	private static final String SUFFIX = "}";
	private static final String JUGGLER_NAME = "juggler_name";
	private static final String NO_CLUBS = "noClubs";
	private static final String ONE_CLUB = "oneClub";
	private static final String MULTIPLE_CLUBS = "multipleClubs";
	private static final double[] LIMITS = {0,1,2};

	private final Map<Locale, ResourceBundle> bundles;
	private final List<String> names;
	private final BiFunction<PassingSiteswap, Integer, String> sequenceMapper;
	private final List<Hand> startingHands;

	public SimplePassingSiteswapDescriber(final String baseName,
	                                      final List<String> names,
	                                      final BiFunction<PassingSiteswap, Integer, String> sequenceMapper,
	                                      final List<Hand> startingHands, final Locale... locales)
	{
		this.names = names;
		this.sequenceMapper = sequenceMapper;
		this.startingHands = startingHands;
		this.bundles = Arrays.stream(locales)
			.collect(Collectors.toMap(Function.identity(), locale -> ResourceBundle.getBundle(baseName, locale)));
	}

	public SimplePassingSiteswapDescriber()
	{
		this(DEFAULT_BASE_NAME, DEFAULT_JUGGLER_NAMES, DEFAULT_SEQUENCE_MAPPER, DEFAULT_STARTING_HANDS, Locale.ENGLISH);
	}

	@Override
	public Collection<Locale> getAvailableLocales()
	{
		return bundles.keySet();
	}

	@Override
	public void contribute(final Siteswap siteswap,
	                       final Locale locale,
	                       final SimpleDescription.Builder builder)
	{
		final PassingSiteswap passingSiteswap = (PassingSiteswap) siteswap;
		final Map<String, String> features = getFeatures(passingSiteswap, builder);

		final StrSubstitutor strSubstitutor = new StrSubstitutor(features, PREFIX, SUFFIX);
		final ResourceBundle resourceBundle = bundles.get(locale);

		final StringBuilder longDescription = new StringBuilder(strSubstitutor.replace(resourceBundle.getString(LONG_DESCRIPTION)));
		final String shortDescrption = strSubstitutor.replace(resourceBundle.getString(SHORT_DESCRIPTION));

		for (int juggler = 0; juggler < passingSiteswap.getNumJugglers(); juggler++)
		{
			longDescription.append(" ").append(getPersonDescription(passingSiteswap, juggler, resourceBundle));
		}

		builder.withLongDescription(longDescription.toString())
			.withDescription(shortDescrption);
	}

	private String getPersonDescription(final PassingSiteswap siteswap,
	                                    final int juggler,
	                                    final ResourceBundle resourceBundle)
	{
		final Map<String, String> jugglerFeatures = getJugglerFeatures(siteswap, juggler, resourceBundle);

		final StrSubstitutor strSubstitutor = new StrSubstitutor(jugglerFeatures, PREFIX, SUFFIX);

		return strSubstitutor.replace(resourceBundle.getString(PERSON_DESCRIPTION));
	}

	private Map<String, String> getJugglerFeatures(final PassingSiteswap siteswap, final int juggler, final ResourceBundle bundle)
	{
		final HashMap<String, String> jugglerFeatures = new HashMap<>();

		jugglerFeatures.put(JUGGLER_NAME, names.get(juggler));
		jugglerFeatures.put(JUGGLER_RIGHT_HAND_CLUBS, getClubs(siteswap.getStartingNumberOfObjects(juggler), bundle));
		jugglerFeatures.put(JUGGLER_LEFT_HAND_CLUBS, getClubs(siteswap.getStartingNumberOfObjects(juggler + siteswap.getNumJugglers()), bundle));
		jugglerFeatures.put(JUGGLER_STARTING_HAND, startingHands.get(juggler).name().toLowerCase(Locale.ENGLISH));
		jugglerFeatures.put(JUGGLER_SEQUENCE, sequenceMapper.apply(siteswap, juggler));

		return jugglerFeatures;
	}

	private Map<String, String> getFeatures(final PassingSiteswap siteswap,
	                                        final SimpleDescription.Builder builder)
	{
		final Map<String, String> features = new HashMap<>();

		features.put(NAME, builder.getSiteswapNames().get(0));
		features.put(NUM_CLUBS, String.valueOf(siteswap.getNumObjects()));
		features.put(PERIOD, String.valueOf(siteswap.getPeriod()));
		features.put(NUM_JUGGLERS, String.valueOf(siteswap.getNumJugglers()));

		return features;
	}

	public String getClubs(final int numClubs, final ResourceBundle resourceBundle)
	{

		final String[] formats = new String[]
			{
				resourceBundle.getString(NO_CLUBS),
				resourceBundle.getString(ONE_CLUB),
				resourceBundle.getString(MULTIPLE_CLUBS),
			};

		final ChoiceFormat choiceFormat = new ChoiceFormat(LIMITS, formats);

		final HashMap<String, Integer> map = new HashMap<>();

		map.put(NUMBER, numClubs);

		return new StrSubstitutor(map, PREFIX, SUFFIX).replace(choiceFormat.format(numClubs));
	}

}
