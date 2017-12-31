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
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.RotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultRotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultSwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

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

	@Before
	public void setUp() throws Exception
	{
		causalDiagramProperties = new CausalDiagramProperties();

		fhsToCausalDiagram = new FhsToCausalDiagram(causalDiagramProperties, new Hand[]{Hand.RIGHT, Hand.RIGHT, Hand.LEFT, Hand.LEFT});
		arrowFactory = new DefaultArrowFactory(causalDiagramProperties);
		swapFactory = new DefaultSwapFactory(causalDiagramProperties);
		rotationMarkerFactor = new DefaultRotationMarkerFactory(causalDiagramProperties);
		causalDiagramToSvg = new CausalDiagramToSvg(causalDiagramProperties, swapFactory, arrowFactory, rotationMarkerFactor);
	}

	@Test
	public void visualTest() throws Exception
	{
		// final FourHandedSiteswap siteswap = SiteswapFactory.getFourHandedSiteswap("88522");
		final FourHandedSiteswap siteswap = SiteswapFactory.getFourHandedSiteswap("9968926");
		// final FourHandedSiteswap siteswap = SiteswapFactory.getFourHandedSiteswap("975");
		final CausalDiagram causalDiagram = fhsToCausalDiagram.apply(siteswap);
		final SVGGraphics2D graphics2D = causalDiagramToSvg.apply(causalDiagram, point -> new SVGGraphics2D(point.x, point.y));
		final String svgDocument = graphics2D.getSVGDocument();

		final File file = getFile("visualTest");
		FileUtils.writeStringToFile(file, svgDocument, StandardCharsets.UTF_8);

		LOG.info("{}", FourHandedSiteswapToHefflishSequence.get().apply(siteswap, 0));
		LOG.info("{}", FourHandedSiteswapToHefflishSequence.get().apply(siteswap, 1));

		LOG.info("{}",file.toURI().toURL());
	}

	private File getFile(final String testName)
	{
		final File file = new File(PREFIX);
		return new File(file, testName + XML_EXTENSION);
	}
}