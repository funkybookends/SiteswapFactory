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
	private double distanceBetweenBeats = 15 * FACTOR;
	private double distanceBetweenJugglers = 12 * FACTOR;
	private double distanceForArrowBend = 8 * FACTOR;
	private double distanceBetweenOverlappingSwaps = FACTOR;

	// Line Style
	private double lineWidth = 0.25 * FACTOR;
	private double arrowHeadLength = 2 * FACTOR;
	private double arrowHeadPointyness = 8.0;

	// Site Style
	private boolean swapDrawCircle = true;
	private double distanceBetweenArrowAndSwapCenter = 2 * FACTOR;
	private Font labelFont = new Font("Arial", Font.PLAIN, (int) (3 * FACTOR));
	private double labelYOffsetDistance = 1 * FACTOR;
	private double labelXOffsetDistance = -1.1 * FACTOR;

	// Border Style
	private double leftBorderDistance = 8 * FACTOR;
	private double topBorderDistance = 8 * FACTOR;

	// Markers
	private boolean drawFullRotationMarker = true;
	private boolean drawHalfRotationMarker = true;

	public CausalDiagramProperties(final double distanceBetweenBeatsAndScaled)
	{
		this
			(
				15,
				2,
				10,
				15 * distanceBetweenBeatsAndScaled / 12,
				distanceBetweenBeatsAndScaled,
				8 * distanceBetweenBeatsAndScaled / 12,
				1 * distanceBetweenBeatsAndScaled / 12,
				0.25 * distanceBetweenBeatsAndScaled / 12,
				2 * distanceBetweenBeatsAndScaled / 12,
				8.0,
				false,
				2 * distanceBetweenBeatsAndScaled / 12,
				new Font("Arial", Font.PLAIN, (int) (3 * distanceBetweenBeatsAndScaled / 12)),
				-1 * distanceBetweenBeatsAndScaled / 12,
				-1.1 * distanceBetweenBeatsAndScaled / 12,
				8 * distanceBetweenBeatsAndScaled / 12,
				8 * distanceBetweenBeatsAndScaled / 13,
				true,
				true
			);
	}

	public CausalDiagramProperties(final int maxNumThrowsDisplayed,
	                               final int minNumHalfRotations,
	                               final int preferredNumThrows,
	                               final double distanceForArrowBend,
	                               final double distanceBetweenBeats,
	                               final double distanceBetweenJugglers,
	                               final double distanceBetweenOverlappingSwaps,
	                               final double lineWidth,
	                               final double arrowHeadLength,
	                               final double arrowHeadPointyness,
	                               final boolean swapDrawCircle,
	                               final double swapCircleBuffer,
	                               final Font labelFont,
	                               final double labelYOffsetDistance,
	                               final double labelXOffsetDistance,
	                               final double leftBorder,
	                               final double topBorderDistance,
	                               final boolean drawFullRotationMarker,
	                               final boolean drawHalfRotationMarker)
	{
		this.maxNumThrowsDisplayed = maxNumThrowsDisplayed;
		this.minNumHalfRotations = minNumHalfRotations;
		this.preferredNumThrows = preferredNumThrows;
		this.distanceForArrowBend = distanceForArrowBend;
		this.distanceBetweenBeats = distanceBetweenBeats;
		this.distanceBetweenJugglers = distanceBetweenJugglers;
		this.distanceBetweenOverlappingSwaps = distanceBetweenOverlappingSwaps;
		this.lineWidth = lineWidth;
		this.arrowHeadLength = arrowHeadLength;
		this.arrowHeadPointyness = arrowHeadPointyness;
		this.swapDrawCircle = swapDrawCircle;
		this.distanceBetweenArrowAndSwapCenter = swapCircleBuffer;
		this.labelFont = labelFont;
		this.labelYOffsetDistance = labelYOffsetDistance;
		this.labelXOffsetDistance = labelXOffsetDistance;
		this.leftBorderDistance = leftBorder;
		this.topBorderDistance = topBorderDistance;
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

	public double getDistanceForArrowBend()
	{
		return distanceForArrowBend;
	}

	public void setDistanceForArrowBend(final double distanceForArrowBend)
	{
		this.distanceForArrowBend = distanceForArrowBend;
	}

	public double getDistanceBetweenBeats()
	{
		return distanceBetweenBeats;
	}

	public void setDistanceBetweenBeats(final double distanceBetweenBeats)
	{
		this.distanceBetweenBeats = distanceBetweenBeats;
	}

	public double getDistanceBetweenJugglers()
	{
		return distanceBetweenJugglers;
	}

	public void setDistanceBetweenJugglers(final double distanceBetweenJugglers)
	{
		this.distanceBetweenJugglers = distanceBetweenJugglers;
	}

	public double getDistanceBetweenOverlappingSwaps()
	{
		return distanceBetweenOverlappingSwaps;
	}

	public void setDistanceBetweenOverlappingSwaps(final double distanceBetweenOverlappingSwaps)
	{
		this.distanceBetweenOverlappingSwaps = distanceBetweenOverlappingSwaps;
	}

	public double getArrowHeadLength()
	{
		return arrowHeadLength;
	}

	public void setArrowHeadLength(final double arrowHeadLength)
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

	public double getDistanceBetweenArrowAndSwapCenter()
	{
		return distanceBetweenArrowAndSwapCenter;
	}

	public void setDistanceBetweenArrowAndSwapCenter(final double distanceBetweenArrowAndSwapCenter)
	{
		this.distanceBetweenArrowAndSwapCenter = distanceBetweenArrowAndSwapCenter;
	}

	public Font getLabelFont()
	{
		return labelFont;
	}

	public void setLabelFont(final Font labelFont)
	{
		this.labelFont = labelFont;
	}

	public double getLeftBorderDistance()
	{
		return leftBorderDistance;
	}

	public void setLeftBorderDistance(final double leftBorderDistance)
	{
		this.leftBorderDistance = leftBorderDistance;
	}

	public double getTopBorderDistance()
	{
		return topBorderDistance;
	}

	public void setTopBorderDistance(final double topBorderDistance)
	{
		this.topBorderDistance = topBorderDistance;
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

	public double getLabelYOffsetDistance()
	{
		return labelYOffsetDistance;
	}

	public double getLabelXOffsetDistance()
	{
		return labelXOffsetDistance;
	}
}
