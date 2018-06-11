/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl;

import java.awt.*;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.DefaultSwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DISTANCE_BETWEEN_ARROW_AND_SWAP_CENTER;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DISTANCE_BETWEEN_BEATS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.DISTANCE_BETWEEN_JUGGLERS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LABEL_FONT;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LABEL_XOFFSET_DISTANCE;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LABEL_YOFFSET_DISTANCE;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LEFT_BORDER_DISTANCE;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.LINE_WIDTH;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.SWAP_DRAW_CIRCLE;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.TOP_BORDER_DISTANCE;

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
			.withxCenter(site.getCausalBeat() * cdp.getDouble(DISTANCE_BETWEEN_BEATS) + cdp.getDouble(LEFT_BORDER_DISTANCE))
			.withyCenter(site.getJuggler() * cdp.getDouble(DISTANCE_BETWEEN_JUGGLERS) + cdp.getDouble(TOP_BORDER_DISTANCE))
			.withLabel(getLabel(site))
			.withLabelFont(getCircleFont())
			.withCircleStroke(getCircleStroke(site))
			.withBuffer(getBuffer())
			.withDrawCircle(getDrawCircle())
			.withDrawLabel(getDrawLabel(site))
			.withLabelPaint(getLabelPaint(site))
			.withLabelStroke(getLabelStroke(site))
			.withCirclePaint(getCirclePaint(site))
			.withlabelXOffsetDistance(getLabelXOffsetDistance())
			.withlabelYOffsetDistance(getLabelYOffsetDistance())
			.createSwap();
	}

	protected double getLabelYOffsetDistance()
	{
		return cdp.getDouble(LABEL_YOFFSET_DISTANCE);
	}

	protected double getLabelXOffsetDistance()
	{
		return cdp.getDouble(LABEL_XOFFSET_DISTANCE);
	}

	protected Paint getCirclePaint(final Site site)
	{
		return getLabelPaint(site);
	}

	protected Stroke getLabelStroke(final Site site)
	{
		return new BasicStroke((float) cdp.getDouble(LINE_WIDTH));
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
		return cdp.getFont(LABEL_FONT);
	}

	protected Stroke getCircleStroke(final Site site)
	{
		return new BasicStroke((float) cdp.getDouble(LINE_WIDTH));
	}

	protected double getBuffer()
	{
		return cdp.getDouble(DISTANCE_BETWEEN_ARROW_AND_SWAP_CENTER);
	}

	protected boolean getDrawCircle()
	{
		return cdp.is(SWAP_DRAW_CIRCLE);
	}

	protected boolean getDrawLabel(final Site site)
	{
		return site.getCausalBeat() >= 0;
	}
}
