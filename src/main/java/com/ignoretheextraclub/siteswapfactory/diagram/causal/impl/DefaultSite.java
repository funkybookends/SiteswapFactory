package com.ignoretheextraclub.siteswapfactory.diagram.causal.impl;

import java.util.HashSet;
import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;

public class DefaultSite implements Site
{
	private int juggler;
	private Hand hand;
	private Set<Site> causes = new HashSet<>();
	private double causalBeat;

	public DefaultSite(final int juggler,
	            final Hand hand,
	            final double causalBeat)
	{
		this.juggler = juggler;
		this.hand = hand;
		this.causalBeat = causalBeat;
	}

	@Override
	public void addCauses(final Site cause)
	{
		this.causes.add(cause);
	}

	@Override
	public boolean isVisible()
	{
		return !causes.isEmpty();
	}

	@Override
	public Set<Site> getCauses()
	{
		return causes;
	}

	@Override
	public double getCausalBeat()
	{
		return causalBeat;
	}

	@Override
	public Hand getHand()
	{
		return hand;
	}

	@Override
	public int getJuggler()
	{
		return juggler;
	}

	@Override
	public boolean sameSite(final Site site)
	{
		return juggler == site.getJuggler() &&
			hand == site.getHand() &&
			causalBeat == site.getCausalBeat();
	}

	@Override
	public boolean overlaps(final Site site)
	{
		return juggler == site.getJuggler() &&
			causalBeat == site.getCausalBeat();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final DefaultSite site = (DefaultSite) o;

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
			causesStr.append(", causes={juggler=").append(cause.getJuggler())
				.append(", hand=").append(cause.getHand())
				.append(", causalBeat=").append(cause.getCausalBeat())
				.append("}");
		}

		return causesStr.append('}')
			.toString();
	}

}
