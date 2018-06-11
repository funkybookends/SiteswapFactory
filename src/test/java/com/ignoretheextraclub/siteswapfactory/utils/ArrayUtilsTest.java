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

package com.ignoretheextraclub.siteswapfactory.utils;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;

/**
 Created by caspar on 29/07/17.
 */
public class ArrayUtilsTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void copy() throws Exception
    {
        final boolean[] positions = {true, false};
        final boolean[] result = ArrayUtils.copy(positions);
        softly.assertThat(positions).isEqualTo(result);
        softly.assertThat(positions).isNotSameAs(result);
    }

    @Test
    public void drop() throws Exception
    {
        softly.assertThat(ArrayUtils.drop(new boolean[]{true, false, true, true, false}, true))
            .isEqualTo(new boolean[]{false, true, true, false, true});
    }

    @Test
    public void getCopy() throws Exception
    {
        softly.assertThat(ArrayUtils.getRotatedCopy(StateTestUtils.states(XXX__, XX_X_, XXX__), 1))
              .isEqualTo(StateTestUtils.states(XX_X_, XXX__, XXX__));
    }

    @Test
    public void getRotation() throws Exception
    {
        softly.assertThat(ArrayUtils.getRotatedCopy(new State[]{XXX__, XX_X_}, 1)).isEqualTo(new State[]{XX_X_, XXX__});
        // todo add more
    }
}