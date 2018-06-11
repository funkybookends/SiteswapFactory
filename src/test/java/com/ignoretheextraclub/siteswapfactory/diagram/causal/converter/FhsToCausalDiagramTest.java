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

package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.impl.DefaultCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.MAX_NUM_THROWS_DISPLAYED;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.MIN_NUM_HALF_ROTATIONS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.PREFERRED_NUM_THROWS;

public class FhsToCausalDiagramTest
{
    private static final Logger LOG = LoggerFactory.getLogger(FhsToCausalDiagramTest.class);

    private CausalDiagramProperties causalDiagramProperties;
    private FhsToCausalDiagram fhsToCausalDiagram;

    @Before
    public void setUp() throws Exception
    {
        causalDiagramProperties = new CausalDiagramProperties();
        causalDiagramProperties.set(PREFERRED_NUM_THROWS, 6);
        causalDiagramProperties.set(MIN_NUM_HALF_ROTATIONS, 2);
        causalDiagramProperties.set(MAX_NUM_THROWS_DISPLAYED, 10);

        fhsToCausalDiagram = new FhsToCausalDiagram(causalDiagramProperties);
    }

    @Test
    public void fhs5() throws Exception
    {
        final CausalDiagram result = fhsToCausalDiagram.apply(FourHandedSiteswapFactory.getFourHandedSiteswap("5"));

        final CausalDiagram expected = new DefaultCausalDiagram.Builder()
            .addCause(0, 1, 0, 0.5, Hand.RIGHT, Hand.RIGHT) // J1
            .addCause(0, 1, 1, 1.5, Hand.LEFT, Hand.LEFT)   // J1
            .addCause(0, 1, 2, 2.5, Hand.RIGHT, Hand.RIGHT) // J1
            .addCause(0, 1, 3, 3.5, Hand.LEFT, Hand.LEFT)   // J1
            .addCause(0, 1, 4, 4.5, Hand.RIGHT, Hand.RIGHT) // J1
            .addCause(0, 1, 5, 5.5, Hand.LEFT, Hand.LEFT)   // J1

            .addCause(1, 0, 0.5, 1, Hand.RIGHT, Hand.LEFT)  // J2
            .addCause(1, 0, 1.5, 2, Hand.LEFT, Hand.RIGHT)  // J2
            .addCause(1, 0, 2.5, 3, Hand.RIGHT, Hand.LEFT)  // J2
            .addCause(1, 0, 3.5, 4, Hand.LEFT, Hand.RIGHT)  // J2
            .addCause(1, 0, 4.5, 5, Hand.RIGHT, Hand.LEFT)  // J2
            .addCause(1, 0, 5.5, 6, Hand.LEFT, Hand.RIGHT)  // J2

            .setFullRotationBeat(2.0)

            .build();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void fhs567() throws Exception
    {
        final CausalDiagram result = fhsToCausalDiagram.apply(FourHandedSiteswapFactory.getFourHandedSiteswap("756"));

        final CausalDiagram expected = new DefaultCausalDiagram.Builder()
            .addCause(0, 1, 0, 1.5, Hand.RIGHT, Hand.LEFT) //   7
            .addCause(0, 0, 1, 2, Hand.LEFT, Hand.RIGHT) //     6
            .addCause(0, 1, 2, 2.5, Hand.RIGHT, Hand.RIGHT) //  5

            .addCause(0, 1, 3, 4.5, Hand.LEFT, Hand.RIGHT) //   7
            .addCause(0, 0, 4, 5, Hand.RIGHT, Hand.LEFT) //     6
            .addCause(0, 1, 5, 5.5, Hand.LEFT, Hand.LEFT) //    5

            .addCause(1, 0, 0.5, 1, Hand.RIGHT, Hand.LEFT) //   5
            .addCause(1, 0, 1.5, 3, Hand.LEFT, Hand.LEFT) //    7
            .addCause(1, 1, 2.5, 3.5, Hand.RIGHT, Hand.LEFT) // 6

            .addCause(1, 0, 3.5, 4, Hand.LEFT, Hand.RIGHT) //   5
            .addCause(1, 0, 4.5, 6, Hand.RIGHT, Hand.RIGHT) //  7
            .addCause(1, 1, 5.5, 6.5, Hand.LEFT, Hand.RIGHT) // 6

            .setFullRotationBeat(6.0)

            .build();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void fhs75() throws Exception
    {
        final CausalDiagram result = fhsToCausalDiagram.apply(FourHandedSiteswapFactory.getFourHandedSiteswap("75"));

        final CausalDiagram expected = new DefaultCausalDiagram.Builder()
            .addCause(0, 1, 0, 1.5, Hand.RIGHT, Hand.LEFT) //   7
            .addCause(0, 1, 1, 2.5, Hand.LEFT, Hand.RIGHT) //   7
            .addCause(0, 1, 2, 3.5, Hand.RIGHT, Hand.LEFT) //   7
            .addCause(0, 1, 3, 4.5, Hand.LEFT, Hand.RIGHT) //   7
            .addCause(0, 1, 4, 5.5, Hand.RIGHT, Hand.LEFT) //   7
            .addCause(0, 1, 5, 6.5, Hand.LEFT, Hand.RIGHT) //   7

            .addCause(1, 0, 0.5, 1, Hand.RIGHT, Hand.LEFT) //   5
            .addCause(1, 0, 1.5, 2, Hand.LEFT, Hand.RIGHT) //   5
            .addCause(1, 0, 2.5, 3, Hand.RIGHT, Hand.LEFT) //   5
            .addCause(1, 0, 3.5, 4, Hand.LEFT, Hand.RIGHT) //   5
            .addCause(1, 0, 4.5, 5, Hand.RIGHT, Hand.LEFT) //   5
            .addCause(1, 0, 5.5, 6, Hand.LEFT, Hand.RIGHT) //   5

            .setFullRotationBeat(2.0)

            .build();

        Assertions.assertThat(result).isEqualTo(expected);
    }
}