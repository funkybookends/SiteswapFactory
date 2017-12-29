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
	private static final Color FULL_ROTATION_MARKER_COLOR = new Color(162, 162, 162);
	private static final BasicStroke DASHED_STROKE = new BasicStroke(1.3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10f, new float[]{6f, 6f}, 0f);
	private static final Color HALF_ROTATION_MARKER_COLOR = new Color(190, 190, 190);

	private final CausalDiagramProperties cdp;

	public DefaultRotationMarkerFactory(final CausalDiagramProperties cdp)
	{
		this.cdp = cdp;
	}

	@Override
	public Set<RotationMarkerGraphic> getRotationMarkers(final CausalDiagram causalDiagram)
	{
		final Set<RotationMarkerGraphic> rotationMarkers = new HashSet<>();

		final int maxY = getMaxY(causalDiagram);
		final int minY = getMinY(causalDiagram);

		if (cdp.isDrawFullRotationMarker() && causalDiagram.getFullRotationBeat() <= causalDiagram.getMaxCausalBeat())
		{
			final int xPosition = (int) (cdp.getPixelsPerBeat() * causalDiagram.getFullRotationBeat() + cdp.getLeftBorder());

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
			final int xPosition = (int) (cdp.getPixelsPerBeat() * causalDiagram.getFullRotationBeat() / 2 + cdp.getLeftBorder());

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

	protected int getMaxY(final CausalDiagram causalDiagram)
	{
		return (causalDiagram.getNumJugglers() * cdp.getPixelsPerJuggler() + cdp.getTopBorder()) + 20;
	}

	protected int getMinY(final CausalDiagram causalDiagram)
	{
		return cdp.getTopBorder() - 20;
	}

	protected Stroke getFullRotationMarkerStroke()
	{
		return new BasicStroke(1.3f);
	}

	protected Paint getFullRotationMarkerPaint()
	{
		return FULL_ROTATION_MARKER_COLOR;
	}

	protected Stroke getHalfRotationMarkerStroke()
	{
		return DASHED_STROKE;
	}

	protected Paint getHalfRotationMarkerPaint()
	{
		return HALF_ROTATION_MARKER_COLOR;
	}
}
