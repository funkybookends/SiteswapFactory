package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.HashSet;
import java.util.Set;

public class Site
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

	void addCauses(final com.ignoretheextraclub.siteswapfactory.diagram.causal.Site cause)
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

	public boolean sameSite(final com.ignoretheextraclub.siteswapfactory.diagram.causal.Site site)
	{
		return juggler == site.juggler &&
			hand == site.hand &&
			causalBeat == site.causalBeat;
	}

	public boolean overlaps(final com.ignoretheextraclub.siteswapfactory.diagram.causal.Site site)
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
