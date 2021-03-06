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

package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SwapToSwapArrowGraphicTest
{
	private static final Logger LOG = LoggerFactory.getLogger(SwapToSwapArrowGraphicTest.class);

	private BasicStroke stroke = new BasicStroke(1);
	private DefaultSwapGraphic finish = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
	private DefaultSwapGraphic start = new DefaultSwapGraphic.SwapBuilder().withxCenter(150).withyCenter(100).withLabel('L').createSwap();

	@Test
	public void testArrowWithArrowHead() throws Exception
	{
		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(200, 200);

		final SwapToSwapArrowGraphic arrow = new SwapToSwapArrowGraphic.ArrowGraphicBuilder()
			.withStart(start)
			.withFinish(finish)
			.withControl(null)
			.withStroke(stroke)
			.withDisplayArrowHead(true)
			.withArrowHeadLength(20)
			.withArrowHeadPointyness(9.0)
			.createArrowGraphic();

		arrow.draw(svgGraphics2D);
		start.draw(svgGraphics2D);
		finish.draw(svgGraphics2D);
	}

	@Test
	public void testTranslateControl() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic second = new DefaultSwapGraphic.SwapBuilder().withxCenter(150).withyCenter(100).withLabel('L').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(300, 200);

		final SwapToSwapArrowGraphic arrow = new SwapToSwapArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.translateControl(0, -75);

		first.draw(svgGraphics2D);
		second.draw(svgGraphics2D);
		third.draw(svgGraphics2D);
		arrow.draw(svgGraphics2D);

		LOG.info("\n\n{}\n\n", svgGraphics2D.getSVGDocument());
	}

	@Test
	public void verifyDrawsCurveAndArrowHead() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final Graphics2D svgGraphics2D = mock(Graphics2D.class);

		final SwapToSwapArrowGraphic arrow = new SwapToSwapArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.translateControl(0, -75);
		arrow.draw(svgGraphics2D);

		final ArgumentCaptor<Shape> curveCaptor = ArgumentCaptor.forClass(Shape.class);

		final QuadCurve2D.Double expectedCurve = new QuadCurve2D.Double(50.0, 100.0, 150.0, 25.0, 250.0, 100.0);

		verify(svgGraphics2D, times(3)).draw(curveCaptor.capture());
		assertThat(curveCaptor.getAllValues().get(1)).isEqualToComparingFieldByField(new Line2D.Double(239.0691597873335, 83.2513662573584, 250.0, 100.0));
		assertThat(curveCaptor.getAllValues().get(2)).isEqualToComparingFieldByField(new Line2D.Double(230.86067634751743, 94.1960108437798, 250.0, 100.0));

		final Shape actual = curveCaptor.getAllValues().get(0);
		assertThat(actual).isEqualToComparingFieldByField(expectedCurve);
	}

	@Test
	public void verifyDrawsLineAndArrowHead() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final Graphics2D svgGraphics2D = mock(Graphics2D.class);

		final SwapToSwapArrowGraphic arrow = new SwapToSwapArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.draw(svgGraphics2D);

		final ArgumentCaptor<Line2D.Double> lineArgCaptor = ArgumentCaptor.forClass(Line2D.Double.class);

		verify(svgGraphics2D, times(3)).draw(lineArgCaptor.capture());

		assertThat(lineArgCaptor.getAllValues().get(0)).isEqualToComparingFieldByField(new Line2D.Double(50.0, 100, 250.0, 100));
		assertThat(lineArgCaptor.getAllValues().get(1)).isEqualToComparingFieldByField(new Line2D.Double(231.20614758428184, 93.15959713348663, 250.0, 100));
		assertThat(lineArgCaptor.getAllValues().get(2)).isEqualToComparingFieldByField(new Line2D.Double(231.20614758428184, 106.84040286651337, 250.0, 100));
	}

	@Test
	public void testGetBounds() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final SwapToSwapArrowGraphic arrow = new SwapToSwapArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.translateControl(0, -75);

		final Rectangle2D bounds = arrow.getBounds();

		assertThat(bounds.getX()).isCloseTo(50, withPercentage(1));
		assertThat(bounds.getY()).isCloseTo(25, withPercentage(1));
		assertThat(bounds.getWidth()).isCloseTo(200, withPercentage(1));
		assertThat(bounds.getHeight()).isCloseTo(75, withPercentage(1));
	}
}