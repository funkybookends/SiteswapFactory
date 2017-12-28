package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl;

import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;

/**
 * Draws a line (with an optional arrow head) between two {@link SwapGraphic}s. An optional {@link #control} allows
 * for a curve to be used instead of a line.
 * https://stackoverflow.com/questions/27778951/drawing-an-arrow-on-an-html5-canvas-quadratic-curve
 *
 * @author Caspar Nonclercq
 */
public class SwapToSwapArrowGraphic implements ArrowGraphic
{
	private static final double DEFAULT_ARROW_HEAD_POINTYNESS = 9.0;
	private static final int DEFAULT_ARROW_HEAD_LENGTH = 20;
	private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1);
	private static final Color DEFAULT_PAINT = new Color(80, 80, 80);

	private SwapGraphic start;
	private SwapGraphic finish;

	private Point control;
	private Stroke stroke;
	private Paint paint;
	private boolean displayArrowHead;
	private int arrowHeadLength;

	/**
	 * Draws an arrow head like a section of cake cut into this many pieces.
	 * Higher values mean more pointy
	 */
	private double arrowHeadPointyness;

	/**
	 * Creates an arrow.
	 * @param start               The start point of the arrow, found using {@link SwapGraphic#getConnectionPointFor(Point)}
	 * @param finish              The end point of the arrow, found using {@link SwapGraphic#getConnectionPointFor(Point)}
	 * @param control             An optional control, will cause a {@link QuadCurve2D} to be drawn instead of a line.
	 * @param stroke              The stroke to use.
	 * @param paint
	 * @param displayArrowHead    If the arrow head should be drawn or not.
	 * @param arrowHeadLength     The length of the arrow head.
	 * @param arrowHeadPointyness The pointyness of the arrowhead, greater than 0.
	 */
	public SwapToSwapArrowGraphic(final SwapGraphic start,
	                              final SwapGraphic finish,
	                              final Point control,
	                              final Stroke stroke,
	                              final Paint paint, final boolean displayArrowHead,
	                              final int arrowHeadLength,
	                              final double arrowHeadPointyness)
	{
		Objects.requireNonNull(start, "start cannot be null");
		Objects.requireNonNull(finish, "finish cannot be null");

		this.start = start;
		this.paint = paint;
		this.finish = finish;
		this.control = control;
		this.stroke = stroke;
		this.displayArrowHead = displayArrowHead;
		this.arrowHeadLength = arrowHeadLength;
		this.arrowHeadPointyness = arrowHeadPointyness;

		if (arrowHeadPointyness <= 0)
		{
			throw new IllegalArgumentException("arrowHeadPointyness must be > than 0");
		}
	}

	@Override
	public void draw(final Graphics2D graphics)
	{
		if (start != finish)
		{
			graphics.setStroke(stroke);
			graphics.setPaint(paint);

			final Point shaftStartingPoint = getShaftStartingPoint();
			final Point shaftEndingPoint = getShaftEndingPoint();

			if (control == null)
			{
				drawLine(graphics, shaftStartingPoint, shaftEndingPoint, stroke);
			}
			else
			{
				final Shape curve = new QuadCurve2D.Double(shaftStartingPoint.x, shaftStartingPoint.y, control.x, control.y, shaftEndingPoint.x, shaftEndingPoint.y);
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
	}

	@Override
	public Rectangle getBounds()
	{
		if (start != finish)
		{
			final List<Point> extremePoints = getAllPoints();

			final int left = min((point) -> point.x, extremePoints);
			final int top = min((point) -> point.y, extremePoints);
			final int right = max((point) -> point.x, extremePoints);
			final int bottom = max((point) -> point.y, extremePoints);

			return new Rectangle(left, top, right - left, bottom - top);
		}
		else
		{
			return start.getBounds();
		}
	}

	private int min(Function<Point, Integer> extractor, final List<Point> points)
	{
		return points.stream()
			.filter(Objects::nonNull)
			.map(extractor)
			.reduce((first, second) -> first < second ? first : second)
			.orElseThrow(IllegalArgumentException::new);
	}

	private int max(Function<Point, Integer> extractor, final List<Point> points)
	{
		return points.stream()
			.filter(Objects::nonNull)
			.map(extractor)
			.reduce((first, second) -> first > second ? first : second)
			.orElseThrow(IllegalArgumentException::new);
	}

	private List<Point> getAllPoints()
	{
		final Point shaftEndingPoint = getShaftEndingPoint();

		return Arrays.asList(getShaftStartingPoint(),
			shaftEndingPoint,
			getFirstArrowHeadPoint(shaftEndingPoint),
			getSecondArrowHeadPoint(shaftEndingPoint),
			control);
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
		final Point entry = control == null ? start.getCenter() : control;
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
		private Stroke stroke = DEFAULT_STROKE;
		private Paint paint = DEFAULT_PAINT;
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

		public ArrowGraphicBuilder withStroke(final Stroke stroke)
		{
			this.stroke = stroke;
			return this;
		}

		public ArrowGraphicBuilder withPaint(final Paint paint)
		{
			this.paint = paint;
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

		public SwapToSwapArrowGraphic createArrowGraphic()
		{
			return new SwapToSwapArrowGraphic(start, finish, control, stroke, paint, displayArrowHead, arrowHeadLength, arrowHeadPointyness);
		}
	}
}
