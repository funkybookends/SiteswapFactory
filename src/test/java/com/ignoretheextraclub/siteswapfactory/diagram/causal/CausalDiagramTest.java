package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Set;

import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram.Hand.LEFT;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram.Hand.RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class CausalDiagramTest
{
    @Test
    public void testSingleNode() throws Exception
    {
        final CausalDiagram.Site start = new CausalDiagram.Site(0, RIGHT, 0);
        final CausalDiagram.Site finish = new CausalDiagram.Site(0, LEFT, 1);
        start.addCauses(finish);

        final CausalDiagram diagram = new CausalDiagram.Builder()
            .addCause(0, 0, 0, 1, RIGHT, LEFT)
            .build();

        final Set<CausalDiagram.Site> sites = diagram.getSites();

        assertThat(sites).containsExactlyInAnyOrder(start, finish);
    }

    @Test
    public void test31() throws Exception
    {
        final CausalDiagram.Site first = new CausalDiagram.Site(0, RIGHT, 0);
        final CausalDiagram.Site second = new CausalDiagram.Site(0, LEFT, 1);

        first.addCauses(second);
        second.addCauses(first);

        final CausalDiagram diagram = new CausalDiagram.Builder()
            .addCause(0, 0, 0, 1, RIGHT, LEFT)
            .addCause(0, 0, 1, 0, LEFT, RIGHT)
            .build();

        assertThat(diagram.getSites()).containsExactlyInAnyOrder(first, second);
    }

    @Test
    public void testFhs633() throws Exception
    {
        final CausalDiagram.Site six = new CausalDiagram.Site(0, RIGHT, 0.0);
        final CausalDiagram.Site firstThree = new CausalDiagram.Site(1, RIGHT, 0.5);
        final CausalDiagram.Site secondThree = new CausalDiagram.Site(0, LEFT, 1.0);

        six.addCauses(secondThree);
        secondThree.addCauses(firstThree);
        firstThree.addCauses(six);

        // Build
        final CausalDiagram diagram = new CausalDiagram.Builder()
            .addCause(0, 0, 0, 1, RIGHT, LEFT)
            .addCause(0, 1, 1, 0.5, LEFT, RIGHT)
            .addCause(1, 0, 0.5, 0, RIGHT, RIGHT)
            .build();

        // Get Sites

        assertThat(diagram.getSites()).containsExactlyInAnyOrder(six, firstThree, secondThree);
    }


}