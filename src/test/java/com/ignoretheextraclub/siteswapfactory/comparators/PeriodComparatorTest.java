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

package com.ignoretheextraclub.siteswapfactory.comparators;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class PeriodComparatorTest
{
    private static final StringToTwoHandedSiteswapConstructor SC = StringToTwoHandedSiteswapConstructor.get();
    private static final PeriodComparator PERIOD_COMPARATOR = PeriodComparator.get();

    @Test
    @Parameters
    public void testEqual(final Siteswap first, final Siteswap second) throws Exception
    {
        assertThat(PERIOD_COMPARATOR.compare(first, second)).isEqualTo(0);
        assertThat(PERIOD_COMPARATOR.compare(second, first)).isEqualTo(0);
    }

    public Object parametersForTestEqual()
    {
        return new Object[]{
            new Object[]{SC.get("567"), SC.get("975")},
            new Object[]{SC.get("5"), SC.get("9")},
            new Object[]{SC.get("95"), SC.get("42")},
        };
    }

    @Test
    @Parameters
    public void testLessThan(final Siteswap less, final Siteswap more) throws Exception
    {
        assertThat(PERIOD_COMPARATOR.compare(less, more)).isLessThan(0);
        assertThat(PERIOD_COMPARATOR.compare(more, less)).isGreaterThan(0);
    }

    public Object parametersForTestLessThan()
    {
        return new Object[]{
            new Object[]{SC.get("567"), SC.get("97577")},
            new Object[]{SC.get("5"), SC.get("99")},
            new Object[]{SC.get("95"), SC.get("423")},
        };
    }
}