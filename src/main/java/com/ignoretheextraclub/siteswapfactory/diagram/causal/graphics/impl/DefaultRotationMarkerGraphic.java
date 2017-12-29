package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl;

import java.awt.*;
import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;

public class DefaultRotationMarkerGraphic implements RotationMarkerGraphic
{
	private int minY;
	private int maxY;
	private int x;

	private Stroke stroke;
	private Paint paint;

	public DefaultRotationMarkerGraphic(final int minY,
	                                    final int maxY,
	                                    final int x,
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
		graphics.drawLine(x, minY, x, maxY);
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x, minY, 0, maxY - minY);
	}

	public static class Builder
	{
		private int minY;
		private int maxY;
		private int x;
		private Stroke stroke;
		private Paint paint;

		public Builder withMinY(final int minY)
		{
			this.minY = minY;
			return this;
		}

		public Builder withMaxY(final int maxY)
		{
			this.maxY = maxY;
			return this;
		}

		public Builder withX(final int x)
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
