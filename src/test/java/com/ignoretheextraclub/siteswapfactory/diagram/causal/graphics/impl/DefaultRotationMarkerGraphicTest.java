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

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
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
		final Line2D.Double expected = new Line2D.Double(X, MIN_Y, X, MAX_Y);
		final ArgumentCaptor<Line2D.Double> captor = ArgumentCaptor.forClass(Line2D.Double.class);
		verify(graphics2D).draw(captor.capture());

		assertThat(captor.getValue()).isEqualToComparingFieldByFieldRecursively(expected);
	}
}