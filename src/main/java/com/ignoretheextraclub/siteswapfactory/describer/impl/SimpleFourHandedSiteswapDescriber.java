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
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.text.StrSubstitutor;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish.FourHandedSiteswapToHefflishSequence;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

public class SimpleFourHandedSiteswapDescriber implements DescriptionContributor
{
    private static final String DEFAULT_BASE_NAME = "i18n/simpleFourHandedSiteswapDescriber/simpleFourHandedSiteswapDescriber";
    private static final BiFunction<FourHandedSiteswap,Integer,String> DEFAULT_SEQUENCE_MAPPER = FourHandedSiteswapToHefflishSequence.get();

    private static final String NAME = "name";
    private static final String PERIOD = "period";
    private static final String NUM_CLUBS = "num_clubs";
    private static final String NUM_JUGGLERS = "num_jugglers";
    private static final String LEADER_NAME = "leader_name";
    private static final String FOLLOWER_NAME = "follower_name";
    private static final String LEADER_RIGHT_HAND_CLUBS = "leader_right_hand_clubs";
    private static final String LEADER_LEFT_HAND_CLUBS = "leader_left_hand_clubs";
    private static final String FOLLOWER_RIGHT_HAND_CLUBS = "follower_right_hand_clubs";
    private static final String FOLLOWER_LEFT_HAND_CLUBS = "follower_left_hand_clubs";
    private static final String LEADER_SEQUENCE = "leader_sequence";
    private static final String FOLLOWER_SEQUENCE = "follower_sequence";
    private static final String DEFAULT_FOLLOWER_NAME = "Becky";
    private static final String DEFAULT_LEADER_NAME = "Aidan";
    private static final String NUMBER = "number";
    private static final String NO_CLUBS = "noClubs";
    private static final String ONE_CLUB = "oneClub";
    private static final String MULTIPLE_CLUBS = "multipleClubs";
    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";
    private static final String LONG_DESCRIPTION = "longDescription";
    private static final String SHORT_DESCRIPTION = "shortDescription";

    private static final double[] LIMITS = {0,1,2};
    private static final String[] HANDS = {LEADER_RIGHT_HAND_CLUBS,
        FOLLOWER_RIGHT_HAND_CLUBS,
        LEADER_LEFT_HAND_CLUBS,
        FOLLOWER_LEFT_HAND_CLUBS};


    private final Map<Locale, ResourceBundle> resourceBundles;
    private final String leaderName;
    private final String followerName;
    private final BiFunction<FourHandedSiteswap, Integer, String> sequenceMapper;

    public SimpleFourHandedSiteswapDescriber(final String baseName,
                                             final String leaderName,
                                             final String followerName,
                                             final BiFunction<FourHandedSiteswap, Integer, String> sequenceMapper,
                                             final Locale... locales)
    {
        this.leaderName = leaderName;
        this.followerName = followerName;
        this.sequenceMapper = sequenceMapper;
        resourceBundles = Arrays.stream(locales)
            .collect(Collectors.toMap(Function.identity(), locale -> ResourceBundle.getBundle(baseName, locale)));
    }

    public SimpleFourHandedSiteswapDescriber()
    {
        this(DEFAULT_BASE_NAME, DEFAULT_LEADER_NAME, DEFAULT_FOLLOWER_NAME, DEFAULT_SEQUENCE_MAPPER, Locale.ENGLISH);
    }

    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return resourceBundles.keySet();
    }

    @Override
    public void contribute(final Siteswap siteswap, final Locale locale, final SimpleDescription.Builder builder)
    {
        final FourHandedSiteswap fourHandedSiteswap = (FourHandedSiteswap) siteswap;
        final Map<String, String> features = getFeatures(fourHandedSiteswap, builder, locale);

        final StrSubstitutor substitutor = new StrSubstitutor(features, PREFIX, SUFFIX);
        final ResourceBundle bundle = resourceBundles.get(locale);

        final String longDescription = substitutor.replace(bundle.getString(LONG_DESCRIPTION));
        final String shortDescrption = substitutor.replace(bundle.getString(SHORT_DESCRIPTION));

        builder.withLongDescription(longDescription);
        builder.withDescription(shortDescrption);
    }

    private Map<String, String> getFeatures(final FourHandedSiteswap siteswap,
                                            final SimpleDescription.Builder builder, final Locale locale)
    {
        final Map<String, String> features = new HashMap<>();

        features.put(NAME, builder.getSiteswapNames().get(0));
        features.put(NUM_CLUBS, String.valueOf(siteswap.getNumObjects()));
        features.put(PERIOD, String.valueOf(siteswap.getPeriod()));
        features.put(NUM_JUGGLERS, String.valueOf(siteswap.getNumJugglers()));
        features.put(LEADER_NAME, this.leaderName);
        features.put(FOLLOWER_NAME, this.followerName);
        features.put(LEADER_SEQUENCE, this.sequenceMapper.apply(siteswap, 0));
        features.put(FOLLOWER_SEQUENCE, this.sequenceMapper.apply(siteswap, 1));

        populateClubs(siteswap, features, locale);

        return features;
    }

    private void populateClubs(final FourHandedSiteswap siteswap,
                               final Map<String, String> features,
                               final Locale locale)
    {
        final String[] formats = new String[]
            {
                resourceBundles.get(locale).getString(NO_CLUBS),
                resourceBundles.get(locale).getString(ONE_CLUB),
                resourceBundles.get(locale).getString(MULTIPLE_CLUBS),
            };

        final ChoiceFormat choiceFormat = new ChoiceFormat(LIMITS, formats);

        for (int hand = 0; hand < HANDS.length; hand++)
        {
            final int startingNumberOfObjects = siteswap.getStartingNumberOfObjects(hand);

            final HashMap<String, Integer> map = new HashMap<>();
            map.put(NUMBER, startingNumberOfObjects);

            final String phrase = new StrSubstitutor(map, PREFIX, SUFFIX)
                .replace(choiceFormat.format(startingNumberOfObjects));

            features.put(HANDS[hand], phrase);
        }
    }
}
