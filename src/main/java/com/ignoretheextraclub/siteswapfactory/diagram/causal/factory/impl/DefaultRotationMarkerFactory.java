package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.RotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.DefaultRotationMarkerGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

public class DefaultRotationMarkerFactory implements RotationMarkerFactory
{
	private static final Paint FULL_ROTATION_MARKER_COLOR = new Color(162, 162, 162);
	private static final Paint HALF_ROTATION_MARKER_COLOR = new Color(190, 190, 190);

	private final CausalDiagramProperties cdp;

	public DefaultRotationMarkerFactory(final CausalDiagramProperties cdp)
	{
		this.cdp = cdp;
	}

	@Override
	public Set<RotationMarkerGraphic> getRotationMarkers(final CausalDiagram causalDiagram)
	{
		final Set<RotationMarkerGraphic> rotationMarkers = new HashSet<>();

		final double maxY = getMaxY(causalDiagram);
		final double minY = getMinY(causalDiagram);

		if (cdp.isDrawFullRotationMarker() && causalDiagram.getFullRotationBeat() <= causalDiagram.getMaxCausalBeat())
		{
			final double xPosition = cdp.getDistanceBetweenBeats() * causalDiagram.getFullRotationBeat() + cdp.getLeftBorderDistance();

			rotationMarkers.add(new DefaultRotationMarkerGraphic.Builder()
				.withMinY(minY)
				.withMaxY(maxY)
				.withX(xPosition)
				.withPaint(getFullRotationMarkerPaint())
				.withStroke(getFullRotationMarkerStroke())
				.createDefaultRotationMarkerGraphic()
			);
		}

		if (cdp.isDrawHalfRotationMarker())
		{
			final double xPosition = cdp.getDistanceBetweenBeats() * causalDiagram.getFullRotationBeat() / 2 + cdp.getLeftBorderDistance();

			rotationMarkers.add(new DefaultRotationMarkerGraphic.Builder()
				.withMinY(minY)
				.withMaxY(maxY)
				.withX(xPosition)
				.withPaint(getHalfRotationMarkerPaint())
				.withStroke(getHalfRotationMarkerStroke())
				.createDefaultRotationMarkerGraphic()
			);
		}

		return rotationMarkers;
	}

	protected double getMaxY(final CausalDiagram causalDiagram)
	{
		return (causalDiagram.getNumJugglers() * cdp.getDistanceBetweenJugglers() + cdp.getTopBorderDistance()) + cdp.getDistanceForArrowBend() / 2;
	}

	protected double getMinY(final CausalDiagram causalDiagram)
	{
		return cdp.getTopBorderDistance() - cdp.getDistanceForArrowBend() / 2;
	}

	protected Stroke getFullRotationMarkerStroke()
	{
		return new BasicStroke((float) cdp.getLineWidth());
	}

	protected Paint getFullRotationMarkerPaint()
	{
		return FULL_ROTATION_MARKER_COLOR;
	}

	protected Stroke getHalfRotationMarkerStroke()
	{
		return new BasicStroke((float) cdp.getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10f, new float[]{6f, 6f}, 0f);
	}

	protected Paint getHalfRotationMarkerPaint()
	{
		return HALF_ROTATION_MARKER_COLOR;
	}
}
