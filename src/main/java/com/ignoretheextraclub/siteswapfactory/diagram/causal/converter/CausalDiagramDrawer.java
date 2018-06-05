package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import java.awt.*;
import java.awt.geom.Point2D;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;

public interface CausalDiagramDrawer
{
	/**
	 * Draws a {@link CausalDiagram} on the graphics.
	 *
	 * @param causalDiagram    The causal diagram to draw
	 * @param graphicsSupplier A supplier for the graphics object to draw on. To be called only once.
	 * @param <T>              The type of graphics
	 *
	 * @return The graphics that was supplied by the {@code graphicsSupplier} with the causal diagram applied to it.
	 */
	<T extends Graphics2D> T apply(CausalDiagram causalDiagram, GraphicsSupplier<T> graphicsSupplier);

	interface GraphicsSupplier<T extends Graphics2D>
	{
		/**
		 * Create a graphics appropriatly sized for receiving objects up to the minSize.
		 *
		 * @param minSize The furthest position from 0,0 where a graphic might be drawn
		 *
		 * @return A graphics object to draw on.
		 */
		T createGraphics(Point2D minSize);
	}
}
