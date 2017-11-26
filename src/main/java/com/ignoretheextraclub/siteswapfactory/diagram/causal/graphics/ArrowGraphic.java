package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.Objects;

import static org.apache.commons.lang3.math.NumberUtils.max;
import static org.apache.commons.lang3.math.NumberUtils.min;

/**
 * Draws a quadratic line if a control is provided, otherwise a straight line.
 * Draws an arrow if configured to.
 * https://stackoverflow.com/questions/27778951/drawing-an-arrow-on-an-html5-canvas-quadratic-curve
 *
 * @author Caspar Nonclercq
 */
public class ArrowGraphic implements Graphic
{
	private static final double DEFAULT_ARROW_HEAD_POINTYNESS = 9.0;
	private static final int DEFAULT_ARROW_HEAD_LENGTH = 20;
	private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1);

	private SwapGraphic start;
	private SwapGraphic finish;

	private Point control;
	private BasicStroke stroke;
	private boolean displayArrowHead;
	private int arrowHeadLength;

	/**
	 * Draws an arrow head like a section of cake cut into this many pieces.
	 * Higher values mean more pointy
	 */
	private double arrowHeadPointyness;

	public ArrowGraphic(final SwapGraphic start,
	                    final SwapGraphic finish,
	                    final Point control,
	                    final BasicStroke stroke,
	                    final boolean displayArrowHead,
	                    final int arrowHeadLength,
	                    final double arrowHeadPointyness)
	{
		Objects.requireNonNull(start, "start cannot be null");
		Objects.requireNonNull(finish, "finish cannot be null");

		this.start = start;
		this.finish = finish;
		this.control = control;
		this.stroke = stroke;
		this.displayArrowHead = displayArrowHead;
		this.arrowHeadLength = arrowHeadLength;
		this.arrowHeadPointyness = arrowHeadPointyness;
	}

	@Override
	public void draw(final Graphics2D graphics)
	{
		graphics.setStroke(stroke);

		final Point shaftStartingPoint = getShaftStartingPoint();
		final Point shaftEndingPoint = getShaftEndingPoint();

		if (control == null)
		{
			drawLine(graphics, shaftStartingPoint, shaftEndingPoint, stroke);
		}
		else
		{
			final Shape curve = new QuadCurve2D.Double(shaftStartingPoint.x, shaftStartingPoint.y,
				control.x, control.y,
				shaftEndingPoint.x, shaftEndingPoint.y);

			graphics.draw(curve);
		}

		if (displayArrowHead)
		{
			final Point firstArrowHeadPoint = getFirstArrowHeadPoint(shaftEndingPoint);
			final Point secondArrowHeadPoint = getSecondArrowHeadPoint(shaftEndingPoint);

			drawLine(graphics, firstArrowHeadPoint, shaftEndingPoint, stroke);
			drawLine(graphics, secondArrowHeadPoint, shaftEndingPoint, stroke);
		}
	}

	@Override
	public Rectangle getBounds()
	{
		final Point shaftStartingPoint = getShaftStartingPoint();
		final Point shaftEndingPoint = getShaftEndingPoint();
		final Point firstArrowHeadPoint = getFirstArrowHeadPoint(shaftEndingPoint);
		final Point secondArrowHeadPoint = getSecondArrowHeadPoint(shaftEndingPoint);

		final int left = min(shaftStartingPoint.x,
			shaftEndingPoint.x,
			firstArrowHeadPoint.x,
			secondArrowHeadPoint.x,
			((control == null) ? shaftStartingPoint.x : control.x));

		final int top = min(shaftStartingPoint.y,
			shaftEndingPoint.y,
			firstArrowHeadPoint.y,
			secondArrowHeadPoint.y,
			((control == null) ? shaftStartingPoint.y : control.y));

		final int right = max(shaftStartingPoint.x,
			shaftEndingPoint.x,
			firstArrowHeadPoint.x,
			secondArrowHeadPoint.x,
			((control == null) ? shaftStartingPoint.x : control.x));

		final int bottom = max(shaftStartingPoint.y,
			shaftEndingPoint.y,
			firstArrowHeadPoint.y,
			secondArrowHeadPoint.y,
			((control == null) ? shaftStartingPoint.y : control.y));


		return new Rectangle(left, top,right - left, bottom - top);
	}

	private Point getShaftEndingPoint()
	{
		return finish.getConnectionPointFor(control == null ? start.getCenter() : control);
	}

	private Point getShaftStartingPoint()
	{
		return start.getConnectionPointFor(control == null ? finish.getCenter() : control);
	}

	private Point getFirstArrowHeadPoint(final Point connectingPoint)
	{
		final double arrowAngle = getArrowAngle();

		return new Point(
			(int) (connectingPoint.x - (arrowHeadLength * Math.sin(arrowAngle - Math.PI / arrowHeadPointyness))),
			(int) (connectingPoint.y - (arrowHeadLength * Math.cos(arrowAngle - Math.PI / arrowHeadPointyness)))
		);
	}

	private Point getSecondArrowHeadPoint(final Point connectingPoint)
	{
		final double arrowAngle = getArrowAngle();

		return new Point(
			(int) (connectingPoint.x - (arrowHeadLength * Math.sin(arrowAngle + Math.PI / arrowHeadPointyness))),
			(int) (connectingPoint.y - (arrowHeadLength * Math.cos(arrowAngle + Math.PI / arrowHeadPointyness)))
		);
	}

	private double getArrowAngle()
	{
		final Point entry;

		if (control != null)
		{
			entry = control;
		}
		else
		{
			entry = start.getCenter();
		}

		return Math.atan2(entry.x - finish.getCenter().getX(), entry.y - finish.getCenter().getY()) + Math.PI;
	}

	private void drawLine(final Graphics2D graphics2D, final Point start, final Point end, final Stroke stroke)
	{
		graphics2D.setStroke(stroke);
		graphics2D.drawLine(start.x, start.y, end.x, end.y);
	}

	public void translateControl(final int dx, final int dy)
	{
		if (control == null)
		{
			control = getCenterPoint(start.getCenter(), finish.getCenter());
		}
		control.translate(dx, dy);
	}

	private Point getCenterPoint(final Point first, final Point second)
	{
		return new Point((first.x + second.x) / 2, (first.y + second.y) / 2);
	}

	public static class ArrowGraphicBuilder
	{
		private SwapGraphic start;
		private SwapGraphic finish;
		private Point control;
		private BasicStroke stroke = DEFAULT_STROKE;
		private boolean displayArrowHead = true;
		private int arrowHeadLength = DEFAULT_ARROW_HEAD_LENGTH;
		private double arrowHeadPointyness = DEFAULT_ARROW_HEAD_POINTYNESS;

		public ArrowGraphicBuilder withStart(final SwapGraphic start)
		{
			this.start = start;
			return this;
		}

		public ArrowGraphicBuilder withFinish(final SwapGraphic finish)
		{
			this.finish = finish;
			return this;
		}

		public ArrowGraphicBuilder withControl(final Point control)
		{
			this.control = control;
			return this;
		}

		public ArrowGraphicBuilder withStroke(final BasicStroke stroke)
		{
			this.stroke = stroke;
			return this;
		}

		public ArrowGraphicBuilder withDisplayArrowHead(final boolean displayArrowHead)
		{
			this.displayArrowHead = displayArrowHead;
			return this;
		}

		public ArrowGraphicBuilder withArrowHeadLength(final int arrowHeadLength)
		{
			this.arrowHeadLength = arrowHeadLength;
			return this;
		}

		public ArrowGraphicBuilder withArrowHeadPointyness(final double arrowHeadPointyness)
		{
			this.arrowHeadPointyness = arrowHeadPointyness;
			return this;
		}

		public ArrowGraphic createArrowGraphic()
		{
			return new ArrowGraphic(start, finish, control, stroke, displayArrowHead, arrowHeadLength, arrowHeadPointyness);
		}
	}
}
