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

package com.ignoretheextraclub.siteswapfactory.diagram;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish.FourHandedSiteswapToHefflishSequence;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramDrawer;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramToSvg;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.FhsToCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.PassingSiteswapToCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.RotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultRotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultSwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.impl.PassingSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;

public class VisualTests
{
	private static final Logger LOG = LoggerFactory.getLogger(VisualTests.class);
	private static final String PREFIX = "visualtests";
	private static final String XML_EXTENSION = ".html";

	private CausalDiagramProperties causalDiagramProperties;
	private FhsToCausalDiagram fhsToCausalDiagram;
	private ArrowFactory arrowFactory;
	private SwapFactory swapFactory;
	private CausalDiagramDrawer causalDiagramToSvg;
	private RotationMarkerFactory rotationMarkerFactor;
	private PassingSiteswapToCausalDiagram passingSiteswapToCausalDiagram;

	@Before
	public void setUp() throws Exception
	{
		causalDiagramProperties = new CausalDiagramProperties();

		fhsToCausalDiagram = new FhsToCausalDiagram(causalDiagramProperties, new Hand[]{Hand.RIGHT, Hand.RIGHT, Hand.LEFT, Hand.LEFT});
		passingSiteswapToCausalDiagram = new PassingSiteswapToCausalDiagram(causalDiagramProperties);
		arrowFactory = new DefaultArrowFactory(causalDiagramProperties);
		swapFactory = new DefaultSwapFactory(causalDiagramProperties);
		rotationMarkerFactor = new DefaultRotationMarkerFactory(causalDiagramProperties);
		causalDiagramToSvg = new CausalDiagramToSvg(causalDiagramProperties, swapFactory, arrowFactory, rotationMarkerFactor);
	}

	@Test
	public void visualTest() throws Exception
	{
		// final FourHandedSiteswap siteswap = FourHandedSiteswapFactory.getFourHandedSiteswap("88522");
		final FourHandedSiteswap siteswap = FourHandedSiteswapFactory.getFourHandedSiteswap("9968926");
		// final FourHandedSiteswap siteswap = FourHandedSiteswapFactory.getFourHandedSiteswap("975");
		final CausalDiagram causalDiagram = fhsToCausalDiagram.apply(siteswap);
		final SVGGraphics2D graphics2D = causalDiagramToSvg.apply(causalDiagram, point -> new SVGGraphics2D((int) point.getX(), (int) point.getY()));
		final String svgDocument = graphics2D.getSVGDocument();

		final File file = getFile("visualTest");
		FileUtils.writeStringToFile(file, svgDocument, StandardCharsets.UTF_8);

		LOG.info("{}", FourHandedSiteswapToHefflishSequence.get().apply(siteswap, 0));
		LOG.info("{}", FourHandedSiteswapToHefflishSequence.get().apply(siteswap, 1));

		LOG.info("{}",file.toURI().toURL());
	}

	@Test
	public void visualTest1() throws Exception
	{
		final PassingSiteswap siteswap = (PassingSiteswap) PassingSiteswapFactory.getPassingSiteswap("<3|4p><4|3><4p|3>");
		final CausalDiagram causalDiagram = passingSiteswapToCausalDiagram.apply(siteswap);
		final SVGGraphics2D graphics2D = causalDiagramToSvg.apply(causalDiagram, point -> new SVGGraphics2D((int) point.getX(), (int) point.getY()));
		final String svgDocument = graphics2D.getSVGDocument();

		final File file = getFile("visualTest1");
		FileUtils.writeStringToFile(file, svgDocument, StandardCharsets.UTF_8);

		LOG.info("{}",file.toURI().toURL());
	}

	private File getFile(final String testName)
	{
		final File file = new File(PREFIX);
		return new File(file, testName + XML_EXTENSION);
	}
}
