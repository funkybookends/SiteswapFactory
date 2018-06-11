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
