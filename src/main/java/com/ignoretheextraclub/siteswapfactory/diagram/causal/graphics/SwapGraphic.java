package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;

/**
 * A position on a {@link com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram}.
 *
 * @author Caspar Nonclercq
 */
public class SwapGraphic implements Graphic
{
	private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
	private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1);
	private static final int DEFUALT_BUFFER_BETWEEN_LABEL_AND_CIRCLE = 0;

	private int xCenter;
	private int yCenter;

	private Character label;
	private Font labelFont;
	private BasicStroke circleStroke;
	private int buffer;
	private boolean drawCircle;

	public SwapGraphic(final int xCenter,
	                   final int yCenter,
	                   final Character label,
	                   final Font labelFont,
	                   final BasicStroke circleStroke,
	                   final int buffer,
	                   final boolean drawCircle)
	{
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.label = label;
		this.labelFont = labelFont;
		this.circleStroke = circleStroke;
		this.buffer = buffer;
		this.drawCircle = drawCircle;
	}

	@Override
	public void draw(final Graphics2D graphics)
	{
		// Set styles
		graphics.setFont(labelFont);

		// Draw label
		final Point labelPosition = getLabelPosition();
		graphics.drawString(String.valueOf(label), labelPosition.x, labelPosition.y);

		if (drawCircle)
		{
			graphics.setStroke(circleStroke);
			final Rectangle circleBox = getBounds();
			graphics.drawOval(circleBox.x, circleBox.y, circleBox.width, circleBox.height);
		}
	}

	@Override
	public Rectangle getBounds()
	{
		final int labelWidth = getLabelWidth();
		final int diameterOfCircleBoundingLabel = 2 * (getRadiusOfCircleBoundingBox(labelWidth) +
			(int) circleStroke.getLineWidth() +
			this.buffer);

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
		private BasicStroke circleStroke = DEFAULT_STROKE;
		private int buffer = DEFUALT_BUFFER_BETWEEN_LABEL_AND_CIRCLE;
		private boolean drawCircle = true;

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

		public SwapBuilder withCircleStroke(final BasicStroke circleStroke)
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

		public SwapGraphic createSwap()
		{
			return new SwapGraphic(xCenter, yCenter, label, labelFont, circleStroke, buffer, drawCircle);
		}
	}
}
