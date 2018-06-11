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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.impl.DefaultCausalDiagram;


/**
 * A position on a {@link DefaultCausalDiagram}.
 *
 * @author Caspar Nonclercq
 */
public class DefaultSwapGraphic implements SwapGraphic
{
	private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
	private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1);
	private static final int DEFUALT_BUFFER_BETWEEN_LABEL_AND_CIRCLE = 0;
	private static final Paint DEFAULT_PAINT = Color.DARK_GRAY;

	private double xCenter;
	private double yCenter;

	private Character label;
	private Font labelFont;
	private Stroke circleStroke;
	private double connectionPointDistanceFromCenter;
	private boolean drawCircle;
	private boolean drawLabel;
	private Paint labelPaint;
	private Stroke labelStroke;
	private Paint circlePaint;
	private double labelXOffsetDistance;
	private double labelYOffsetDistance;

	public DefaultSwapGraphic(final double xCenter,
	                          final double yCenter,
	                          final boolean drawLabel,
	                          final Character label,
	                          final Font labelFont,
	                          final Paint labelPaint,
	                          final Stroke labelStroke,
	                          final boolean drawCircle,
	                          final Stroke circleStroke,
	                          final Paint circlePaint,
	                          final double connectionPointDistanceFromCenter,
	                          final double labelXOffsetDistance,
	                          final double labelYOffsetDistance)
	{
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.label = Objects.requireNonNull(label, "label cannot be null");
		this.labelFont = Objects.requireNonNull(labelFont, "labelFont cannot be null");
		this.circleStroke = Objects.requireNonNull(circleStroke, "circleStroke cannot be null");
		this.connectionPointDistanceFromCenter = connectionPointDistanceFromCenter;
		this.drawCircle = drawCircle;
		this.drawLabel = drawLabel;
		this.labelPaint = Objects.requireNonNull(labelPaint, "labelPaint cannot be null");
		this.labelStroke = Objects.requireNonNull(labelStroke, "labelStroke cannot be null");
		this.circlePaint = Objects.requireNonNull(circlePaint, "circlePaint cannot be null");
		this.labelXOffsetDistance = labelXOffsetDistance;
		this.labelYOffsetDistance = labelYOffsetDistance;
	}

	@Override
	public void draw(final Graphics2D graphics)
	{
		if (drawLabel)
		{
			graphics.setPaint(labelPaint);
			graphics.setStroke(labelStroke);
			graphics.setFont(labelFont);

			final Point2D labelPosition = getLabelPosition();

			graphics.drawString(String.valueOf(label), (float) labelPosition.getX(), (float) labelPosition.getY());
		}

		if (drawCircle)
		{
			graphics.setStroke(circleStroke);
			graphics.setPaint(circlePaint);

			final Rectangle2D circleBox = getBounds();

			graphics.draw(new Ellipse2D.Double(circleBox.getX(), circleBox.getY(), circleBox.getWidth(), circleBox.getHeight()));
		}
	}

	@Override
	public Rectangle2D getBounds()
	{
		final double labelWidth = getLabelWidth();

		final double diameterOfCircleBoundingLabel = 2 * (this.connectionPointDistanceFromCenter);

		return getBoxCenteredOnPoint(xCenter, yCenter, diameterOfCircleBoundingLabel, diameterOfCircleBoundingLabel);
	}

	private int getLabelWidth()
	{
		return labelFont.getSize();
	}

	private Point2D getLabelPosition()
	{
		return new Point2D.Double(this.xCenter + this.labelXOffsetDistance, this.yCenter + this.labelYOffsetDistance);
	}

	private Rectangle2D getBoxCenteredOnPoint(final double xCenter,
	                                          final double yCenter,
	                                          final double width,
	                                          final double height)
	{
		return new Rectangle2D.Double(xCenter - width / 2, yCenter - height / 2, width, height);
	}

	public void translate(final double dx,
	                      final double dy)
	{
		this.xCenter = this.xCenter + dx;
		this.yCenter = this.yCenter + dy;
	}

	public Point2D getCenter()
	{
		return new Point2D.Double(xCenter, yCenter);
	}

	public Point2D getConnectionPointFor(final Point2D point)
	{
		final double adjacent = point.getX() - this.xCenter;
		final double opposite = point.getY() - this.yCenter;

		final double theta = Math.atan(opposite / adjacent);
		final double radius = this.connectionPointDistanceFromCenter;

		final double xdist = Math.cos(theta) * radius * (adjacent < 0 ? -1 : 1);
		final double ydist = Math.sin(theta) * radius * (adjacent < 0 ? -1 : 1);

		return new Point2D.Double(this.xCenter + xdist, this.yCenter + ydist);
	}

	private double getRadiusOfCircleBoundingBox(final double length)
	{
		return Math.ceil(length / 1.41);
	}

	public static class SwapBuilder
	{
		private double xCenter;
		private double yCenter;
		private Character label;
		private Font labelFont = DEFAULT_FONT;
		private Stroke circleStroke = DEFAULT_STROKE;
		private double buffer = DEFUALT_BUFFER_BETWEEN_LABEL_AND_CIRCLE;
		private boolean drawCircle = true;
		private boolean drawLabel = true;
		private Paint labelPaint = DEFAULT_PAINT;
		private Stroke labelStroke = DEFAULT_STROKE;
		private Paint circlePaint = DEFAULT_PAINT;
		private double labelXOffsetDistance;
		private double labelYOffsetDistance;

		public SwapBuilder withxCenter(final double xCenter)
		{
			this.xCenter = xCenter;
			return this;
		}

		public SwapBuilder withyCenter(final double yCenter)
		{
			this.yCenter = yCenter;
			return this;
		}

		public SwapBuilder withLabel(final Character label)
		{
			this.label = label;
			return this;
		}

		public SwapBuilder withLabelFont(final Font labelFont)
		{
			this.labelFont = labelFont;
			return this;
		}

		public SwapBuilder withCircleStroke(final Stroke circleStroke)
		{
			this.circleStroke = circleStroke;
			return this;
		}

		public SwapBuilder withBuffer(final double buffer)
		{
			this.buffer = buffer;
			return this;
		}

		public SwapBuilder withlabelXOffsetDistance(final double labelXOffsetDistance)
		{
			this.labelXOffsetDistance = labelXOffsetDistance;
			return this;
		}

		public SwapBuilder withlabelYOffsetDistance(final double labelYOffsetDistance)
		{
			this.labelYOffsetDistance = labelYOffsetDistance;
			return this;
		}

		public SwapBuilder withDrawCircle(final boolean drawCircle)
		{
			this.drawCircle = drawCircle;
			return this;
		}

		public SwapBuilder withDrawLabel(final boolean drawLabel)
		{
			this.drawLabel = drawLabel;
			return this;
		}

		public SwapBuilder withLabelPaint(final Paint labelPaint)
		{
			this.labelPaint = labelPaint;
			return this;
		}

		public SwapBuilder withLabelStroke(final Stroke labelStroke)
		{
			this.labelStroke = labelStroke;
			return this;
		}

		public SwapBuilder withCirclePaint(final Paint circlePaint)
		{
			this.circlePaint = circlePaint;
			return this;
		}

		public DefaultSwapGraphic createSwap()
		{
			return new DefaultSwapGraphic(xCenter, yCenter, drawLabel, label, labelFont, labelPaint, labelStroke, drawCircle, circleStroke, circlePaint, buffer, labelXOffsetDistance, labelYOffsetDistance);
		}
	}
}
