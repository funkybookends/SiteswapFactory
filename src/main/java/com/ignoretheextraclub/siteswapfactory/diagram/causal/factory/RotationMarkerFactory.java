package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory;

import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;

public interface RotationMarkerFactory
{
	Set<RotationMarkerGraphic> getRotationMarkers(CausalDiagram causalDiagram);
}
