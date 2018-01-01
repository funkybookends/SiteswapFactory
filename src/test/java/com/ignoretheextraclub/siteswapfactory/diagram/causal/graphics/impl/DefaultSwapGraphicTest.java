package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.assertj.core.api.Assertions;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultSwapGraphicTest
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultSwapGraphicTest.class);
	private static final Font FONT = new Font("Arial", Font.PLAIN, 12);
	private static final double MAX_X = 106.36396103067892;
	private static final double MIN_Y = 93.63603896932108;
	private static final double MIN_Y_VERTICAL = 91;
	private static final double MAX_Y_VERTICAL = 109;

	@Test
	public void visualTest() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(50)
			.withyCenter(50)
			.withLabel('L')
			.withLabelFont(new Font("Arial", Font.PLAIN, 12))
			.withCircleStroke(new BasicStroke(1))
			.createSwap();

		final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(500, 500);
		swap.draw(svgGraphics2D);
	}

	@Test
	public void GIVEN_swap_WHEN_getBoundingBox_EXPECT_correctBoundingBox() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('L')
			.withLabelFont(new Font("Arial", Font.PLAIN, 12))
			.withCircleStroke(new BasicStroke(1))
			.createSwap();

		final Rectangle2D bounds = swap.getBounds();
		final Rectangle2D expected = new Rectangle2D.Double(MIN_Y_VERTICAL, MIN_Y_VERTICAL, 18, 18);

		Assertions.assertThat(bounds).isEqualTo(expected);
	}

	@Test
	public void GIVEN_swap_WHEN_getMinDocumentSize_EXPECT_correctPoint() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('L')
			.createSwap();

		final Point2D bounds = swap.getMinDocumentSize();
		final Point2D expected = new Point2D.Double(MAX_Y_VERTICAL, MAX_Y_VERTICAL);

		Assertions.assertThat(bounds).isEqualTo(expected);
	}

	@Test
	public void verifyFontIsSet() throws Exception
	{
		final Font font = FONT;
		final BasicStroke stroke = mock(BasicStroke.class);

		final DefaultSwapGraphic l = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(10)
			.withyCenter(10)
			.withLabel('l')
			.withLabelFont(font)
			.withCircleStroke(stroke)
			.createSwap();

		final Graphics2D svgGraphics2D = mock(Graphics2D.class);
		when(svgGraphics2D.getFontMetrics(font)).thenReturn(mock(FontMetrics.class));
		l.draw(svgGraphics2D);
		verify(svgGraphics2D).setFont(font);
	}

	@Test
	public void verifyStrokeIsSet() throws Exception
	{
		final BasicStroke stroke = mock(BasicStroke.class);
		final DefaultSwapGraphic l = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(10)
			.withyCenter(10)
			.withLabel('l')
			.withLabelFont(FONT)
			.withCircleStroke(stroke)
			.createSwap();

		final Graphics2D svgGraphics2D = mock(Graphics2D.class);
		when(svgGraphics2D.getFontMetrics(FONT)).thenReturn(mock(FontMetrics.class));
		l.draw(svgGraphics2D);
		verify(svgGraphics2D).setStroke(stroke);
	}

	@Test
	public void testGetEdgePointClosestToLeft() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point left = new Point(50, 100);
		Assertions.assertThat(swap.getConnectionPointFor(left)).isEqualTo(new Point2D.Double(MIN_Y_VERTICAL, 100.0));
	}

	@Test
	public void testGetEdgePointClosestToRight() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point right = new Point(150, 100);
		Assertions.assertThat(swap.getConnectionPointFor(right)).isEqualTo(new Point2D.Double(MAX_Y_VERTICAL, 100));
	}

	@Test
	public void testGetEdgePointClosestToTop() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point top = new Point(100, 50);
		Assertions.assertThat(swap.getConnectionPointFor(top)).isEqualTo(new Point2D.Double(100, MIN_Y_VERTICAL));
	}

	@Test
	public void testGetEdgePointClosestToBottom() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point bottom = new Point(100, 150);
		Assertions.assertThat(swap.getConnectionPointFor(bottom)).isEqualTo(new Point2D.Double(100, MAX_Y_VERTICAL));
	}

	@Test
	public void testGetEdgePointClosestToNorthWest() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point2D left = new Point2D.Double(50, 50);
		Assertions.assertThat(swap.getConnectionPointFor(left)).isEqualTo(new Point2D.Double(MIN_Y, MIN_Y));
	}

	@Test
	public void testGetEdgePointClosestToNorthEast() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point2D right = new Point2D.Double(150, 50);
		Assertions.assertThat(swap.getConnectionPointFor(right)).isEqualTo(new Point2D.Double(MAX_X, MIN_Y));
	}

	@Test
	public void testGetEdgePointClosestToSouthEast() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point2D top = new Point2D.Double(150, 150);
		Assertions.assertThat(swap.getConnectionPointFor(top)).isEqualTo(new Point2D.Double(MAX_X, MAX_X));
	}

	@Test
	public void testGetEdgePointClosestToSouthWest() throws Exception
	{
		final DefaultSwapGraphic swap = new DefaultSwapGraphic.SwapBuilder()
			.withxCenter(100)
			.withyCenter(100)
			.withLabel('l')
			.createSwap();

		final Point2D bottom = new Point2D.Double(50, 150);
		Assertions.assertThat(swap.getConnectionPointFor(bottom)).isEqualTo(new Point2D.Double(MIN_Y, MAX_X));
	}
}