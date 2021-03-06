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

package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;

public class DefaultRotationMarkerGraphic implements RotationMarkerGraphic
{
	private double minY;
	private double maxY;
	private double x;

	private Stroke stroke;
	private Paint paint;

	public DefaultRotationMarkerGraphic(final double minY,
	                                    final double maxY,
	                                    final double x,
	                                    final Stroke stroke,
	                                    final Paint paint)
	{
		this.minY = minY;
		this.maxY = maxY;
		this.x = x;
		this.stroke = Objects.requireNonNull(stroke, "stroke cannot be null");
		this.paint = Objects.requireNonNull(paint, "paint cannot be null");
	}

	@Override
	public void draw(final Graphics2D graphics)
	{
		graphics.setPaint(paint);
		graphics.setStroke(stroke);
		graphics.draw(new Line2D.Double(x, minY, x, maxY));
	}

	@Override
	public Rectangle2D getBounds()
	{
		return new Rectangle2D.Double(x, minY, 0, maxY - minY);
	}

	public static class Builder
	{
		private double minY;
		private double maxY;
		private double x;
		private Stroke stroke;
		private Paint paint;

		public Builder withMinY(final double minY)
		{
			this.minY = minY;
			return this;
		}

		public Builder withMaxY(final double maxY)
		{
			this.maxY = maxY;
			return this;
		}

		public Builder withX(final double x)
		{
			this.x = x;
			return this;
		}

		public Builder withStroke(final Stroke stroke)
		{
			this.stroke = stroke;
			return this;
		}

		public Builder withPaint(final Paint paint)
		{
			this.paint = paint;
			return this;
		}

		public DefaultRotationMarkerGraphic createDefaultRotationMarkerGraphic()
		{
			return new DefaultRotationMarkerGraphic(minY, maxY, x, stroke, paint);
		}
	}
}
