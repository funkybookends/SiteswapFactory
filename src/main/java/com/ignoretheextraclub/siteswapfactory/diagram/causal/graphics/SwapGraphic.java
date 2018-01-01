package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.geom.Point2D;

public interface SwapGraphic extends Graphic
{
	Point2D getConnectionPointFor(Point2D point);

	Point2D getCenter();

	void translate(double dx, double dy);
}
