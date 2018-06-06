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
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.MAX_NUM_THROWS_DISPLAYED;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.MIN_NUM_HALF_ROTATIONS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.PREFERRED_NUM_THROWS;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap.NUM_HANDS;


/**
 * Converts a {@link FourHandedSiteswap} to a {@link CausalDiagram}
 */
public class FhsToCausalDiagram implements Function<FourHandedSiteswap, CausalDiagram>
{
	private static final Logger LOG = LoggerFactory.getLogger(FhsToCausalDiagram.class);

	private static final Hand[] DEFAULT_HAND_THROW_ORDER = new Hand[]{RIGHT, RIGHT, LEFT, LEFT};

	private final CausalDiagramProperties properties;

	private final Hand[] handThrowingOrder;

	/**
	 * Creates a new Converter that uses the {@link #DEFAULT_HAND_THROW_ORDER}
	 *
	 * @param properties The causal diagram properties
	 */
	public FhsToCausalDiagram(final CausalDiagramProperties properties)
	{
		this(properties, DEFAULT_HAND_THROW_ORDER);
	}

	/**
	 * Creates a new converter that will use the provided handThrowing order as the default
	 *
	 * @param properties        The causal diagram properties
	 * @param handThrowingOrder The throwing order of hands for the causal diagram
	 */
	public FhsToCausalDiagram(final CausalDiagramProperties properties, final Hand[] handThrowingOrder)
	{
		this.properties = properties;

		validateHandThrowingOrder(handThrowingOrder);

		this.handThrowingOrder = handThrowingOrder;
	}

	public CausalDiagram apply(final FourHandedSiteswap fhs)
	{
		return apply(fhs, handThrowingOrder);
	}

	public CausalDiagram apply(final FourHandedSiteswap fhs, final Hand[] handThrowingOrder)
	{
		validateHandThrowingOrder(handThrowingOrder);

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
						getHandThrowingOnBeat(beat, handThrowingOrder),
						getHandThrowingOnBeat(landingBeat, handThrowingOrder)
					);
			}
		}

		builder.setFullRotationBeat(fhs.getPeriod() * (fhs.getPeriod() % 2 == 0 ? 1 : 2));

		return builder.build();
	}

	private boolean stillMoreThrosToDisplay(final int currentBeat, final int period)
	{
		if (currentBeat > properties.getInt(MAX_NUM_THROWS_DISPLAYED) * 2)
		{
			LOG.trace("Maximum number of throws reached: currentBeat={}, period={}, MaxMunThrowsDisplayed={}", currentBeat, period, properties.getInt(MAX_NUM_THROWS_DISPLAYED));
			return false;
		}
		if (currentBeat < (period * (period % 2 == 0 ? 1 : 2) * properties.getInt(MIN_NUM_HALF_ROTATIONS)))
		{
			LOG.trace("Minimum number of repititions not reached: currentBeat={}, period={}, MinNumRepititions={}", currentBeat, period, properties.getInt(MIN_NUM_HALF_ROTATIONS));
			return true;
		}
		if (currentBeat < properties.getInt(PREFERRED_NUM_THROWS) * 2)
		{
			LOG.trace("Preferred number of throws not reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getInt(PREFERRED_NUM_THROWS));
			return true;
		}
		LOG.trace("Preferred number of throws reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getInt(PREFERRED_NUM_THROWS));
		return false;
	}

	private Hand getHandThrowingOnBeat(final int beat, final Hand[] handThrowingOrder)
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

	private void validateHandThrowingOrder(final Hand[] handThrowingOrder)
	{
		if (handThrowingOrder == null || handThrowingOrder.length != NUM_HANDS)
		{
			throw new IllegalArgumentException("handThrowingOrder must be of length 4");
		}

		for (final Hand hand : handThrowingOrder)
		{
			Objects.requireNonNull(hand, "handThrowingOrder cannot contain any nulls");
		}
	}
}
