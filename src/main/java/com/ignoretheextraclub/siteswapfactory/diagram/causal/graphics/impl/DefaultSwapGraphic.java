package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl;

import java.awt.*;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.impl.DefaultCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;

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

	private int xCenter;
	private int yCenter;

	private Character label;
	private Font labelFont;
	private Stroke circleStroke;
	private int buffer;
	private boolean drawCircle;
	private boolean drawLabel;
	private Paint labelPaint;
	private Stroke labelStroke;
	private Paint circlePaint;

	public DefaultSwapGraphic(final int xCenter,
	                          final int yCenter,
	                          final boolean drawLabel,
	                          final Character label,
	                          final Font labelFont,
	                          final Paint labelPaint,
	                          final Stroke labelStroke,
	                          final boolean drawCircle,
	                          final Stroke circleStroke,
	                          final Paint circlePaint,
	                          final int buffer)
	{
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.label = Objects.requireNonNull(label, "label cannot be null");
		this.labelFont = Objects.requireNonNull(labelFont, "labelFont cannot be null");
		this.circleStroke = Objects.requireNonNull(circleStroke, "circleStroke cannot be null");
		this.buffer = buffer;
		this.drawCircle = drawCircle;
		this.drawLabel = drawLabel;
		this.labelPaint = Objects.requireNonNull(labelPaint, "labelPaint cannot be null");
		this.labelStroke = Objects.requireNonNull(labelStroke, "labelStroke cannot be null");
		this.circlePaint = Objects.requireNonNull(circlePaint, "circlePaint cannot be null");
	}

	@Override
	public void draw(final Graphics2D graphics)
	{
		if (drawLabel)
		{
			graphics.setPaint(labelPaint);
			graphics.setStroke(labelStroke);
			graphics.setFont(labelFont);

			final Point labelPosition = getLabelPosition();
			graphics.drawString(String.valueOf(label), labelPosition.x, labelPosition.y);
		}

		if (drawCircle)
		{
			graphics.setStroke(circleStroke);
			graphics.setPaint(circlePaint);

			final Rectangle circleBox = getBounds();
			graphics.drawOval(circleBox.x, circleBox.y, circleBox.width, circleBox.height);
		}
	}

	@Override
	public Rectangle getBounds()
	{
		final int labelWidth = getLabelWidth();

		final int diameterOfCircleBoundingLabel = 2 * (getRadiusOfCircleBoundingBox(labelWidth) + this.buffer);

		return getBoxCenteredOnPoint(xCenter, yCenter,
			diameterOfCircleBoundingLabel, diameterOfCircleBoundingLabel);
	}

	private int getLabelWidth()
	{
		return labelFont.getSize();
	}

	private Point getLabelPosition()
	{
		final int stringWidth = getLabelWidth();

		int labelX = (int) (xCenter - stringWidth * 0.3); // TODO magic numbers, they seem to work though!
		int labelY = (int) (yCenter + stringWidth * 0.4);

		return new Point(labelX, labelY);
	}

	private Rectangle getBoxCenteredOnPoint(final int xCenter,
	                                        final int yCenter,
	                                        final int width,
	                                        final int height)
	{
		return new Rectangle(xCenter - width / 2, yCenter - height / 2, width, height);
	}

	public void translate(final int dx,
	                      final int dy)
	{
		this.xCenter = this.xCenter + dx;
		this.yCenter = this.yCenter + dy;
	}

	public Point getCenter()
	{
		return new Point(xCenter, yCenter);
	}

	public Point getConnectionPointFor(final Point point)
	{
		final double adjacent = point.x - this.xCenter;
		final double opposite = point.y - this.yCenter;

		final double theta = Math.atan(opposite / adjacent);
		final int radius = getBounds().width / 2;

		final int xdist = (int) (Math.cos(theta) * radius * (adjacent < 0 ? -1 : 1));
		final int ydist = (int) (Math.sin(theta) * radius * (adjacent < 0 ? -1 : 1));

		return new Point(this.xCenter + xdist, this.yCenter + ydist);
	}

	private int getRadiusOfCircleBoundingBox(final int length)
	{
		return (int) Math.ceil(length / 1.41);
	}

	public static class SwapBuilder
	{
		private int xCenter;
		private int yCenter;
		private Character label;
		private Font labelFont = DEFAULT_FONT;
		private Stroke circleStroke = DEFAULT_STROKE;
		private int buffer = DEFUALT_BUFFER_BETWEEN_LABEL_AND_CIRCLE;
		private boolean drawCircle = true;
		private boolean drawLabel = true;
		private Paint labelPaint = DEFAULT_PAINT;
		private Stroke labelStroke = DEFAULT_STROKE;
		private Paint circlePaint = DEFAULT_PAINT;

		public SwapBuilder withxCenter(final int xCenter)
		{
			this.xCenter = xCenter;
			return this;
		}

		public SwapBuilder withyCenter(final int yCenter)
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

		public SwapBuilder withBuffer(final int buffer)
		{
			this.buffer = buffer;
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
			return new DefaultSwapGraphic(xCenter, yCenter, drawLabel, label, labelFont, labelPaint, labelStroke, drawCircle, circleStroke, circlePaint, buffer);
		}
	}
}
