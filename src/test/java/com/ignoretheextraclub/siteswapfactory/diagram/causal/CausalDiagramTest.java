package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CausalDiagramTest
{
    @Test
    public void testSingleNode() throws Exception
    {
        final CausalDiagram diagram = new CausalDiagram.Builder()
            .addCause(0, 0, 0, 1, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT)
            .build();

        final List<CausalDiagram.JugglerSequence> jugglerSequences = diagram.getJugglerSequences();

        final Enumeration<CausalDiagram.Site> enumerate = jugglerSequences.get(0).enumerate();

        assertThat(enumerate.hasMoreElements()).isTrue();
        final CausalDiagram.Site firstSite = enumerate.nextElement();
        assertThat(enumerate.hasMoreElements()).isTrue();
        final CausalDiagram.Site secondSite = enumerate.nextElement();
        assertThat(enumerate.hasMoreElements()).isFalse();

        Assertions.assertThat(firstSite).extracting("hand", "causes", "causalBeat")
            .contains(CausalDiagram.Hand.RIGHT, Optional.of(secondSite), 0.0);

        assertThat(firstSite.isVisible()).isTrue();

        Assertions.assertThat(secondSite).extracting("hand", "causes", "causalBeat")
            .contains(CausalDiagram.Hand.LEFT, Optional.empty(), 1.0);

        assertThat(secondSite.isVisible()).isFalse();

        final String expectedFirstNodeToString = "Site{hand=RIGHT, causalBeat=0.0, causes={hand=LEFT, causalBeat=1.0}}";
        Assertions.assertThat(firstSite.toString()).isEqualTo(expectedFirstNodeToString);

        final String expectedSecondNodeToString = "Site{hand=LEFT, causalBeat=1.0}";
        Assertions.assertThat(secondSite.toString()).isEqualTo(expectedSecondNodeToString);
    }

    @Test
    public void test31() throws Exception
    {
        final CausalDiagram diagram = new CausalDiagram.Builder()
            .addCause(0, 0, 0, 1, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT)
            .addCause(0, 0, 1, 0, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.RIGHT)
            .build();

        final List<CausalDiagram.JugglerSequence> jugglerSequences = diagram.getJugglerSequences();

        final Enumeration<CausalDiagram.Site> enumerate = jugglerSequences.get(0).enumerate();

        assertThat(enumerate.hasMoreElements()).isTrue();
        final CausalDiagram.Site firstSite = enumerate.nextElement();
        assertThat(enumerate.hasMoreElements()).isTrue();
        final CausalDiagram.Site secondSite = enumerate.nextElement();
        assertThat(enumerate.hasMoreElements()).isFalse();

        Assertions.assertThat(firstSite).extracting("hand", "causes", "causalBeat")
            .contains(CausalDiagram.Hand.RIGHT, Optional.of(secondSite), 0.0);

        assertThat(firstSite.isVisible()).isTrue();

        Assertions.assertThat(secondSite).extracting("hand", "causes", "causalBeat")
            .contains(CausalDiagram.Hand.LEFT, Optional.of(firstSite), 1.0);

        assertThat(secondSite.isVisible()).isTrue();

        final String expectedFirstNodeToString = "Site{hand=RIGHT, causalBeat=0.0, causes={hand=LEFT, causalBeat=1.0}}";
        Assertions.assertThat(firstSite.toString()).isEqualTo(expectedFirstNodeToString);

        final String expectedSecondNodeToString = "Site{hand=LEFT, causalBeat=1.0, causes={hand=RIGHT, causalBeat=0.0}}";
        Assertions.assertThat(secondSite.toString()).isEqualTo(expectedSecondNodeToString);
    }

    @Test
    public void testFhs633() throws Exception
    {
        // Build
        final CausalDiagram diagram = new CausalDiagram.Builder()
            .addCause(0, 0, 0, 1, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT)
            .addCause(0, 1, 1, 0.5, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.RIGHT)
            .addCause(1, 0, 0.5, 0, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.RIGHT)
            .build();

        // Get Sites
        final List<CausalDiagram.JugglerSequence> jugglerSequences = diagram.getJugglerSequences();

        final Enumeration<CausalDiagram.Site> aJuggler = jugglerSequences.get(0).enumerate();
        final Enumeration<CausalDiagram.Site> bJuggler = jugglerSequences.get(1).enumerate();

        // From first Juggler
        assertThat(aJuggler.hasMoreElements()).isTrue();
        final CausalDiagram.Site aSix = aJuggler.nextElement();
        assertThat(aJuggler.hasMoreElements()).isTrue();
        final CausalDiagram.Site aThree = aJuggler.nextElement();
        assertThat(aJuggler.hasMoreElements()).isFalse();

        // From Second Juggler
        assertThat(bJuggler.hasMoreElements()).isTrue();
        final CausalDiagram.Site bThree = bJuggler.nextElement();
        assertThat(bJuggler.hasMoreElements()).isFalse();

        // Assert visibility
        assertThat(aSix.isVisible()).isTrue();
        assertThat(aThree.isVisible()).isTrue();
        assertThat(bThree.isVisible()).isTrue();

        Assertions.assertThat(aSix).extracting("hand", "causes", "causalBeat")
            .contains(CausalDiagram.Hand.RIGHT, Optional.of(aThree), 0.0);
        Assertions.assertThat(aThree).extracting("hand", "causes", "causalBeat")
            .contains(CausalDiagram.Hand.LEFT, Optional.of(bThree), 1.0);
        Assertions.assertThat(bThree).extracting("hand", "causes", "causalBeat")
            .contains(CausalDiagram.Hand.RIGHT, Optional.of(aSix), 0.5);

        final String expectedaSixToString = "Site{hand=RIGHT, causalBeat=0.0, causes={hand=LEFT, causalBeat=1.0}}";
        Assertions.assertThat(aSix.toString()).isEqualTo(expectedaSixToString);
        final String expectedaThreeToString = "Site{hand=LEFT, causalBeat=1.0, causes={hand=RIGHT, causalBeat=0.5}}";
        Assertions.assertThat(aThree.toString()).isEqualTo(expectedaThreeToString);
        final String expectedbThreeToString = "Site{hand=RIGHT, causalBeat=0.5, causes={hand=RIGHT, causalBeat=0.0}}";
        Assertions.assertThat(bThree.toString()).isEqualTo(expectedbThreeToString);
    }


}