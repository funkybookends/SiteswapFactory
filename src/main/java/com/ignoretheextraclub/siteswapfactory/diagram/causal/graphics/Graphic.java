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
