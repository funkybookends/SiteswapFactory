package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.Graphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;

import static org.apache.commons.lang3.math.NumberUtils.max;

public class CausalDiagramToSvg
{
	public SVGGraphics2D convert(final CausalDiagram causalDiagram)
	{
		final Map<CausalDiagram.Site, SwapGraphic> swaps = getSiteToSwapMap(causalDiagram);
		final List<ArrowGraphic> arrows = getArrowGraphics(causalDiagram, swaps);
		final Point minDocumentSize = getMinDocumentSize(swaps, arrows);
		return drawSvgDiagram(swaps, arrows, minDocumentSize);
	}

	private SVGGraphics2D drawSvgDiagram(final Map<CausalDiagram.Site, SwapGraphic> swaps, final List<ArrowGraphic> arrows, final Point minDocumentSize)
	{
		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(minDocumentSize.x, minDocumentSize.y);
		swaps.values().forEach(graphic -> graphic.draw(svgGraphics2D));
		arrows.forEach(graphic -> graphic.draw(svgGraphics2D));
		return svgGraphics2D;
	}

	private Point getMinDocumentSize(final Map<CausalDiagram.Site, SwapGraphic> swaps, final List<ArrowGraphic> arrows)
	{
		return Stream.of(swaps.values(), arrows)
			.flatMap(Collection::stream)
			.map(Graphic::getMinDocumentSize)
			.reduce(new Point(), (first, second) -> new Point(max(first.x, second.x), max(first.y, second.y)));
	}

	private List<ArrowGraphic> getArrowGraphics(final CausalDiagram causalDiagram, final Map<CausalDiagram.Site, SwapGraphic> swaps)
	{
		final List<ArrowGraphic> arrows = new ArrayList<>();

		for (final CausalDiagram.Site origin : causalDiagram.getSites())
		{
			for (final CausalDiagram.Site causes : origin.getCauses())
			{
				final ArrowGraphic arrowGraphic = new ArrowGraphic.ArrowGraphicBuilder()
					.withStart(swaps.get(origin))
					.withFinish(swaps.get(causes))
					.withStroke(getArrowStroke())
					.withArrowHeadLength(getArrowHeadLength())
					.withArrowHeadPointyness(getArrowHeadPointyness())
					.withDisplayArrowHead(origin.getCausalBeat() <= causes.getCausalBeat())
					.createArrowGraphic();

				arrows.add(arrowGraphic);

				if (origin.getJuggler() == causes.getJuggler() && (moreThanABeatApart(origin.getCausalBeat(), causes.getCausalBeat())))
				{
					arrowGraphic.translateControl(0, getArrowBend() * (origin.getJuggler() == 1 ? 1 : -1));
				}
			}
		}
		return arrows;
	}

	private boolean moreThanABeatApart(final double causalBeat, final double causalBeat1)
	{
		return Math.abs(causalBeat - causalBeat1) > 1.1;
	}

	private int getArrowBend()
	{
		return 75;
	}

	private BasicStroke getArrowStroke()
	{
		return new BasicStroke(1);
	}

	private Map<CausalDiagram.Site, SwapGraphic> getSiteToSwapMap(final CausalDiagram causalDiagram)
	{
		final Map<CausalDiagram.Site, SwapGraphic> swaps = new HashMap<>();

		for (final CausalDiagram.Site site : causalDiagram.getSites())
		{
			final SwapGraphic newSwap = mapToSwap(site);

			final Optional<SwapGraphic> sameSiteOptional = containsSameSite(swaps, site);

			if (sameSiteOptional.isPresent())
			{
				final SwapGraphic oldSwap = sameSiteOptional.get();
				oldSwap.translate(0, getSwapSeperation());
				oldSwap.translate(0, -getSwapSeperation());
			}

			swaps.put(site, newSwap);
		}
		return swaps;
	}

	private double getArrowHeadPointyness()
	{
		return 9.0;
	}

	private int getArrowHeadLength()
	{
		return 20;
	}

	private SwapGraphic mapToSwap(final CausalDiagram.Site site)
	{
		return new SwapGraphic.SwapBuilder()
			.withxCenter((int) (site.getCausalBeat() * getBeatSeperation() + getLeftBorder()))
			.withyCenter(site.getJuggler() * getJugglerSeperation() + getTopBorder())
			.withLabel(site.getHand() == CausalDiagram.Hand.RIGHT ? 'R' : 'L')
			.withLabelFont(getLabelFont())
			.withCircleStroke(getSwapStroke())
			.withBuffer(getSwapBuffer())
			.withDrawCircle(getSwapDrawCircle())
			.createSwap();
	}

	private Optional<SwapGraphic> containsSameSite(final Map<CausalDiagram.Site, SwapGraphic> swaps, final CausalDiagram.Site newSite)
	{
		for (final CausalDiagram.Site containedSite : swaps.keySet())
		{
			if (containedSite.sameLocation(newSite))
			{
				return Optional.of(swaps.get(containedSite));
			}
		}
		return Optional.empty();
	}

	private boolean getSwapDrawCircle()
	{
		return true;
	}

	private int getSwapSeperation()
	{
		return 10;
	}

	private int getSwapBuffer()
	{
		return 0;
	}

	private BasicStroke getSwapStroke()
	{
		return new BasicStroke(1);
	}

	private Font getLabelFont()
	{
		return new Font("Arial", Font.PLAIN, 12);
	}

	private int getTopBorder()
	{
		return 50;
	}

	private int getJugglerSeperation()
	{
		return 100;
	}

	private int getBeatSeperation()
	{
		return 100;
	}

	private int getLeftBorder()
	{
		return 50;
	}
}
