package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand.LEFT;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand.RIGHT;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap.NUM_HANDS;

public class FhsToCausalDiagram
{
	private static final Logger LOG = LoggerFactory.getLogger(FhsToCausalDiagram.class);

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
        final DefaultCausalDiagram.Builder builder = new DefaultCausalDiagram.Builder();

        final ArrayLoopingIterator<Thro> thros = new ArrayLoopingIterator<>(fhs.getThrows());

        for (int beat = 0; stillMoreThrosToDisplay(beat, fhs.getPeriod()); beat++)
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

    private boolean stillMoreThrosToDisplay(final int currentBeat, final int period)
    {
        if (currentBeat < properties.getMinNumThrowsDisplayed() * 2)
        {
        	LOG.debug("Minimum number of throws not reached: currentBeat={}, period={}, MinNumThrowsDisplayed={}", currentBeat, period, properties.getMinNumThrowsDisplayed());
            return true;
        }
        if (currentBeat < period * properties.getMinNumRepititions())
        {
        	LOG.debug("Minimum number of repititions not reached: currentBeat={}, period={}, MinNumRepititions={}", currentBeat, period, properties.getMinNumRepititions());
            return true;
        }
        if (currentBeat > properties.getMaxMunThrowsDisplayed() * 2)
        {
        	LOG.debug("Maximum number of throws reached: currentBeat={}, period={}, MaxMunThrowsDisplayed={}", currentBeat, period, properties.getMaxMunThrowsDisplayed());
            return false;
        }
        if (currentBeat < properties.getPreferredNumThrows() * 2)
        {
        	LOG.debug("Preferred number of throws not reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getPreferredNumThrows());
            return true;
        }
        LOG.debug("Preferred number of throws reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getPreferredNumThrows());
        return false;
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
}
