package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.impl;

import java.awt.*;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultRotationMarkerGraphicTest
{

	private static final int MIN_Y = 30;
	private static final int MAX_Y = 40;
	private static final int X = 50;
	private static final Stroke STROKE = mock(Stroke.class);
	private static final Paint PAINT = mock(Paint.class);

	@Test
	public void testDrawsCorrectLine() throws Exception
	{
		final Graphics2D graphics2D = mock(Graphics2D.class);
		final DefaultRotationMarkerGraphic graphic = new DefaultRotationMarkerGraphic(MIN_Y, MAX_Y, X, STROKE, PAINT);

		graphic.draw(graphics2D);

		verify(graphics2D).setPaint(PAINT);
		verify(graphics2D).setStroke(STROKE);
		verify(graphics2D).drawLine(X, MIN_Y, X, MAX_Y);
	}
}