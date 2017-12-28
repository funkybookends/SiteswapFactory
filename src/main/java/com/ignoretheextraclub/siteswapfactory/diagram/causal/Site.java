package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Set;

public interface Site
{
	boolean isVisible();

	Set<Site> getCauses();

	double getCausalBeat();

	Hand getHand();

	int getJuggler();

	boolean sameSite(Site site);

	boolean overlaps(Site site);

	void addCauses(Site causes);
}
