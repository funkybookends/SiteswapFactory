package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.function.Function;

import org.assertj.core.api.Assertions;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.VisualTestRule;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.DefaultGraphicFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

public class FhsToCausalDiagramTest
{
    private static final Logger LOG = LoggerFactory.getLogger(FhsToCausalDiagramTest.class);

    private CausalDiagramProperties causalDiagramProperties;
    private FhsToCausalDiagram fhsToCausalDiagram;
    private ArrowFactory arrowFactory;
    private SwapFactory swapFactory;
    private CausalDiagramToSvg causalDiagramToSvg;
    private Function<String, SVGGraphics2D> siteswapToCausalDiagramGraphic;

    @Rule
    public VisualTestRule visualTestRule = new VisualTestRule();
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception
    {
        causalDiagramProperties = new CausalDiagramProperties()
            .withPreferredNumThrows(6)
            .withMinNumRepititions(2)
            .withMinNumThrowsDisplayed(4)
            .withMaxMunThrowsDisplayed(10);

        fhsToCausalDiagram = new FhsToCausalDiagram(causalDiagramProperties);
        arrowFactory = new DefaultGraphicFactory(causalDiagramProperties);
        swapFactory = new DefaultGraphicFactory(causalDiagramProperties);
        causalDiagramToSvg = new CausalDiagramToSvg(causalDiagramProperties, arrowFactory, swapFactory);

        siteswapToCausalDiagramGraphic = ((Function<String, FourHandedSiteswap>) SiteswapFactory::getFourHandedSiteswap)
            .andThen(fhsToCausalDiagram)
            .andThen(causalDiagramToSvg);

    }

    @Test
    public void fhs5() throws Exception
    {
        final CausalDiagram result = fhsToCausalDiagram.apply(SiteswapFactory.getFourHandedSiteswap("5"));

        final CausalDiagram expected = new DefaultCausalDiagram.Builder()
            .addCause(0, 1, 0, 0.5, Hand.RIGHT, Hand.RIGHT) // J1
            .addCause(0, 1, 1, 1.5, Hand.LEFT, Hand.LEFT)   // J1
            .addCause(0, 1, 2, 2.5, Hand.RIGHT, Hand.RIGHT) // J1
            .addCause(0, 1, 3, 3.5, Hand.LEFT, Hand.LEFT)   // J1
            .addCause(0, 1, 4, 4.5, Hand.RIGHT, Hand.RIGHT) // J1
            .addCause(0, 1, 5, 5.5, Hand.LEFT, Hand.LEFT)   // J1

            .addCause(1, 0, 0.5, 1, Hand.RIGHT, Hand.LEFT)  // J2
            .addCause(1, 0, 1.5, 2, Hand.LEFT, Hand.RIGHT)  // J2
            .addCause(1, 0, 2.5, 3, Hand.RIGHT, Hand.LEFT)  // J2
            .addCause(1, 0, 3.5, 4, Hand.LEFT, Hand.RIGHT)  // J2
            .addCause(1, 0, 4.5, 5, Hand.RIGHT, Hand.LEFT)  // J2
            .addCause(1, 0, 5.5, 6, Hand.LEFT, Hand.RIGHT)  // J2
            .build();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void fhs567() throws Exception
    {
        final CausalDiagram result = fhsToCausalDiagram.apply(SiteswapFactory.getFourHandedSiteswap("756"));

        final CausalDiagram expected = new DefaultCausalDiagram.Builder()
            .addCause(0, 1, 0, 1.5, Hand.RIGHT, Hand.LEFT) //   7
            .addCause(0, 0, 1, 2, Hand.LEFT, Hand.RIGHT) //     6
            .addCause(0, 1, 2, 2.5, Hand.RIGHT, Hand.RIGHT) //  5

            .addCause(0, 1, 3, 4.5, Hand.LEFT, Hand.RIGHT) //   7
            .addCause(0, 0, 4, 5, Hand.RIGHT, Hand.LEFT) //     6
            .addCause(0, 1, 5, 5.5, Hand.LEFT, Hand.LEFT) //    5

            .addCause(1, 0, 0.5, 1, Hand.RIGHT, Hand.LEFT) //   5
            .addCause(1, 0, 1.5, 3, Hand.LEFT, Hand.LEFT) //    7
            .addCause(1, 1, 2.5, 3.5, Hand.RIGHT, Hand.LEFT) // 6

            .addCause(1, 0, 3.5, 4, Hand.LEFT, Hand.RIGHT) //   5
            .addCause(1, 0, 4.5, 6, Hand.RIGHT, Hand.RIGHT) //  7
            .addCause(1, 1, 5.5, 6.5, Hand.LEFT, Hand.RIGHT) // 6

            .build();

        Assertions.assertThat(result).isEqualTo(expected);

        final CausalDiagramToSvg causalDiagramToSvg = new CausalDiagramToSvg(new CausalDiagramProperties(), arrowFactory, swapFactory);
        final SVGGraphics2D convert = causalDiagramToSvg.apply(result);
    }

    @Test
    public void test756() throws Exception
    {
        visualTestRule.test(getClass(), testName, siteswapToCausalDiagramGraphic.apply("756"));
    }

    @Test
    public void test9A678() throws Exception
    {
        visualTestRule.test(getClass(), testName, siteswapToCausalDiagramGraphic.apply("9A678"));
    }

    @Test
    public void test77786() throws Exception
    {
        visualTestRule.test(getClass(), testName, siteswapToCausalDiagramGraphic.apply("77786"));
    }
}