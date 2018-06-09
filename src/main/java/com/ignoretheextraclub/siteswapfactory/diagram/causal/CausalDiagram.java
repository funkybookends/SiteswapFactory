package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Set;

/**
 * A object that provides information on a causal diagram.
 */
public interface CausalDiagram
{
	/**
	 * Get all the sites of this causal diagram.
	 * @return All the sites.
	 */
	Set<Site> getSites();

	/**
	 * Get the beat at which this Causal Diagram repeats itself.
	 * @return
	 */
	double getFullRotationBeat();

	/**
	 * Get the total number of jugglers in this causal diagram.
	 * @return The number of jugglers.
	 */
	default int getNumJugglers()
	{
		return getSites().stream().mapToInt(Site::getJuggler).max().orElse(0);
	}

	/**
	 * Get the beat of the last site in this causal diagram.
	 * @return The max causal beat.
	 */
	default double getMaxCausalBeat()
	{
		return getSites().stream().mapToDouble(Site::getCausalBeat).max().orElse(0);
	}
}
