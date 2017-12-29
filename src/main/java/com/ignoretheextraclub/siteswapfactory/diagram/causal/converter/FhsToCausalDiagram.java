package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import java.util.Objects;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.impl.DefaultCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand.LEFT;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand.RIGHT;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap.NUM_HANDS;

public class FhsToCausalDiagram implements Function<FourHandedSiteswap, CausalDiagram>
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

	public CausalDiagram apply(final FourHandedSiteswap fhs)
	{
		final DefaultCausalDiagram.Builder builder = new DefaultCausalDiagram.Builder();

		final ArrayLoopingIterator<Thro> thros = new ArrayLoopingIterator<>(fhs.getThrows());

		for (int beat = 0; stillMoreThrosToDisplay(beat, fhs.getPeriod()); beat++)
		{
			final int landingBeat = beat + thros.next().getNumBeats();
			final int landingCausalBeat = landingBeat - NUM_HANDS;

			if (landingCausalBeat >= 0)
			{
				builder.addCause
					(
						getJugglerThrowingOnBeat(beat),
						getJugglerThrowingOnBeat(landingBeat),
						getCausalBeat(beat),
						getCausalBeat(landingCausalBeat),
						getHandThrowingOnBeat(beat),
						getHandThrowingOnBeat(landingBeat)
					);
			}
		}

		if (fhs.getPeriod() % 2 == 0)
		{
			builder.setFullRotationBeat(fhs.getPeriod());
		}
		else
		{
			builder.setFullRotationBeat(fhs.getPeriod() * 2);
		}

		return builder.build();
	}

	private boolean stillMoreThrosToDisplay(final int currentBeat, final int period)
	{
		if (currentBeat > properties.getMaxNumThrowsDisplayed() * 2)
		{
			LOG.info("Maximum number of throws reached: currentBeat={}, period={}, MaxMunThrowsDisplayed={}", currentBeat, period, properties.getMaxNumThrowsDisplayed());
			return false;
		}
		if (currentBeat < ((period % 2 == 0 ? period : period * 2) * properties.getMinNumHalfRotations()))
		{
			LOG.info("Minimum number of repititions not reached: currentBeat={}, period={}, MinNumRepititions={}", currentBeat, period, properties.getMinNumHalfRotations());
			return true;
		}
		if (currentBeat < properties.getPreferredNumThrows() * 2)
		{
			LOG.info("Preferred number of throws not reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getPreferredNumThrows());
			return true;
		}
		LOG.info("Preferred number of throws reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getPreferredNumThrows());
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
