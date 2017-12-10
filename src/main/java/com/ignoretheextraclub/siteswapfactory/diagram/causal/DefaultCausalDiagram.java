package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DefaultCausalDiagram implements CausalDiagram
{
	private Set<Site> sites;

	private DefaultCausalDiagram(final Set<Site> sites)
	{
		this.sites = new LinkedHashSet<>(sites);
	}

	@Override
	public Set<Site> getSites()
	{
		return new LinkedHashSet<>(sites);
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
			return new DefaultCausalDiagram(new LinkedHashSet<>(sites));
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
