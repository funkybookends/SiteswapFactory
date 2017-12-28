package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.DefaultSwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

public class DefaultSwapFactory implements SwapFactory
{
	private final CausalDiagramProperties cdp;

	public DefaultSwapFactory(final CausalDiagramProperties causalDiagramProperties)
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
			.withCircleStroke(cdp.getCircleStroke().apply(site))
			.withBuffer(cdp.getSwapCircleBuffer())
			.withDrawCircle(cdp.isSwapDrawCircle())
			.withDrawLabel(site.isVisible())
			.createSwap();
	}
}
