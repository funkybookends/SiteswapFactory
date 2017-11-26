package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.awt.*;

import org.assertj.core.api.JUnitSoftAssertions;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CausalDiagramToSvgTest
{
    private static final Logger LOG = LoggerFactory.getLogger(CausalDiagramToSvgTest.class);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void name() throws Exception
    {
        final SVGGraphics2D svgGraphics2D = new SVGGraphics2D(175, 125);
        svgGraphics2D.setZeroStrokeWidth(2.0);
        final Stroke stroke = new BasicStroke(2);
        final Font font = new Font(null, Font.PLAIN, 20);
        svgGraphics2D.drawString("R", 45, 60);
        svgGraphics2D.setFont(font);
        svgGraphics2D.setStroke(stroke);
        svgGraphics2D.drawLine(50, 50, 75, 100);
        svgGraphics2D.drawLine(75, 100, 100, 50);
        svgGraphics2D.drawLine(100, 50, 125, 100);
        svgGraphics2D.drawLine(125, 100, 150, 50);

        final String svgDocument = svgGraphics2D.getSVGElement();

        LOG.info("\n\n\n{}\n\n\n", svgDocument);
    }
}