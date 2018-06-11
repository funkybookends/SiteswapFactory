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

package com.ignoretheextraclub.siteswapfactory.generator.siteswap.helper;

import java.util.function.Predicate;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.generator.siteswap.SiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 09/10/17.
 */
public class TwoHandedVanillaSiteswapGeneratorTest
{ 
    private static final Logger LOG = LoggerFactory.getLogger(TwoHandedVanillaSiteswapGeneratorTest.class);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testGround() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<TwoHandedVanillaSiteswap> ground = TwoHandedSiteswapGenerator.ground(numObjects, maxThro, maxPeriod);

        Predicate<TwoHandedVanillaSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly())
            .and(isGrounded())
            .and(twoHandedSiteswap -> twoHandedSiteswap.getHighestThro().getNumBeats() < 9);

        assertThat(ground.generate().allMatch(test)).isTrue();
    }

    @Test
    public void testExcited() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<TwoHandedVanillaSiteswap> excited = TwoHandedSiteswapGenerator.excited(numObjects, maxThro, maxPeriod);

        Predicate<TwoHandedVanillaSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly())
            .and(isNotGrounded());

        assertThat(excited.generate().allMatch(test)).isTrue();
    }

    @Test
    public void testAll() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<TwoHandedVanillaSiteswap> all = TwoHandedSiteswapGenerator.all(numObjects, maxThro, maxPeriod);

        Predicate<TwoHandedVanillaSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly());

        assertThat(all.generate().allMatch(test)).isTrue();
    }

    @Test
    public void WHEN_maxThroLessThanNumObjects_EXPECT_exception() throws Exception
    {
        softly.assertThatThrownBy(() -> TwoHandedSiteswapGenerator.all(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> TwoHandedSiteswapGenerator.ground(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> TwoHandedSiteswapGenerator.excited(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO add more

    private Predicate<TwoHandedVanillaSiteswap> isNotGrounded()
    {
        return twoHandedSiteswap ->
        {
            if (!twoHandedSiteswap.isGrounded())
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed isNotGrounded", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedVanillaSiteswap> periodLessThan(final int maxPeriod)
    {
        return twoHandedSiteswap ->
        {
            if (twoHandedSiteswap.getPeriod() <= maxPeriod)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed periodLessThan", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedVanillaSiteswap> isGrounded()
    {
        return (twoHandedSiteswap) ->
        {
            if (twoHandedSiteswap.isGrounded())
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed isGrounded", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedVanillaSiteswap> classIsExactly()
    {
        return twoHandedSiteswap ->
        {
            if (twoHandedSiteswap.getClass() == TwoHandedVanillaSiteswap.class)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed classIsExactly", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedVanillaSiteswap> numObjectsEquals(final int numObjects)
    {
        return twoHandedSiteswap ->
        {
            if (twoHandedSiteswap.getNumObjects() == numObjects)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed numObjectsEquals", twoHandedSiteswap);
                return false;
            }
        };
    }
}