package com.ignoretheextraclub.siteswapfactory.diagram.causal.properties;

import java.awt.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;

public class CausalDiagramProperties
{
	private static final int FACTOR = 5;

	// Length Properties
	private int minNumThrowsDisplayed = 8;
	private int maxNumThrowsDisplayed = 24;
	private int minNumRepititions = 2;
	private int preferredNumThrows = 8;
	private int getArrowBend = 8 * FACTOR;

	// Seperation Properties
	private int pixelsPerBeat = 15 * FACTOR;
	private int pixelsPerJuggler = 13 * FACTOR;
	private int swapSeperation = FACTOR;

	// Line Style
	private BiPredicate<Site, Site> arrowStyle = ArrowHeadStyle.ALWAYS;
	private BiFunction<Site, Site, Stroke> arrowStroke = ArrowStrokeStyle.FULL_DASHED_IF_ENDPOINT_NOT_VISIBLE;
	private BiFunction<Site, Site, Paint> arrowPaintStyle = ArrowPaintStyle.GRAY_CROSSING_PASS_BLUE;
	private int arrowHeadLength = 2 * FACTOR;
	private double arrowHeadPointyness = 8.0;

	// Site Style
	private boolean swapDrawCircle = false;
	private int swapCircleBuffer = 3;
	private boolean drawLabel = true;
	private Function<Site, Stroke> circleStroke = CircleStrokeStyle.CONSISTENT;
	private Font labelFont = new Font("Arial", Font.PLAIN, 16);

	// Border Style
	private int leftBorder = 8 * FACTOR;
	private int topBorder = 8 * FACTOR;

	// Markers
	private boolean drawFullRotationMarker = true;
	private Stroke fullRotationMarkerStroke = new BasicStroke(1.3f);
	private boolean drawHalfRotationMarker = true;
	private Stroke halfRotationMarkerStroke = new BasicStroke(1.3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, new float[]{6f,6f}, 0.0f);
	private Paint fullRotationMarkerPaint = new Color(160,160,160);
	private Paint halfRotationMarkerPaint = new Color(200,200,200);

	public int getMinNumThrowsDisplayed()
	{
		return minNumThrowsDisplayed;
	}

	public void setMinNumThrowsDisplayed(final int minNumThrowsDisplayed)
	{
		this.minNumThrowsDisplayed = minNumThrowsDisplayed;
	}

	public int getMaxNumThrowsDisplayed()
	{
		return maxNumThrowsDisplayed;
	}

	public void setMaxNumThrowsDisplayed(final int maxNumThrowsDisplayed)
	{
		this.maxNumThrowsDisplayed = maxNumThrowsDisplayed;
	}

	public int getMinNumRepititions()
	{
		return minNumRepititions;
	}

	public void setMinNumRepititions(final int minNumRepititions)
	{
		this.minNumRepititions = minNumRepititions;
	}

	public int getPreferredNumThrows()
	{
		return preferredNumThrows;
	}

	public void setPreferredNumThrows(final int preferredNumThrows)
	{
		this.preferredNumThrows = preferredNumThrows;
	}

	public int getGetArrowBend()
	{
		return getArrowBend;
	}

	public void setGetArrowBend(final int getArrowBend)
	{
		this.getArrowBend = getArrowBend;
	}

	public int getPixelsPerBeat()
	{
		return pixelsPerBeat;
	}

	public void setPixelsPerBeat(final int pixelsPerBeat)
	{
		this.pixelsPerBeat = pixelsPerBeat;
	}

	public int getPixelsPerJuggler()
	{
		return pixelsPerJuggler;
	}

	public void setPixelsPerJuggler(final int pixelsPerJuggler)
	{
		this.pixelsPerJuggler = pixelsPerJuggler;
	}

	public int getSwapSeperation()
	{
		return swapSeperation;
	}

	public void setSwapSeperation(final int swapSeperation)
	{
		this.swapSeperation = swapSeperation;
	}

	public BiPredicate<Site, Site> getArrowStyle()
	{
		return arrowStyle;
	}

	public void setArrowStyle(final BiPredicate<Site, Site> arrowStyle)
	{
		this.arrowStyle = arrowStyle;
	}

	public int getArrowHeadLength()
	{
		return arrowHeadLength;
	}

	public void setArrowHeadLength(final int arrowHeadLength)
	{
		this.arrowHeadLength = arrowHeadLength;
	}

	public double getArrowHeadPointyness()
	{
		return arrowHeadPointyness;
	}

	public void setArrowHeadPointyness(final double arrowHeadPointyness)
	{
		this.arrowHeadPointyness = arrowHeadPointyness;
	}

	public boolean isSwapDrawCircle()
	{
		return swapDrawCircle;
	}

	public void setSwapDrawCircle(final boolean swapDrawCircle)
	{
		this.swapDrawCircle = swapDrawCircle;
	}

	public int getSwapCircleBuffer()
	{
		return swapCircleBuffer;
	}

	public void setSwapCircleBuffer(final int swapCircleBuffer)
	{
		this.swapCircleBuffer = swapCircleBuffer;
	}

	public boolean isDrawLabel()
	{
		return drawLabel;
	}

	public void setDrawLabel(final boolean drawLabel)
	{
		this.drawLabel = drawLabel;
	}

	public Font getLabelFont()
	{
		return labelFont;
	}

	public void setLabelFont(final Font labelFont)
	{
		this.labelFont = labelFont;
	}

	public int getLeftBorder()
	{
		return leftBorder;
	}

	public void setLeftBorder(final int leftBorder)
	{
		this.leftBorder = leftBorder;
	}

	public int getTopBorder()
	{
		return topBorder;
	}

	public void setTopBorder(final int topBorder)
	{
		this.topBorder = topBorder;
	}

