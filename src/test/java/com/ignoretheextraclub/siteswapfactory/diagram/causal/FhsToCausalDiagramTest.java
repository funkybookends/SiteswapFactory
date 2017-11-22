package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;

public class FhsToCausalDiagramTest
{
    private FhsToCausalDiagram fhsToCausalDiagram = new FhsToCausalDiagram(new CausalDiagramProperties());

    @Test
    public void fhs5() throws Exception
    {
        final CausalDiagram result = fhsToCausalDiagram.convert(SiteswapFactory.getFourHandedSiteswap("5"));

        final CausalDiagram expected = new CausalDiagram.Builder()
            .addCause(0, 1, 0, 0.5, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.RIGHT)
            .addCause(0, 1, 1, 1.5, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.LEFT)
            .addCause(1, 0, 0.5, 1, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT)
            .addCause(1, 0, 1.5, 2, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.RIGHT)
            .build();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void fhs567() throws Exception
    {
        final CausalDiagram result = fhsToCausalDiagram.convert(SiteswapFactory.getFourHandedSiteswap("756"));

        final CausalDiagram expected = new CausalDiagram.Builder()
            .addCause(0, 1, 0, 1.5, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT) //   7
            .addCause(0, 0, 1, 2, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.RIGHT) //     6
            .addCause(0, 1, 2, 2.5, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.RIGHT) //  5

            .addCause(0, 1, 3, 4.5, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.RIGHT) //   7
            .addCause(0, 0, 4, 5, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT) //     6
            .addCause(0, 1, 5, 5.5, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.LEFT) //    5

            .addCause(1, 0, 0.5, 1, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT) //   5
            .addCause(1, 0, 1.5, 3, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.LEFT) //    7
            .addCause(1, 1, 2.5, 3.5, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT) // 6

            .addCause(1, 0, 3.5, 4, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.RIGHT) //   5
            .addCause(1, 0, 4.5, 6, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.RIGHT) //  7
            .addCause(1, 1, 5.5, 6.5, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.RIGHT) // 6

            .build();

        Assertions.assertThat(result).isEqualTo(expected);
    }
}