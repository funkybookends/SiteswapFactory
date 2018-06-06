package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.RotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.DefaultRotationMarkerGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DISTANCE_BETWEEN_BEATS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DISTANCE_BETWEEN_JUGGLERS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DISTANCE_FOR_ARROW_BEND;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DRAW_FULL_ROTATION_MARKER;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DRAW_HALF_ROTATION_MARKER;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.FULL_ROTATION_MARKER_COLOR;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.HALF_ROTATION_MARKER_COLOR;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LEFT_BORDER_DISTANCE;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LINE_WIDTH;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.TOP_BORDER_DISTANCE;

public class DefaultRotationMarkerFactory implements RotationMarkerFactory
{
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

		if (cdp.is(DRAW_FULL_ROTATION_MARKER) && causalDiagram.getFullRotationBeat() <= causalDiagram.getMaxCausalBeat())
		{
			final double xPosition = cdp.getDouble(DISTANCE_BETWEEN_BEATS) * causalDiagram.getFullRotationBeat() + cdp.getDouble(LEFT_BORDER_DISTANCE);

			rotationMarkers.add(new DefaultRotationMarkerGraphic.Builder()
				.withMinY(minY)
				.withMaxY(maxY)
				.withX(xPosition)
				.withPaint(getFullRotationMarkerPaint())
				.withStroke(getFullRotationMarkerStroke())
				.createDefaultRotationMarkerGraphic()
			);
		}

		if (cdp.is(DRAW_HALF_ROTATION_MARKER))
		{
			final double xPosition = cdp.getDouble(DISTANCE_BETWEEN_BEATS) * causalDiagram.getFullRotationBeat() / 2 + cdp.getDouble(LEFT_BORDER_DISTANCE);

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
		return (causalDiagram.getNumJugglers() * cdp.getDouble(DISTANCE_BETWEEN_JUGGLERS) + cdp.getDouble(TOP_BORDER_DISTANCE)) + cdp.getDouble(DISTANCE_FOR_ARROW_BEND) / 2;
	}

	protected double getMinY(final CausalDiagram causalDiagram)
	{
		return cdp.getDouble(TOP_BORDER_DISTANCE) - cdp.getDouble(DISTANCE_FOR_ARROW_BEND) / 2;
	}

	protected Stroke getFullRotationMarkerStroke()
	{
		return new BasicStroke((float) cdp.getDouble(LINE_WIDTH));
	}

	protected Paint getFullRotationMarkerPaint()
	{
		return (Paint) cdp.get(FULL_ROTATION_MARKER_COLOR);
	}

	protected Stroke getHalfRotationMarkerStroke()
	{
		return new BasicStroke((float) cdp.getDouble(LINE_WIDTH), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10f, new float[]{6f, 6f}, 0f);
	}

	protected Paint getHalfRotationMarkerPaint()
	{
		return (Paint) cdp.get(HALF_ROTATION_MARKER_COLOR);
	}
}
