package com.ignoretheextraclub.siteswapfactory.diagram.causal.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;

public class DefaultCausalDiagram implements CausalDiagram
{
	private Set<Site> sites;
	private double fullRotationBeat;

	private DefaultCausalDiagram(final Set<Site> sites, final double fullRotationBeat)
	{
		this.sites = new LinkedHashSet<>(sites);
		this.fullRotationBeat = fullRotationBeat;
	}

	@Override
	public Set<Site> getSites()
	{
		return new LinkedHashSet<>(sites);
	}

	@Override
	public double getFullRotationBeat()
	{
		return fullRotationBeat;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final DefaultCausalDiagram that = (DefaultCausalDiagram) o;

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

	public static class Builder
	{
		private List<Site> sites = new ArrayList<>();
		private double fullRotationBeat;

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

		public Builder setFullRotationBeat(final double fullRotationBeat)
		{
			this.fullRotationBeat = fullRotationBeat;
			return this;
		}

		public CausalDiagram build()
		{
			return new DefaultCausalDiagram(new LinkedHashSet<>(sites), fullRotationBeat);
		}

		private Site getNode(final int juggler, final double beat, final Hand hand)
		{
			final DefaultSite newSite = new DefaultSite(juggler, hand, beat);

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
