package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.RotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.Graphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

import static org.apache.commons.lang3.math.NumberUtils.max;

/**
 * Converts a causal diagram to an {@link SVGGraphics2D}
 */
public class CausalDiagramToSvg implements CausalDiagramDrawer
{
	private final CausalDiagramProperties cdp;
	private final ArrowFactory arrowFactory;
	private final SwapFactory swapFactory;
	private final RotationMarkerFactory rotationMarkerFactor;

	/**
	 * Creates a new CasualDiagramToSvg Converter
	 *
	 * @param causalDiagramProperties The properties for the causal diagram
	 * @param swapFactory             A factory for creating the swaps
	 * @param arrowFactory            A factory for creating the arrows
	 * @param rotationMarkerFactor    A factory for creating the rotation markers
	 */
	public CausalDiagramToSvg(final CausalDiagramProperties causalDiagramProperties,
	                          final SwapFactory swapFactory,
	                          final ArrowFactory arrowFactory,
	                          final RotationMarkerFactory rotationMarkerFactor)
	{
		this.cdp = causalDiagramProperties;
		this.arrowFactory = arrowFactory;
		this.swapFactory = swapFactory;
		this.rotationMarkerFactor = rotationMarkerFactor;
	}

	@Override
	public <T extends Graphics2D> T apply(final CausalDiagram causalDiagram, final GraphicsSupplier<T> graphicsSupplier)
	{
		final Map<Site, SwapGraphic> swaps = getSiteToSwapMap(causalDiagram);
		final List<ArrowGraphic> arrows = getArrowGraphics(causalDiagram, swaps);
		final Set<RotationMarkerGraphic> rotationMarkers = getRotationMarkers(causalDiagram);

		final Point2D minDocumentSize = getMinDocumentSize(swaps.values(), arrows, rotationMarkers, causalDiagram);
		final T graphics2d = graphicsSupplier.createGraphics(minDocumentSize);

		return drawSvgDiagram(graphics2d, swaps, arrows, rotationMarkers);
	}

	private Set<RotationMarkerGraphic> getRotationMarkers(final CausalDiagram causalDiagram)
	{
		return rotationMarkerFactor.getRotationMarkers(causalDiagram);
	}

	private Map<Site, SwapGraphic> getSiteToSwapMap(final CausalDiagram causalDiagram)
	{
		final Map<Site, SwapGraphic> swaps = new HashMap<>();

		for (final Site unmappedSite : causalDiagram.getSites())
		{
			final SwapGraphic newSwap = swapFactory.getSwap(unmappedSite);

			final Optional<Site> overlappingSite = containsSameSite(swaps, unmappedSite);

			if (overlappingSite.isPresent())
			{
				final SwapGraphic overlapping = swaps.get(overlappingSite.get());

				overlapping.translate(0, cdp.getDistanceBetweenOverlappingSwaps());
				newSwap.translate(0, -cdp.getDistanceBetweenOverlappingSwaps());
			}

			swaps.put(unmappedSite, newSwap);
		}

		return swaps;
	}

	private List<ArrowGraphic> getArrowGraphics(final CausalDiagram causalDiagram, final Map<Site, SwapGraphic> swaps)
	{
		final List<ArrowGraphic> arrows = new ArrayList<>();

		for (final Site origin : causalDiagram.getSites())
		{
			for (final Site causes : origin.getCauses())
			{
				arrows.add(arrowFactory.getArrow(origin, swaps.get(origin), causes, swaps.get(causes)));
			}
		}

		return arrows;
	}

	private <T extends Graphics2D> T drawSvgDiagram(final T graphics2D,
	                                                final Map<Site, SwapGraphic> swaps,
	                                                final List<ArrowGraphic> arrows,
	                                                final Set<RotationMarkerGraphic> rotationMarkers)
	{
		swaps.values().forEach(graphic -> graphic.draw(graphics2D));
		arrows.forEach(graphic -> graphic.draw(graphics2D));
		rotationMarkers.forEach(marker -> marker.draw(graphics2D));

		return graphics2D;
	}

	private Optional<Site> containsSameSite(final Map<Site, SwapGraphic> swaps, final Site newSite)
	{
		for (final Site mappedSite : swaps.keySet())
		{
			if (mappedSite.overlaps(newSite))
			{
				return Optional.of(mappedSite);
			}
		}
		return Optional.empty();
	}

	private Point2D getMinDocumentSize(final Collection<SwapGraphic> swaps,
	                                   final Collection<ArrowGraphic> arrows,
	                                   final Collection<RotationMarkerGraphic> rotationMarkers,
	                                   final CausalDiagram causalDiagram)
	{

		final double maxJugglerPlusBorder = causalDiagram.getNumJugglers() * cdp.getDistanceBetweenJugglers() + cdp.getTopBorderDistance() * 2;
		final double maxBeatPlusBorder = causalDiagram.getMaxCausalBeat() * cdp.getDistanceBetweenBeats() + cdp.getLeftBorderDistance() * 2;

		final Point2D causalMax = new Point2D.Double(maxBeatPlusBorder, maxJugglerPlusBorder);

		return Stream.of(swaps, arrows, rotationMarkers)
			.flatMap(Collection::stream)
			.map(Graphic::getMinDocumentSize)
			.reduce(causalMax, (first, second) -> new Point2D.Double(max(first.getX(), second.getX()), max(first.getY(), second.getY())));
	}
}
