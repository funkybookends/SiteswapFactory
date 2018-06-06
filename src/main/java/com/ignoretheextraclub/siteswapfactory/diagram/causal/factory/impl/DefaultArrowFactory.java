package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl;

import java.awt.*;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.SwapToSwapArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.ARROW_HEAD_LENGTH;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.ARROW_HEAD_POINTYNESS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DISTANCE_FOR_ARROW_BEND;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LINE_WIDTH;

public class DefaultArrowFactory implements ArrowFactory
{
	private static final Color CROSSING_PASS_COLOR = new Color(0, 0, 200);
	private static final Color NORMAL_THROW_COLOR = new Color(85, 42, 0);

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
			.withStroke(getArrowStroke(originSite, causesSite))
			.withPaint(getArrowPaint(originSite, originGraphic, causesSite, causesGraphic))
			.withArrowHeadLength(getArrowHeadLength())
			.withArrowHeadPointyness(getArrowHeadPointyness())
			.withDisplayArrowHead(getDisplayArrowHead(originSite, causesSite))
			.createArrowGraphic();

		if (controlShouldBeTranslated(originSite, causesSite))
		{
			translateControl(originSite, causesSite, arrowGraphic);
		}

		return arrowGraphic;
	}

	protected void translateControl(final Site originSite, final Site causesSite, final SwapToSwapArrowGraphic arrowGraphic)
	{
		arrowGraphic.translateControl(0, getArrowBend(originSite, causesSite));
	}

	protected boolean controlShouldBeTranslated(final Site originSite, final Site causesSite)
	{
		return originSite.getJuggler() == causesSite.getJuggler() && (moreThanABeatApart(originSite.getCausalBeat(), causesSite.getCausalBeat()));
	}

	protected Stroke getArrowStroke(final Site originSite, final Site causesSite)
	{
		return new BasicStroke((float) cdp.getDouble(LINE_WIDTH), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
	}

	protected Paint getArrowPaint(final Site origin, final SwapGraphic originGraphic, final Site causes, final SwapGraphic causesGraphic)
	{
		if (!causes.hasAnyCauses())
		{
			return new GradientPaint(originGraphic.getCenter(), NORMAL_THROW_COLOR, causesGraphic.getCenter(), Color.WHITE);
		}
		return NORMAL_THROW_COLOR;
	}

	protected double getArrowHeadLength()
	{
		return cdp.getDouble(ARROW_HEAD_LENGTH);
	}

	protected double getArrowHeadPointyness()
	{
		return cdp.getDouble(ARROW_HEAD_POINTYNESS);
	}

	protected boolean moreThanABeatApart(final double causalBeat, final double causalBeat1)
	{
		return Math.abs(causalBeat - causalBeat1) > 1.1;
	}

	protected double getArrowBend(final Site origin, final Site causes)
	{
		return cdp.getDouble(DISTANCE_FOR_ARROW_BEND) * (origin.getJuggler() % 2 == 1 ? -1 : 1) * (origin.getCausalBeat() < causes.getCausalBeat() ? -1 : 1);
	}

	protected boolean getDisplayArrowHead(final Site origin, final Site causes)
	{
		return true;
	}

}
