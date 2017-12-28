package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Set;

public interface CausalDiagram
{
	Set<Site> getSites();

	double getFullRotationBeat();

	default int getNumJugglers()
	{
		return getSites().stream().mapToInt(Site::getJuggler).max().orElse(0);
	}
}
