package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.SwapToSwapArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

public class DefaultArrowFactory implements ArrowFactory
{
	private final CausalDiagramProperties cdp;

	public DefaultArrowFactory(final CausalDiagramProperties cdp)
	{
		this.cdp = cdp;
	}

	@Override
	public ArrowGraphic getArrow(final Site originSite, final SwapGraphic originGraphic, final Site causesSite, final SwapGraphic causesGraphic)
	{
		final SwapToSwapArrowGraphic arrowGraphic = new SwapToSwapArrowGraphic.ArrowGraphicBuilder()
			.withStart(originGraphic)
			.withFinish(causesGraphic)
			.withStroke(cdp.getArrowStroke().apply(originSite, causesSite))
			.withPaint(cdp.getArrowPaintStyle().apply(originSite, causesSite))
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
