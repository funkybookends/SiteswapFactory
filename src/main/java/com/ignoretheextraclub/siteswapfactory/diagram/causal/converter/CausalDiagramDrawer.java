package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import java.awt.*;
import java.awt.geom.Point2D;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;

public interface CausalDiagramDrawer
{
	<T extends Graphics2D> T apply(CausalDiagram causalDiagram, GraphicsSupplier<T> graphicsSupplier);

	interface  GraphicsSupplier<T extends Graphics2D>
	{
		T apply(Point2D point);
	}
}
