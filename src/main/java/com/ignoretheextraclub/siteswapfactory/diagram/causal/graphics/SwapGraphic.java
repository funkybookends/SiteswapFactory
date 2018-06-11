/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A graphic for marking the location where a hand throws and catches an object.
 * This could be just an L or an R, it could have a circle.
 */
public interface SwapGraphic extends Graphic
{
	/**
	 * Given a straight line intending to connect at {@code point}, returns
	 * where the line should end to connect gracefully with the swap graphic.
	 * <p>
	 * For example, if a circle is to be drawn for this swap graphic, this should
	 * return the location on the circle closest to the provided {@code point}.
	 *
	 * @param point The point to connect to.
	 *
	 * @return Where to connect
	 */
	Point2D getConnectionPointFor(Point2D point);

	/**
	 * Returns the center of this swap graphic.
	 *
	 * @return The center.
	 */
	default Point2D getCenter()
	{
		final Rectangle2D bounds = getBounds();

		return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
	}

	/**
	 * Move the swap graphic.
	 *
	 * @param dx The x translation
	 * @param dy The y translation.
	 */
	void translate(double dx, double dy);
}
