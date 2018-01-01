package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface Graphic
{
	/**
	 * Draws this graphic on the provided graphic
	 */
	void draw(Graphics2D graphics);

	/**
	 * Returns the bounding rectangle of the graphic.
	 * @see java.awt.Shape#getBounds()
	 * @return an integer <code>Rectangle</code> that completely encloses the <code>SvgGraphic</code>.
	 */
	Rectangle2D getBounds();

	/**
	 * Returns the max x, max y coordinate of the {@link Rectangle} in {@link #getBounds()}
	 * @return a point
	 */
	default Point2D getMinDocumentSize()
	{
		final Rectangle2D bounds = getBounds();

		return new Point2D.Double(
			bounds.getX() + bounds.getWidth(),
			bounds.getY() + bounds.getHeight()
		);
	}
}
