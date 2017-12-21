package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;

public class DefaultGraphicFactory implements ArrowFactory, SwapFactory
{
	private final CausalDiagramProperties cdp;

	public DefaultGraphicFactory(final CausalDiagramProperties causalDiagramProperties)
	{
		this.cdp = causalDiagramProperties;
	}

	@Override
	public SwapGraphic getSwap(final Site site)
	{
		return new DefaultSwapGraphic.SwapBuilder()
			.withxCenter((int) (site.getCausalBeat() * cdp.getPixelsPerBeat() + cdp.getLeftBorder()))
			.withyCenter(site.getJuggler() * cdp.getPixelsPerJuggler() + cdp.getTopBorder())
			.withLabel(site.getHand() == Hand.RIGHT ? 'R' : 'L')
			.withLabelFont(cdp.getLabelFont())
			.withCircleStroke(new BasicStroke(cdp.getLineWidth()))
			.withBuffer(cdp.getSwapCircleBuffer())
			.withDrawCircle(cdp.getSwapDrawCircle())
			.withDrawLabel(cdp.isDrawLabel())
			.createSwap();
	}

	@Override
	public ArrowGraphic getArrow(final Site originSite, final SwapGraphic originGraphic, final Site causesSite, final SwapGraphic causesGraphic)
	{
		final PointToPointArrowGraphic arrowGraphic = new PointToPointArrowGraphic.ArrowGraphicBuilder()
			.withStart(originGraphic)
			.withFinish(causesGraphic)
			.withStroke(new BasicStroke(cdp.getLineWidth()))
			.withArrowHeadLength(cdp.getArrowHeadLength())
			.withArrowHeadPointyness(cdp.getArrowHeadPointyness())
			.withDisplayArrowHead(getDisplayArrowHead(originSite, causesSite))
			.createArrowGraphic();

		if (originSite.getJuggler() == causesSite.getJuggler() && (moreThanABeatApart(originSite.getCausalBeat(), causesSite.getCausalBeat())))
		{
			arrowGraphic.translateControl(0, getArrowBend(originSite, causesSite));
		}

		return arrowGraphic;
	}

	private boolean moreThanABeatApart(final double causalBeat, final double causalBeat1)
	{
		return Math.abs(causalBeat - causalBeat1) > 1.1;
	}

	private int getArrowBend(final Site origin, final Site causes)
	{
		return cdp.getGetArrowBend() * (origin.getJuggler() == 1 ? -1 : 1) * (origin.getCausalBeat() < causes.getCausalBeat() ? -1 : 1);
	}

	private boolean getDisplayArrowHead(final Site origin, final Site causes)
	{
		return cdp.getArrowStyle().test(origin, causes);
	}
}
