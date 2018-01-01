package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl;

import java.awt.*;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.DefaultSwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

public class DefaultSwapFactory implements SwapFactory
{
	private static final Color DEFAULT_FONT_COLOR = new Color(60, 60, 60);
	private static final Color FADING_FONT_COLOR = new Color(200, 200, 200);
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
			.withLabel(getLabel(site))
			.withLabelFont(getCircleFont())
			.withCircleStroke(getCircleStroke(site))
			.withBuffer(getBuffer())
			.withDrawCircle(getDrawCircle())
			.withDrawLabel(getDrawLabel(site))
			.withLabelPaint(getLabelPaint(site))
			.withLabelStroke(getLabelStroke(site))
			.withCirclePaint(getCirclePaint(site))
			.createSwap();
	}

	protected Paint getCirclePaint(final Site site)
	{
		return Color.DARK_GRAY;
	}

	protected Stroke getLabelStroke(final Site site)
	{
		return new BasicStroke(1.f);
	}

	protected Paint getLabelPaint(final Site site)
	{
		return site.hasAnyCauses() ? DEFAULT_FONT_COLOR : FADING_FONT_COLOR;
	}

	protected char getLabel(final Site site)
	{
		return site.getHand() == Hand.RIGHT ? 'R' : 'L';
	}

	protected Font getCircleFont()
	{
		return cdp.getLabelFont();
	}

	protected Stroke getCircleStroke(final Site site)
	{
		return new BasicStroke(0.8f);
	}

	protected double getBuffer()
	{
		return cdp.getSwapCircleBuffer();
	}

	protected boolean getDrawCircle()
	{
		return cdp.isSwapDrawCircle();
	}

	protected boolean getDrawLabel(final Site site)
	{
		return site.getCausalBeat() >= 0;
	}
}
