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

package com.ignoretheextraclub.siteswapfactory.diagram.causal.impl;

import java.util.Set;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand.LEFT;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand.RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class DefaultCausalDiagramTest
{
    @Test
    public void testSingleNode() throws Exception
    {
        final DefaultSite start = new DefaultSite(0, RIGHT, 0);
        final Site finish = new DefaultSite(0, LEFT, 1);
        start.addCauses(finish);

        final CausalDiagram diagram = new DefaultCausalDiagram.Builder()
            .addCause(0, 0, 0, 1, RIGHT, LEFT)
            .build();

        final Set<Site> sites = diagram.getSites();

        assertThat(sites).containsExactlyInAnyOrder(start, finish);
    }

    @Test
    public void test31() throws Exception
    {
        final DefaultSite first = new DefaultSite(0, RIGHT, 0);
        final DefaultSite second = new DefaultSite(0, LEFT, 1);

        first.addCauses(second);
        second.addCauses(first);

        final CausalDiagram diagram = new DefaultCausalDiagram.Builder()
            .addCause(0, 0, 0, 1, RIGHT, LEFT)
            .addCause(0, 0, 1, 0, LEFT, RIGHT)
            .build();

        assertThat(diagram.getSites()).containsExactlyInAnyOrder(first, second);
    }

    @Test
    public void testFhs633() throws Exception
    {
        final DefaultSite six = new DefaultSite(0, RIGHT, 0.0);
        final DefaultSite firstThree = new DefaultSite(1, RIGHT, 0.5);
        final DefaultSite secondThree = new DefaultSite(0, LEFT, 1.0);

        six.addCauses(secondThree);
        secondThree.addCauses(firstThree);
        firstThree.addCauses(six);

        // Build
        final CausalDiagram diagram = new DefaultCausalDiagram.Builder()
            .addCause(0, 0, 0, 1, RIGHT, LEFT)
            .addCause(0, 1, 1, 0.5, LEFT, RIGHT)
            .addCause(1, 0, 0.5, 0, RIGHT, RIGHT)
            .build();

        // Get Sites

        assertThat(diagram.getSites()).containsExactlyInAnyOrder(six, firstThree, secondThree);
    }


}