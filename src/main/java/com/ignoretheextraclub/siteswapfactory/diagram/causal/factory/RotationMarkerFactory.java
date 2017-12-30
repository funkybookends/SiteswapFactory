package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory;

import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;

/**
 * A factory for rotation markers
 */
public interface RotationMarkerFactory
{
	/**
	 * Returns all the rotation markers for the causal diagram.
	 *
	 * @param causalDiagram The causal diagram.
	 * @return The set of rotation markers
	 * @see CausalDiagram#getFullRotationBeat()
	 */
	Set<RotationMarkerGraphic> getRotationMarkers(CausalDiagram causalDiagram);
}
