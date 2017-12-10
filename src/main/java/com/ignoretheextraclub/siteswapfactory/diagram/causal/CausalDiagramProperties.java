package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.awt.*;
import java.util.function.BiPredicate;

public class CausalDiagramProperties
{
	private static final int FACTOR = 5;
	// Length Properties
	private final int minNumThrowsDisplayed;
	private final int maxMunThrowsDisplayed;
	private final int minNumRepititions;
	private final int preferredNumThrows;
	private final int getArrowBend;

	// Seperation Properties
	private final int pixelsPerBeat;
	private final int pixelsPerJuggler;
	private final int swapSeperation;

	// Line Style
	private final int lineWidth;
	private final ArrowStyle arrowStyle;
	private final int arrowHeadLength;
	private double arrowHeadPointyness;

	// Landing Style
	private boolean swapDrawCircle;
	private final int swapCircleBuffer;
	private final boolean drawLabel;
	private final Font labelFont;

	// Border Style
	private final int leftBorder;
	private final int topBorder;

	public CausalDiagramProperties()
	{
		this(8,
			24,
			2,
			16,
			6 * FACTOR,
			10 * FACTOR,
			8 *FACTOR,
			FACTOR,
			1,
			ArrowStyle.BACKWARDS_ONLY,
			17,
			8.0,
			true,
			0,
			true,
			new Font("Arial", Font.PLAIN, 12),
			5 * FACTOR,
			5 * FACTOR);
	}

	private CausalDiagramProperties(final int minNumThrowsDisplayed,
	                                final int maxMunThrowsDisplayed,
	                                final int minNumRepititions,
	                                final int preferredNumThrows,
	                                final int getArrowBend,
	                                final int pixelsPerBeat,
	                                final int pixelsPerJuggler,
	                                final int swapSeperation,
	                                final int lineWidth,
	                                final ArrowStyle arrowStyle,
	                                final int arrowHeadLength,
	                                final double arrowHeadPointyness,
	                                final boolean swapDrawCircle,
	                                final int swapCircleBuffer,
	                                final boolean drawLabel,
	                                final Font labelFont,
	                                final int leftBorder,
	                                final int topBorder)
	{
		this.minNumThrowsDisplayed = minNumThrowsDisplayed;
		this.maxMunThrowsDisplayed = maxMunThrowsDisplayed;
		this.minNumRepititions = minNumRepititions;
		this.preferredNumThrows = preferredNumThrows;
		this.getArrowBend = getArrowBend;
		this.pixelsPerBeat = pixelsPerBeat;
		this.pixelsPerJuggler = pixelsPerJuggler;
		this.swapSeperation = swapSeperation;
		this.lineWidth = lineWidth;
		this.arrowStyle = arrowStyle;
		this.arrowHeadLength = arrowHeadLength;
		this.arrowHeadPointyness = arrowHeadPointyness;
		this.swapDrawCircle = swapDrawCircle;
		this.swapCircleBuffer = swapCircleBuffer;
		this.drawLabel = drawLabel;
		this.labelFont = labelFont;
		this.leftBorder = leftBorder;
		this.topBorder = topBorder;
	}

	public enum ArrowStyle
	{
		ALWAYS((Site origin, Site causes) -> true),
		BACKWARDS_ONLY((Site origin, Site causes) -> origin.getCausalBeat() >= causes.getCausalBeat());

		private final BiPredicate<Site, Site> drawArrow;


		ArrowStyle(final BiPredicate<Site, Site> drawArrow)
		{
			this.drawArrow = drawArrow;
		}

		public boolean test(final Site origin,
		                    final Site cause)
		{
			return drawArrow.test(origin, cause);
		}
	}

	public boolean getSwapDrawCircle()
	{
		return swapDrawCircle;
	}

	public double getArrowHeadPointyness()
	{
		return arrowHeadPointyness;
	}

	public int getArrowHeadLength()
	{
		return arrowHeadLength;
	}

	public int getMinNumThrowsDisplayed()
	{
		return minNumThrowsDisplayed;
	}

	public int getMaxMunThrowsDisplayed()
	{
		return maxMunThrowsDisplayed;
	}

	public int getMinNumRepititions()
	{
		return minNumRepititions;
	}

	public int getPreferredNumThrows()
	{
		return preferredNumThrows;
	}

	public int getGetArrowBend()
	{
		return getArrowBend;
	}

	public int getPixelsPerBeat()
	{
		return pixelsPerBeat;
	}

	public int getPixelsPerJuggler()
	{
		return pixelsPerJuggler;
	}

	public int getSwapSeperation()
	{
		return swapSeperation;
	}

	public int getLineWidth()
	{
		return lineWidth;
	}

	public ArrowStyle getArrowStyle()
	{
		return arrowStyle;
	}

	public int getSwapCircleBuffer()
	{
		return swapCircleBuffer;
	}

	public boolean isDrawLabel()
	{
		return drawLabel;
	}

	public Font getLabelFont()
	{
		return labelFont;
	}

	public int getLeftBorder()
	{
		return leftBorder;
	}

	public int getTopBorder()
	{
		return topBorder;
	}

	public CausalDiagramProperties withSwapDrawCircle(final boolean swapDrawCircle)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withArrowHeadPointyness(final double arrowHeadPointyness)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withArrowHeadLength(final int arrowHeadLength)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withMinNumThrowsDisplayed(final int minNumThrowsDisplayed)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withMaxMunThrowsDisplayed(final int maxMunThrowsDisplayed)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withMinNumRepititions(final int minNumRepititions)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withPreferredNumThrows(final int preferredNumThrows)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withGetArrowBend(final int withArrowBend)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withPixelsPerBeat(final int pixelsPerBeat)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withPixelsPerJuggler(final int pixelsPerJuggler)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withSwapSeperation(final int swapSeperation)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withLineWidth(final int lineWidth)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withArrowStyle(final ArrowStyle arrowStyle)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withSwapCircleBuffer(final int swapCircleBuffer)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties setIsDrawLabel(final boolean drawLabel)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withLabelFont(final Font labelFont)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withLeftBorder(final int leftBorder)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

	public CausalDiagramProperties withTopBorder(final int topBorder)
	{
		return new CausalDiagramProperties(minNumThrowsDisplayed,
			maxMunThrowsDisplayed,
			minNumRepititions,
			preferredNumThrows,
			getArrowBend,
			pixelsPerBeat,
			pixelsPerJuggler,
			swapSeperation,
			lineWidth,
			arrowStyle,
			arrowHeadLength,
			arrowHeadPointyness,
			swapDrawCircle,
			swapCircleBuffer,
			drawLabel,
			labelFont,
			leftBorder,
			topBorder);
	}

}
