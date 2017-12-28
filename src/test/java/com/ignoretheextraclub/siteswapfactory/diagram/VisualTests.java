package com.ignoretheextraclub.siteswapfactory.diagram;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramToSvg;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.FhsToCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultSwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

public class VisualTests
{
	private static final Logger LOG = LoggerFactory.getLogger(VisualTests.class);
	private static final String PREFIX = "src/test/resources/visualtests";
	private static final String XML_EXTENSION = ".html";

	private CausalDiagramProperties causalDiagramProperties;
	private FhsToCausalDiagram fhsToCausalDiagram;
	private ArrowFactory arrowFactory;
	private SwapFactory swapFactory;
	private CausalDiagramToSvg causalDiagramToSvg;
	private Function<String, SVGGraphics2D> siteswapToCausalDiagramGraphic;

	@Before
	public void setUp() throws Exception
	{
		causalDiagramProperties = new CausalDiagramProperties();

		fhsToCausalDiagram = new FhsToCausalDiagram(causalDiagramProperties);
		arrowFactory = new DefaultArrowFactory(causalDiagramProperties);
		swapFactory = new DefaultSwapFactory(causalDiagramProperties);
		causalDiagramToSvg = new CausalDiagramToSvg(causalDiagramProperties, arrowFactory, swapFactory);

		siteswapToCausalDiagramGraphic = ((Function<String, FourHandedSiteswap>) SiteswapFactory::getFourHandedSiteswap)
			.andThen(fhsToCausalDiagram)
			.andThen(causalDiagramToSvg);
	}

	@Test
	public void visualTest() throws Exception
	{
		final SVGGraphics2D graphic = siteswapToCausalDiagramGraphic.apply("72786");
		// final SVGGraphics2D graphic = siteswapToCausalDiagramGraphic.apply("7278672786");

		final String svgDocument = graphic.getSVGDocument();

		final File file = getFile("visualTest");
		FileUtils.writeStringToFile(file, svgDocument, StandardCharsets.UTF_8);

		LOG.info("{}",file.toURI().toURL());
	}

	private File getFile(final String testName)
	{
		final File file = new File(PREFIX);
		return new File(file, testName + XML_EXTENSION);
	}
}
