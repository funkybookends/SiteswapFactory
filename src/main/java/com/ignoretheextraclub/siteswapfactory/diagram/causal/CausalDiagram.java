package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections4.iterators.IteratorEnumeration;

public class CausalDiagram
{
    private List<JugglerSequence> jugglerSequences;

    private CausalDiagram(final List<JugglerSequence> jugglerSequences)
    {
        this.jugglerSequences = new ArrayList<>(jugglerSequences);
    }

    public List<JugglerSequence> getJugglerSequences()
    {
        return new ArrayList<>(jugglerSequences);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CausalDiagram that = (CausalDiagram) o;

        return jugglerSequences.equals(that.jugglerSequences);
    }

    @Override
    public int hashCode()
    {
        return jugglerSequences.hashCode();
    }

    @Override
    public String toString()
    {
        return "CausalDiagram{" +
            "jugglerSequences=" + jugglerSequences +
            '}';
    }

    public static class JugglerSequence
    {
        private Set<Site> sites = new TreeSet<>();

        private void addNode(final Site site)
        {
            this.sites.add(site);
        }

        public Enumeration<Site> enumerate()
        {
            return new IteratorEnumeration<>(new TreeSet<>(sites).iterator());
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final JugglerSequence that = (JugglerSequence) o;

            return sites.equals(that.sites);
        }

        @Override
        public int hashCode()
        {
            return sites.hashCode();
        }

        @Override
        public String toString()
        {
            return "JugglerSequence{" +
                "sites=" + sites +
                '}';
        }
    }

    public static class Site implements Comparable<Site>
    {
        private Hand hand;
        private Site causes;
        private double causalBeat;

        private Site(final Hand hand,
                     final double causalBeat)
        {
            this.hand = hand;
            this.causalBeat = causalBeat;
        }

        private void setCauses(final Site causes)
        {
            this.causes = causes;
        }

        public boolean isVisible()
        {
            return causes != null;
        }

        public Optional<Site> getCauses()
        {
            return Optional.ofNullable(causes);
        }

        public double getCausalBeat()
        {
            return causalBeat;
        }

        public Hand getHand()
        {
            return hand;
        }

        @Override
        public int compareTo(final Site other)
        {
            if (causalBeat != other.causalBeat)
            {
                return causalBeat < other.causalBeat ? -1 : 1;
            }
            if (hand == other.hand)
            {
                return 0;
            }
            return hand == Hand.RIGHT ? -1 : 1;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final Site site = (Site) o;

            if (Double.compare(site.causalBeat, causalBeat) != 0) return false;
            return hand == site.hand;
        }

        @Override
        public int hashCode()
        {
            int result;
            long temp;
            result = hand.hashCode();
            temp = Double.doubleToLongBits(causalBeat);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public String toString()
        {
            final String causesStr;

            if (causes == null)
            {
                causesStr = "";
            }
            else
            {
                causesStr = ", causes={hand=" + causes.hand + ", causalBeat=" + causes.causalBeat + "}";
            }

            return "Site{" +
                "hand=" + hand +
                ", causalBeat=" + causalBeat +
                causesStr +
                '}';
        }
    }

    public enum Hand
    {
        RIGHT,
        LEFT;

        public static Hand other(final Hand hand)
        {
            return hand == RIGHT ? LEFT : RIGHT;
        }
    }

    public static class Builder
    {
        private List<JugglerSequence> jugglerSequenceList = new ArrayList<>();

        public Builder()
        {
        }

        public Builder addCause(final int fromJuggler,
                                final int toJuggler,
                                final double fromCausalBeat,
                                final double toCausalBeat,
                                final Hand fromHand,
                                final Hand toHand)
        {
            final Site source = getNode(fromJuggler, fromCausalBeat, fromHand);
            final Site causes = getNode(toJuggler, toCausalBeat, toHand);
            source.setCauses(causes);
            return this;
        }

        public CausalDiagram build()
        {
            return new CausalDiagram(jugglerSequenceList);
        }

        private Site getNode(final int juggler, final double beat, final Hand hand)
        {
            final JugglerSequence sequence = getCausalSequence(juggler);

            final Site newSite = new Site(hand, beat);

            for (final Site site : sequence.sites)
            {
                if (site.equals(newSite))
                {
                    return site;
                }
            }

            sequence.addNode(newSite);
            return newSite;
        }

        private JugglerSequence getCausalSequence(final int juggler)
        {
            while (jugglerSequenceList.size() < juggler + 1)
            {
                jugglerSequenceList.add(new JugglerSequence());
            }
            return jugglerSequenceList.get(juggler);
        }

    }
}
