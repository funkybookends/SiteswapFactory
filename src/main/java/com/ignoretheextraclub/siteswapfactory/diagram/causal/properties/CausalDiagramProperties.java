package com.ignoretheextraclub.siteswapfactory.diagram.causal.properties;

import java.awt.*;

public class CausalDiagramProperties
{
	private static final int FACTOR = 5;

	// Length Properties

	private int maxNumThrowsDisplayed = 15;
	private int minNumHalfRotations = 2;
	private int preferredNumThrows = 10;
	private int getArrowBend = 8 * FACTOR;

	// Seperation Properties
	private int pixelsPerBeat = 15 * FACTOR;
	private int pixelsPerJuggler = 12 * FACTOR;
	private int swapSeparation = FACTOR;

	// Line Style
	private int arrowHeadLength = 2 * FACTOR;
	private double arrowHeadPointyness = 8.0;

	// Site Style
	private boolean swapDrawCircle = false;
	private int swapCircleBuffer = 3;
	private Font labelFont = new Font("Arial", Font.PLAIN, 16);

	// Border Style
	private int leftBorder = 8 * FACTOR;
	private int topBorder = 8 * FACTOR;

	// Markers
	private boolean drawFullRotationMarker = true;
	private boolean drawHalfRotationMarker = true;

	public CausalDiagramProperties(final int maxNumThrowsDisplayed, final int minNumHalfRotations, final int preferredNumThrows, final int getArrowBend, final int pixelsPerBeat, final int pixelsPerJuggler, final int swapSeparation, final int arrowHeadLength, final double arrowHeadPointyness, final boolean swapDrawCircle, final int swapCircleBuffer, final Font labelFont, final int leftBorder, final int topBorder, final boolean drawFullRotationMarker, final boolean drawHalfRotationMarker)
	{
		this.maxNumThrowsDisplayed = maxNumThrowsDisplayed;
		this.minNumHalfRotations = minNumHalfRotations;
		this.preferredNumThrows = preferredNumThrows;
		this.getArrowBend = getArrowBend;
		this.pixelsPerBeat = pixelsPerBeat;
		this.pixelsPerJuggler = pixelsPerJuggler;
		this.swapSeparation = swapSeparation;
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

	public int getSwapSeparation()
	{
		return swapSeparation;
	}

	public void setSwapSeparation(final int swapSeparation)
	{
		this.swapSeparation = swapSeparation;
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

}