	public BiFunction<Site, Site, Stroke> getArrowStroke()
	{
		return arrowStroke;
	}

	public BiFunction<Site, Site, Paint> getArrowPaintStyle()
	{
		return arrowPaintStyle;
	}

	public Function<Site, Stroke> getCircleStroke()
	{
		return circleStroke;
	}

	public boolean isDrawFullRotationMarker()
	{
		return drawFullRotationMarker;
	}

	public Stroke getFullRotationMarkerStroke()
	{
		return fullRotationMarkerStroke;
	}

	public void setArrowStroke(final BiFunction<Site, Site, Stroke> arrowStroke)
	{
		this.arrowStroke = arrowStroke;
	}

	public void setArrowPaintStyle(final BiFunction<Site, Site, Paint> arrowPaintStyle)
	{
		this.arrowPaintStyle = arrowPaintStyle;
	}

	public void setCircleStroke(final Function<Site, Stroke> circleStroke)
	{
		this.circleStroke = circleStroke;
	}

	public void setDrawFullRotationMarker(final boolean drawFullRotationMarker)
	{
		this.drawFullRotationMarker = drawFullRotationMarker;
	}

	public void setFullRotationMarkerStroke(final Stroke fullRotationMarkerStroke)
	{
		this.fullRotationMarkerStroke = fullRotationMarkerStroke;
	}

	public boolean isDrawHalfRotationMarker()
	{
		return drawHalfRotationMarker;
	}

	public void setDrawHalfRotationMarker(final boolean drawHalfRotationMarker)
	{
		this.drawHalfRotationMarker = drawHalfRotationMarker;
	}

	public Stroke getHalfRotationMarkerStroke()
	{
		return halfRotationMarkerStroke;
	}

	public void setHalfRotationMarkerStroke(final Stroke halfRotationMarkerStroke)
	{
		this.halfRotationMarkerStroke = halfRotationMarkerStroke;
	}

	public Paint getFullRotationMarkerPaint()
	{
		return fullRotationMarkerPaint;
	}

	public void setFullRotationMarkerPaint(final Paint fullRotationMarkerPaint)
	{
		this.fullRotationMarkerPaint = fullRotationMarkerPaint;
	}

	public Paint getHalfRotationMarkerPaint()
	{
		return halfRotationMarkerPaint;
	}

	public void setHalfRotationMarkerPaint(final Paint halfRotationMarkerPaint)
	{
		this.halfRotationMarkerPaint = halfRotationMarkerPaint;
	}

	public enum ArrowHeadStyle implements BiPredicate<Site, Site>
	{
		ALWAYS((Site origin, Site causes) -> true),
		BACKWARDS_ONLY((Site origin, Site causes) -> origin.getCausalBeat() >= causes.getCausalBeat());

		private final BiPredicate<Site, Site> drawArrow;


		ArrowHeadStyle(final BiPredicate<Site, Site> drawArrow)
		{
			this.drawArrow = drawArrow;
		}

		public boolean test(final Site origin,
		                    final Site cause)
		{
			return drawArrow.test(origin, cause);
		}
	}

	public enum ArrowStrokeStyle implements BiFunction<Site, Site, Stroke>
	{
		CONSISTENT((Site origin, Site causes) -> new BasicStroke(1.3f)),
		FULL_DASHED_IF_ENDPOINT_NOT_VISIBLE((Site origin, Site cause) ->
		{
			if (!cause.isVisible())
			{
				return new BasicStroke(1.3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, new float[]{6f,6f}, 0.0f);
			}
			return new BasicStroke(1.3f);
		}),
		DASHED((Site origin, Site cause) -> new BasicStroke(1.3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, new float[]{6f,6f}, 0.0f)),
		;

		private final BiFunction<Site, Site, Stroke> strokeBiFunction;

		ArrowStrokeStyle(final BiFunction<Site, Site, Stroke> strokeBiFunction)
		{
			this.strokeBiFunction = strokeBiFunction;
		}

		@Override
		public Stroke apply(final Site origin, final Site cause)
		{
			return this.strokeBiFunction.apply(origin, cause);
		}
	}

	public enum ArrowPaintStyle implements BiFunction<Site, Site, Paint>
	{
		ALWAYS_GRAY((Site origin, Site causes) -> new Color(80, 80, 80)),

		STRAIGHT_GRAY_CROSSING_BLUE((Site origin, Site causes) ->
		{
			if (origin.getHand() == causes.getHand())
			{
				return new Color(80, 80, 80);
			}
			else
			{
				return new Color(0, 0, 200);
			}
		}),

		GRAY_CROSSING_PASS_BLUE((Site origin, Site causes) ->
		{
			if (origin.getHand() == causes.getHand() && origin.getJuggler() != causes.getJuggler())
			{
				return new Color(0, 0, 200);
			}
			return new Color(148, 93, 0);
		}),;

		private final BiFunction<Site, Site, Paint> siteSitePaintBiFunction;

		ArrowPaintStyle(final BiFunction<Site, Site, Paint> strokeBiFunction)
		{
			this.siteSitePaintBiFunction = strokeBiFunction;
		}

		@Override
		public Paint apply(final Site origin, final Site cause)
		{
			return this.siteSitePaintBiFunction.apply(origin, cause);
		}
	}

	public enum CircleStrokeStyle implements Function<Site, Stroke>
	{
		CONSISTENT((Site site) -> new BasicStroke(1f))
		;
		private final Function<Site, Stroke> function;

		CircleStrokeStyle(final Function<Site, Stroke> function)
		{
			this.function = function;
		}


		@Override
		public Stroke apply(final Site site)
		{
			return this.function.apply(site);
		}
	}

}
