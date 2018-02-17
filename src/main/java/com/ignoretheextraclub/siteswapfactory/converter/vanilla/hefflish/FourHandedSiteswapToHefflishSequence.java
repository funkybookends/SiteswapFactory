package com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish;

import java.util.function.BiFunction;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.ThrosToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * For the siteswap and juggler provided returns the Hefflish Sequence
 */
public class FourHandedSiteswapToHefflishSequence implements BiFunction<FourHandedSiteswap, Integer, String>
{
	public static FourHandedSiteswapToHefflishSequence INSTANCE;

	private FourHandedSiteswapToHefflishSequence()
	{
		// Singleton
	}

	public static FourHandedSiteswapToHefflishSequence get()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new FourHandedSiteswapToHefflishSequence();
		}
		return INSTANCE;
	}

	@Override
	public String apply(final FourHandedSiteswap siteswap, final Integer integer)
	{
		final Thro[] throwsForJuggler = siteswap.getThrowsForJuggler(integer);
		final VanillaThro[] thros = ThrosToVanillaThrosConverter.convert(throwsForJuggler);
		return VanillaThrosToHefflishConverter.get().apply(thros);
	}
}
