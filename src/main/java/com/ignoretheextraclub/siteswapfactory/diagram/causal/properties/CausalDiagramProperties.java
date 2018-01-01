package com.ignoretheextraclub.siteswapfactory.diagram.causal.properties;

import java.awt.*;

public class CausalDiagramProperties
{
	private static final double FACTOR = 5;

	// Length Properties
	private int maxNumThrowsDisplayed = 15;
	private int minNumHalfRotations = 2;
	private int preferredNumThrows = 10;

	// Seperation Properties
	private double pixelsPerBeat = 15 * FACTOR;
	private double pixelsPerJuggler = 12 * FACTOR;
	private double getArrowBend = 8 * FACTOR;
	private double swapSeparation = FACTOR;

	// Line Style
	private double lineWidth = 1.3;
	private double arrowHeadLength = 2 * FACTOR;
	private double arrowHeadPointyness = 8.0;

	// Site Style
	private boolean swapDrawCircle = false;
	private double swapCircleBuffer = 0.5 * FACTOR;
	private Font labelFont = new Font("Arial", Font.PLAIN, (int) (3 * FACTOR));

	// Border Style
	private double leftBorder = 8 * FACTOR;
	private double topBorder = 8 * FACTOR;

	// Markers
	private boolean drawFullRotationMarker = true;
	private boolean drawHalfRotationMarker = true;

	public CausalDiagramProperties(final int maxNumThrowsDisplayed,
	                               final int minNumHalfRotations,
	                               final int preferredNumThrows,
	                               final double getArrowBend,
	                               final double pixelsPerBeat,
	                               final double pixelsPerJuggler,
	                               final double swapSeparation,
	                               final double lineWidth,
	                               final double arrowHeadLength,
	                               final double arrowHeadPointyness,
	                               final boolean swapDrawCircle,
	                               final double swapCircleBuffer,
	                               final Font labelFont,
	                               final double leftBorder,
	                               final double topBorder,
	                               final boolean drawFullRotationMarker,
	                               final boolean drawHalfRotationMarker)
	{
		this.maxNumThrowsDisplayed = maxNumThrowsDisplayed;
		this.minNumHalfRotations = minNumHalfRotations;
		this.preferredNumThrows = preferredNumThrows;
		this.getArrowBend = getArrowBend;
		this.pixelsPerBeat = pixelsPerBeat;
		this.pixelsPerJuggler = pixelsPerJuggler;
		this.swapSeparation = swapSeparation;
		this.lineWidth = lineWidth;
		this.arrowHeadLength = arrowHeadLength;
		this.arrowHeadPointyness = arrowHeadPointyness;
		this.swapDrawCircle = swapDrawCircle;
		this.swapCircleBuffer = swapCircleBuffer;
		this.labelFont = labelFont;
		this.leftBorder = leftBorder;
		this.topBorder = topBorder;
		this.drawFullRotationMarker = drawFullRotationMarker;
		this.drawHalfRotationMarker = drawHalfRotationMarker;
	}

	public CausalDiagramProperties()
	{
	}

	public int getMaxNumThrowsDisplayed()
	{
		return maxNumThrowsDisplayed;
	}

	public void setMaxNumThrowsDisplayed(final int maxNumThrowsDisplayed)
	{
		this.maxNumThrowsDisplayed = maxNumThrowsDisplayed;
	}

	public int getMinNumHalfRotations()
	{
		return minNumHalfRotations;
	}

	public void setMinNumHalfRotations(final int minNumHalfRotations)
	{
		this.minNumHalfRotations = minNumHalfRotations;
	}

	public int getPreferredNumThrows()
	{
		return preferredNumThrows;
	}

	public void setPreferredNumThrows(final int preferredNumThrows)
	{
		this.preferredNumThrows = preferredNumThrows;
	}

	public double getGetArrowBend()
	{
		return getArrowBend;
	}

	public void setGetArrowBend(final int getArrowBend)
	{
		this.getArrowBend = getArrowBend;
	}

	public double getPixelsPerBeat()
	{
		return pixelsPerBeat;
	}

	public void setPixelsPerBeat(final int pixelsPerBeat)
	{
		this.pixelsPerBeat = pixelsPerBeat;
	}

	public double getPixelsPerJuggler()
	{
		return pixelsPerJuggler;
	}

	public void setPixelsPerJuggler(final int pixelsPerJuggler)
	{
		this.pixelsPerJuggler = pixelsPerJuggler;
	}

	public double getSwapSeparation()
	{
		return swapSeparation;
	}

	public void setSwapSeparation(final int swapSeparation)
	{
		this.swapSeparation = swapSeparation;
	}

	public double getArrowHeadLength()
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

	public double getSwapCircleBuffer()
	{
		return swapCircleBuffer;
	}

	public void setSwapCircleBuffer(final int swapCircleBuffer)
	{
		this.swapCircleBuffer = swapCircleBuffer;
	}

	public Font getLabelFont()
	{
		return labelFont;
	}

	public void setLabelFont(final Font labelFont)
	{
		this.labelFont = labelFont;
	}

	public double getLeftBorder()
	{
		return leftBorder;
	}

	public void setLeftBorder(final int leftBorder)
	{
		this.leftBorder = leftBorder;
	}

	public double getTopBorder()
	{
		return topBorder;
	}

	public void setTopBorder(final int topBorder)
	{
		this.topBorder = topBorder;
	}

	public boolean isDrawFullRotationMarker()
	{
		return drawFullRotationMarker;
	}

	public void setDrawFullRotationMarker(final boolean drawFullRotationMarker)
	{
		this.drawFullRotationMarker = drawFullRotationMarker;
	}

	public boolean isDrawHalfRotationMarker()
	{
		return drawHalfRotationMarker;
	}

	public void setDrawHalfRotationMarker(final boolean drawHalfRotationMarker)
	{
		this.drawHalfRotationMarker = drawHalfRotationMarker;
	}

	public double getLineWidth()
	{
		return lineWidth;
	}

	public void setLineWidth(final double lineWidth)
	{
		this.lineWidth = lineWidth;
	}
}
