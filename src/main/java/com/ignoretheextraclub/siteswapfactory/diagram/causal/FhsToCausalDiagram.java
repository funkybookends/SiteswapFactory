package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Objects;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram.Hand;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram.Hand.LEFT;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram.Hand.RIGHT;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap.NUM_HANDS;

public class FhsToCausalDiagram
{
    private static final Hand[] DEFAULT_HAND_THROW_ORDER = new Hand[]{RIGHT, RIGHT, LEFT, LEFT};

    private final CausalDiagramProperties properties;
    private final Hand[] handThrowingOrder;

    public FhsToCausalDiagram(final CausalDiagramProperties properties)
    {
        this(properties, DEFAULT_HAND_THROW_ORDER);
    }

    public FhsToCausalDiagram(final CausalDiagramProperties properties, final Hand[] handThrowingOrder)
    {
        this.properties = properties;

        if (handThrowingOrder == null || handThrowingOrder.length != NUM_HANDS)
        {
            throw new IllegalArgumentException("handThrowingOrder must be of length 4");
        }

        for (final Hand hand : handThrowingOrder)
        {
            Objects.requireNonNull(hand, "handThrowingOrder cannot contain any nulls");
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
                    getCausalBeat(landingBeat - NUM_HANDS),
                    getHandThrowingOnBeat(beat),
                    getHandThrowingOnBeat(landingBeat)
                );
        }

        return builder.build();
    }

    private Hand getHandThrowingOnBeat(final int beat)
    {
        return handThrowingOrder[beat % NUM_HANDS];
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
