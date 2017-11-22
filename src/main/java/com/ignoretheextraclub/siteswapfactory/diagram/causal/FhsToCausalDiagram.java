package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

public class FhsToCausalDiagram
{
    private static final com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram.Hand[] DEFAULT_HAND_THROW_ORDER =
        new CausalDiagram.Hand[]{CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.RIGHT, CausalDiagram.Hand.LEFT, CausalDiagram.Hand.LEFT};

    private final CausalDiagramProperties properties;
    private final CausalDiagram.Hand[] handThrowingOrder;

    public FhsToCausalDiagram(final CausalDiagramProperties properties)
    {
        this(properties, DEFAULT_HAND_THROW_ORDER);
    }

    public FhsToCausalDiagram(final CausalDiagramProperties properties, final CausalDiagram.Hand[] handThrowingOrder)
    {
        this.properties = properties;

        if (handThrowingOrder == null || handThrowingOrder.length != FourHandedSiteswap.NUM_HANDS)
        {
            throw new IllegalArgumentException("handThrowingOrder must be of length 4");
        }

        this.handThrowingOrder = handThrowingOrder;
    }

    public CausalDiagram convert(final FourHandedSiteswap fhs)
    {
        final CausalDiagram.Builder builder = new CausalDiagram.Builder();
        final int numThrowsToDisplay = getNumThrowsToDisplay(fhs);

        final ArrayLoopingIterator<Thro> thros = new ArrayLoopingIterator<>(fhs.getThrows());

        for (int beat = 0; beat < numThrowsToDisplay; beat++)
        {
            final int landingBeat = beat + thros.next().getNumBeats();

            builder.addCause
                (
                    getJugglerThrowingOnBeat(beat),
                    getJugglerThrowingOnBeat(landingBeat),
                    getCausalBeat(beat),
                    getCausalBeat(landingBeat - FourHandedSiteswap.NUM_HANDS),
                    getHandThrowingOnBeat(beat),
                    getHandThrowingOnBeat(landingBeat)
                );
        }

        return builder.build();
    }

    private CausalDiagram.Hand getHandThrowingOnBeat(final int beat)
    {
        return handThrowingOrder[beat % FourHandedSiteswap.NUM_HANDS];
    }

    private double getCausalBeat(final int beat)
    {
        return beat / 2.0;
    }

    private int getJugglerThrowingOnBeat(final int beat)
    {
        return beat % 2;
    }

    private int getNumThrowsToDisplay(final FourHandedSiteswap fhs)
    {
        return fhs.getPeriod() * properties.getMinNumRepititions() * 2;
    }
}
