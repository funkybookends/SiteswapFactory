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

import java.util.ArrayList;
import java.util.HashSet;
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

		if (Double.compare(that.fullRotationBeat, fullRotationBeat) != 0) return false;
		return sites.equals(that.sites);
	}

	@Override
	public int hashCode()
	{
		int result;
		long temp;
		result = sites.hashCode();
		temp = Double.doubleToLongBits(fullRotationBeat);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString()
	{
		return "DefaultCausalDiagram{" +
			"sites=" + sites +
			", fullRotationBeat=" + fullRotationBeat +
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

		public Builder setFullRotationBeat(final double fullRotationBeat)
		{
			this.fullRotationBeat = fullRotationBeat;
			return this;
		}

		public CausalDiagram build()
		{
			return new DefaultCausalDiagram(new LinkedHashSet<>(sites), fullRotationBeat);
		}

	}
}
