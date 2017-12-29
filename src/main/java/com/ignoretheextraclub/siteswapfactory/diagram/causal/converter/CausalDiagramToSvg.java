package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.RotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.Graphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

import static org.apache.commons.lang3.math.NumberUtils.max;

public class CausalDiagramToSvg implements Function<CausalDiagram, SVGGraphics2D>
{
	private final CausalDiagramProperties cdp;
	private final ArrowFactory arrowFactory;
	private final SwapFactory swapFactory;
	private RotationMarkerFactory rotationMarkerFactor;

	public CausalDiagramToSvg(final CausalDiagramProperties causalDiagramProperties,
	                          final ArrowFactory arrowFactory,
	                          final SwapFactory swapFactory,
	                          final RotationMarkerFactory rotationMarkerFactor)
	{
		this.cdp = causalDiagramProperties;
		this.arrowFactory = arrowFactory;
		this.swapFactory = swapFactory;
		this.rotationMarkerFactor = rotationMarkerFactor;
	}

	public SVGGraphics2D apply(final CausalDiagram causalDiagram)
	{
		final Map<Site, SwapGraphic> swaps = getSiteToSwapMap(causalDiagram);
		final List<ArrowGraphic> arrows = getArrowGraphics(causalDiagram, swaps);
		final Set<RotationMarkerGraphic> rotationMarkers = getRotationMarkers(causalDiagram);

		final Point minDocumentSize = getMinDocumentSize(swaps.values(), arrows, rotationMarkers, causalDiagram);

		return drawSvgDiagram(minDocumentSize, swaps, arrows, rotationMarkers);
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

				overlapping.translate(0, cdp.getSwapSeparation());
				newSwap.translate(0, -cdp.getSwapSeparation());
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

	private SVGGraphics2D drawSvgDiagram(final Point minDocumentSize,
	                                     final Map<Site, SwapGraphic> swaps,
	                                     final List<ArrowGraphic> arrows,
	                                     final Set<RotationMarkerGraphic> rotationMarkers)
	{
		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(minDocumentSize.x, minDocumentSize.y);

		swaps.values().forEach(graphic -> graphic.draw(svgGraphics2D));
		arrows.forEach(graphic -> graphic.draw(svgGraphics2D));
		rotationMarkers.forEach(marker -> marker.draw(svgGraphics2D));

		return svgGraphics2D;
	}

	private void drawRotationMarkers(final SVGGraphics2D svgGraphics2D, final CausalDiagramProperties cdp, final CausalDiagram causalDiagram)
	{

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

	private Point getMinDocumentSize(final Collection<SwapGraphic> swaps,
	                                 final Collection<ArrowGraphic> arrows,
	                                 final Collection<RotationMarkerGraphic> rotationMarkers,
	                                 final CausalDiagram causalDiagram)
	{
		final Point maxJugglerPlusBorder = new Point(0, causalDiagram.getNumJugglers() * cdp.getPixelsPerJuggler() + cdp.getTopBorder() * 2);

		return Stream.of(swaps, arrows, rotationMarkers)
			.flatMap(Collection::stream)
			.map(Graphic::getMinDocumentSize)
			.reduce(maxJugglerPlusBorder, (first, second) -> new Point(max(first.x, second.x), max(first.y, second.y)));
	}
}
