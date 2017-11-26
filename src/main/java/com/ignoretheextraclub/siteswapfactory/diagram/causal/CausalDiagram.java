package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CausalDiagram
{
	private Set<Site> sites;

	private CausalDiagram(final Set<Site> sites)
	{
		this.sites = new LinkedHashSet<>(sites);
	}

	public Set<Site> getSites()
	{
		return new LinkedHashSet<>(sites);
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final CausalDiagram that = (CausalDiagram) o;

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
		return "CausalDiagram{" +
			"sites=" + sites +
			'}';
	}

	public static class Site
	{
		private int juggler;
		private Hand hand;
		private Set<Site> causes = new HashSet<>();
		private double causalBeat;

		Site(final int juggler,
		     final Hand hand,
		     final double causalBeat)
		{
			this.juggler = juggler;
			this.hand = hand;
			this.causalBeat = causalBeat;
		}

		void setCauses(final Set<Site> causes)
		{
			this.causes = causes;
		}

		void addCauses(final Site cause)
		{
			this.causes.add(cause);
		}

		public boolean isVisible()
		{
			return causes != null;
		}

		public Set<Site> getCauses()
		{
			return causes;
		}

		public double getCausalBeat()
		{
			return causalBeat;
		}

		public Hand getHand()
		{
			return hand;
		}

		public int getJuggler()
		{
			return juggler;
		}

		public boolean sameSite(final Site site)
		{
			return juggler == site.juggler &&
				hand == site.hand &&
				causalBeat == site.causalBeat;
		}

		public boolean sameLocation(final Site site)
		{
			return juggler == site.juggler &&
				causalBeat == site.causalBeat;
		}

		@Override
		public boolean equals(final Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			final Site site = (Site) o;

			if (juggler != site.juggler) return false;
			if (Double.compare(site.causalBeat, causalBeat) != 0) return false;
			if (hand != site.hand) return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			int result;
			long temp;
			result = juggler;
			result = 31 * result + (hand != null ? hand.hashCode() : 0);
			temp = Double.doubleToLongBits(causalBeat);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public String toString()
		{
			final StringBuilder causesStr = new StringBuilder();

			causesStr.append("Site{" + "juggler=").append(juggler)
				.append(", hand=").append(hand)
				.append(", causalBeat=").append(causalBeat);

			for (final Site cause : causes)
			{
				causesStr.append(", causes={juggler=").append(cause.juggler)
					.append(", hand=").append(cause.hand)
					.append(", causalBeat=").append(cause.causalBeat)
					.append("}");
			}

			return causesStr.append('}')
				.toString();
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
		private List<Site> sites = new ArrayList<>();

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
			source.addCauses(causes);
			return this;
		}

		public CausalDiagram build()
		{
			return new CausalDiagram(new LinkedHashSet<>(sites));
		}

		private Site getNode(final int juggler, final double beat, final Hand hand)
		{
			final Site newSite = new Site(juggler, hand, beat);

			for (final Site site : sites)
			{
				if (site.sameSite(newSite))
				{
					return site;
				}
			}

			sites.add(newSite);

			return newSite;
		}

	}
}
