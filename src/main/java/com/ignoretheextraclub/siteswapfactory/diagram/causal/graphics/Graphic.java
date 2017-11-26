package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;

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
	Rectangle getBounds();

	/**
	 * Returns the max x, max y coordinate of the {@link Rectangle} in {@link #getBounds()}
	 * @return a point
	 */
	default Point getMinDocumentSize()
	{
		final Rectangle bounds = getBounds();
		return new Point(
			(int) Math.ceil(bounds.getX() + bounds.getWidth()),
			(int) Math.ceil(bounds.getY() + bounds.getHeight())
		);
	}
}
