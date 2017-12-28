package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.Graphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

import static org.apache.commons.lang3.math.NumberUtils.max;

public class CausalDiagramToSvg implements Function<CausalDiagram, SVGGraphics2D>
{
	private final CausalDiagramProperties cdp;
	private final ArrowFactory arrowFactory;
	private final SwapFactory swapFactory;

	public CausalDiagramToSvg(final CausalDiagramProperties causalDiagramProperties,
	                          final ArrowFactory arrowFactory,
	                          final SwapFactory swapFactory)
	{
		this.cdp = causalDiagramProperties;
		this.arrowFactory = arrowFactory;
		this.swapFactory = swapFactory;
	}

	public SVGGraphics2D apply(final CausalDiagram causalDiagram)
	{
		final Map<Site, SwapGraphic> swaps = getSiteToSwapMap(causalDiagram);
		final List<ArrowGraphic> arrows = getArrowGraphics(causalDiagram, swaps);
		final Point minDocumentSize = getMinDocumentSize(swaps, arrows, causalDiagram);
		final SVGGraphics2D svgGraphics2D = drawSvgDiagram(swaps, arrows, minDocumentSize);

		drawFullMarker(svgGraphics2D, cdp, causalDiagram);


		return svgGraphics2D;

	}

	private void drawFullMarker(final SVGGraphics2D svgGraphics2D, final CausalDiagramProperties cdp, final CausalDiagram causalDiagram)
	{
		final int maxYPosition = (causalDiagram.getNumJugglers() * cdp.getPixelsPerJuggler() + cdp.getTopBorder()) + 20;

		if (cdp.isDrawFullRotationMarker())
		{
			svgGraphics2D.setStroke(cdp.getFullRotationMarkerStroke());
			svgGraphics2D.setPaint(cdp.getFullRotationMarkerPaint());

			final int xPosition = (int) (cdp.getPixelsPerBeat() * causalDiagram.getFullRotationBeat() + cdp.getLeftBorder());
			svgGraphics2D.drawLine(xPosition, cdp.getTopBorder() - 20, xPosition, maxYPosition);
		}

		if (cdp.isDrawHalfRotationMarker())
		{
			svgGraphics2D.setStroke(cdp.getHalfRotationMarkerStroke());
			svgGraphics2D.setPaint(cdp.getHalfRotationMarkerPaint());

			final int xPosition = (int) (cdp.getPixelsPerBeat() * causalDiagram.getFullRotationBeat() / 2 + cdp.getLeftBorder());
			svgGraphics2D.drawLine(xPosition, cdp.getTopBorder() - 20, xPosition, maxYPosition);
		}
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

				overlapping.translate(0, cdp.getSwapSeperation());
				newSwap.translate(0, -cdp.getSwapSeperation());
			}

			swaps.put(unmappedSite, newSwap);
		}

		return swaps;
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

	private List<ArrowGraphic> getArrowGraphics(final CausalDiagram causalDiagram, final Map<Site, SwapGraphic> swaps)
	{
		final List<ArrowGraphic> arrows = new ArrayList<>();

		for (final Site origin : causalDiagram.getSites())
		{
			for (final Site causes : origin.getCauses())
			{
				final ArrowGraphic arrowGraphic = arrowFactory.getArrow(origin, swaps.get(origin), causes, swaps.get(causes));

				arrows.add(arrowGraphic);
			}
		}
		return arrows;
	}

	private Point getMinDocumentSize(final Map<Site, SwapGraphic> swaps, final List<ArrowGraphic> arrows, final CausalDiagram causalDiagram)
	{
		return Stream.concat(swaps.values().stream(), arrows.stream())
			.map(Graphic::getMinDocumentSize)
			.reduce(new Point(0, causalDiagram.getNumJugglers() * cdp.getPixelsPerJuggler() + cdp.getTopBorder() *2 ), (first, second) -> new Point(max(first.x, second.x), max(first.y, second.y)));
	}

	private SVGGraphics2D drawSvgDiagram(final Map<Site, SwapGraphic> swaps,
	                                     final List<ArrowGraphic> arrows,
	                                     final Point minDocumentSize)
	{
		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(minDocumentSize.x, minDocumentSize.y);
		swaps.values().forEach(graphic -> graphic.draw(svgGraphics2D));
		arrows.forEach(graphic -> graphic.draw(svgGraphics2D));
		return svgGraphics2D;
	}
}
