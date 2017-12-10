package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;

public interface SwapGraphic extends Graphic
{
	Point getConnectionPointFor(Point point);

	Point getCenter();

	void translate(int dx, int dy);
}
