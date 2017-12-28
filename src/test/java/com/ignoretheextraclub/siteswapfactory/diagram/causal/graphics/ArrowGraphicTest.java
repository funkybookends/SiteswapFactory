package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;
import java.awt.geom.QuadCurve2D;

import org.assertj.core.api.Assertions;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.DefaultSwapGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl.SwapToSwapArrowGraphic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ArrowGraphicTest
{
	private static final Logger LOG = LoggerFactory.getLogger(ArrowGraphicTest.class);

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

		final ArgumentCaptor<QuadCurve2D.Double> curveCaptor = ArgumentCaptor.forClass(QuadCurve2D.Double.class);

		final QuadCurve2D.Double expectedCurve = new QuadCurve2D.Double(57.0, 95.0, 150.0, 25.0, 243.0, 95.0);

		verify(svgGraphics2D).draw(curveCaptor.capture());
		verify(svgGraphics2D).drawLine(232, 78, 243, 95);
		verify(svgGraphics2D).drawLine(223, 89, 243, 95);

		final QuadCurve2D.Double actual = curveCaptor.getValue();
		Assertions.assertThat(actual).isEqualToComparingFieldByField(expectedCurve);
	}

	@Test
	public void verifyDrawsLineAndArrowHead() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final Graphics2D svgGraphics2D = mock(Graphics2D.class);

		final SwapToSwapArrowGraphic arrow = new SwapToSwapArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.draw(svgGraphics2D);

		verify(svgGraphics2D).drawLine(59, 100, 241, 100);
		verify(svgGraphics2D).drawLine(222, 93, 241, 100);
		verify(svgGraphics2D).drawLine(222, 106, 241, 100);
	}

	@Test
	public void testGetBounds() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final SwapToSwapArrowGraphic arrow = new SwapToSwapArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.translateControl(0, -75);

		final Rectangle bounds = arrow.getBounds();
		final Rectangle expected = new Rectangle(57, 25, 186, 70);

		Assertions.assertThat(bounds).isEqualTo(expected);
	}
}