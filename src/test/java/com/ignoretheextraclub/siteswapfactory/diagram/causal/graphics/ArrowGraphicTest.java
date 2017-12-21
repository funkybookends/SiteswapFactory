package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import java.awt.*;
import java.awt.geom.QuadCurve2D;

import org.assertj.core.api.Assertions;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.VisualTestRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ArrowGraphicTest
{
	private static final Logger LOG = LoggerFactory.getLogger(ArrowGraphicTest.class);

	@Rule
	public VisualTestRule visualTestRule = new VisualTestRule();

	@Rule
	public TestName testName = new TestName();

	private BasicStroke stroke = new BasicStroke(1);
	private DefaultSwapGraphic finish = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
	private DefaultSwapGraphic start = new DefaultSwapGraphic.SwapBuilder().withxCenter(150).withyCenter(100).withLabel('L').createSwap();

	@Test
	public void testArrowWithArrowHead() throws Exception
	{
		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(200, 200);

		final PointToPointArrowGraphic arrow = new PointToPointArrowGraphic.ArrowGraphicBuilder()
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

		visualTestRule.test(getClass(), testName, svgGraphics2D);
	}

	@Test
	public void testTranslateControl() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic second = new DefaultSwapGraphic.SwapBuilder().withxCenter(150).withyCenter(100).withLabel('L').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(300, 200);

		final PointToPointArrowGraphic arrow = new PointToPointArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
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

		final PointToPointArrowGraphic arrow = new PointToPointArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.translateControl(0, -75);
		arrow.draw(svgGraphics2D);

		final ArgumentCaptor<QuadCurve2D.Double> curveCaptor = ArgumentCaptor.forClass(QuadCurve2D.Double.class);

		final QuadCurve2D.Double expectedCurve = new QuadCurve2D.Double(58.0, 94.0, 150.0, 25.0, 242.0, 94.0);

		verify(svgGraphics2D).draw(curveCaptor.capture());
		verify(svgGraphics2D).drawLine(231, 77, 242, 94);
		verify(svgGraphics2D).drawLine(222, 88, 242, 94);

		final QuadCurve2D.Double actual = curveCaptor.getValue();
		Assertions.assertThat(actual).isEqualToComparingFieldByField(expectedCurve);
	}

	@Test
	public void verifyDrawsLineAndArrowHead() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final Graphics2D svgGraphics2D = mock(Graphics2D.class);

		final PointToPointArrowGraphic arrow = new PointToPointArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.draw(svgGraphics2D);

		verify(svgGraphics2D).drawLine(60, 100, 240, 100);
		verify(svgGraphics2D).drawLine(221, 93, 240, 100);
		verify(svgGraphics2D).drawLine(221, 106, 240, 100);
	}

	@Test
	public void testGetBounds() throws Exception
	{
		final DefaultSwapGraphic first = new DefaultSwapGraphic.SwapBuilder().withxCenter(50).withyCenter(100).withLabel('R').createSwap();
		final DefaultSwapGraphic third = new DefaultSwapGraphic.SwapBuilder().withxCenter(250).withyCenter(100).withLabel('L').createSwap();

		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(300, 200);

		final PointToPointArrowGraphic arrow = new PointToPointArrowGraphic.ArrowGraphicBuilder().withStart(first).withFinish(third).withControl(null).withStroke(stroke).withDisplayArrowHead(true).withArrowHeadLength(20).withArrowHeadPointyness(9.0).createArrowGraphic();
		arrow.translateControl(0, -75);

		final Rectangle bounds = arrow.getBounds();
		final Rectangle expected = new Rectangle(58, 25, 184, 69);

		Assertions.assertThat(bounds).isEqualTo(expected);
	}
}