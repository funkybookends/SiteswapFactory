package com.ignoretheextraclub.siteswapfactory.diagram.causal.converter;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.impl.DefaultCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.MAX_NUM_THROWS_DISPLAYED;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.MIN_NUM_HALF_ROTATIONS;
import static com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties.PREFERRED_NUM_THROWS;

public class PassingSiteswapToCausalDiagram implements Function<PassingSiteswap, CausalDiagram>
{
	private static final Logger LOG = LoggerFactory.getLogger(PassingSiteswapToCausalDiagram.class);

	private final Hand[][] DEFAULT_THROW_ORDER = new Hand[][]
		{
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
		};

	private final CausalDiagramProperties properties;
	private final Hand[][] throwOrder;

	public PassingSiteswapToCausalDiagram(final CausalDiagramProperties causalDiagramProperties)
	{
		this.properties = causalDiagramProperties;
		this.throwOrder = DEFAULT_THROW_ORDER;
	}

	public PassingSiteswapToCausalDiagram(final CausalDiagramProperties causalDiagramProperties, final Hand[][] throwOrder)
	{
		this.properties = causalDiagramProperties;
		this.throwOrder = throwOrder;
	}

	@Override
	public CausalDiagram apply(final PassingSiteswap passingSiteswap)
	{
		final ArrayLoopingIterator<Thro> thros = new ArrayLoopingIterator<>(passingSiteswap.getThrows());

		final DefaultCausalDiagram.Builder builder = new DefaultCausalDiagram.Builder();

		final int period = passingSiteswap.getPeriod() * (passingSiteswap.getPeriod() % 2 == 0 ? 1 : 2);

		for (int fromCausalBeat = 0; stillMoreThrosToDisplay(fromCausalBeat, period); fromCausalBeat++)
		{
			final MultiHandThro throsOnBeat = (MultiHandThro) thros.next();

			for (int fromJuggler = 0; fromJuggler < throsOnBeat.getNumHands(); fromJuggler++)
			{
				final int toJuggler = throsOnBeat.getThrowForHand(fromJuggler).getToHand();
				final int toCausalBeat = throsOnBeat.getThrowForHand(fromJuggler).getNumBeats() - 2 + fromCausalBeat;

				builder.addCause
					(
						fromJuggler,
						toJuggler,
						fromCausalBeat,
						toCausalBeat,
						getHand(fromJuggler, fromCausalBeat),
						getHand(toJuggler, toCausalBeat)
					);
			}
		}

		builder.setFullRotationBeat(period);

		return builder.build();
	}

	private Hand getHand(final int fromJuggler, final int fromCausalBeat)
	{
		final int i = fromCausalBeat % throwOrder[fromJuggler].length;
		return throwOrder[fromJuggler][i];
	}

	private boolean stillMoreThrosToDisplay(final int currentBeat, int period)
	{
		if (currentBeat > properties.getInt(MAX_NUM_THROWS_DISPLAYED))
		{
			LOG.trace("Maximum number of throws reached: currentBeat={}, period={}, MaxMunThrowsDisplayed={}", currentBeat, period, properties.getInt(MAX_NUM_THROWS_DISPLAYED));
			return false;
		}
		if (currentBeat < (period / 2) * properties.getInt(MIN_NUM_HALF_ROTATIONS))
		{
			LOG.trace("Minimum number of repititions not reached: currentBeat={}, period={}, MinNumRepititions={}", currentBeat, period, properties.getInt(MIN_NUM_HALF_ROTATIONS));
			return true;
		}
		if (currentBeat < properties.getInt(PREFERRED_NUM_THROWS))
		{
			LOG.trace("Preferred number of throws not reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getInt(PREFERRED_NUM_THROWS));
			return true;
		}
		LOG.trace("Preferred number of throws reached: currentBeat={}, period={}, PreferredNumThrows={}", currentBeat, period, properties.getInt(PREFERRED_NUM_THROWS));
		return false;
	}
}
