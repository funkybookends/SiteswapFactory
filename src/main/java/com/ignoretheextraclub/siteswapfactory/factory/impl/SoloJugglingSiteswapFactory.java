package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors.GeneralCircuitToTwoHandedSyncSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors.StringToTwoHandedSyncSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

/**
 * A siteswap factory dedicated to creating solo siteswaps.
 */
public final class SoloJugglingSiteswapFactory extends SiteswapFactoryImpl
{
	private static final List<SiteswapConstructor<? extends Siteswap>> SOLO_JUGGLING_SITESWAP_CONSTRUCTORS =
		Arrays.asList(
			StringToTwoHandedSiteswapConstructor.get(),
			GeneralCircuitToTwoHandedSiteswapConstructor.get(),
			StringToTwoHandedSyncSiteswapConstructor.get(),
			GeneralCircuitToTwoHandedSyncSiteswapConstructor.get()
		);

	private static final SoloJugglingSiteswapFactory DEFAULT = new SoloJugglingSiteswapFactory();

	private SoloJugglingSiteswapFactory()
	{
		super(SOLO_JUGGLING_SITESWAP_CONSTRUCTORS);
	}

	public SoloJugglingSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(SOLO_JUGGLING_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static SoloJugglingSiteswapFactory getDefault()
	{
		return getDefault();
	}

	/**
	 * Creates a solo Siteswap from the object provided.
	 * @param object The object to create a solo siteswap with.
	 * @return The solo siteswap.
	 */
	public static Siteswap getSoloSiteswap(final Object object)
	{
		return DEFAULT.get(object);
	}


	/**
	 * Creates a solo siteswap from the siteswap request provided.
	 * @param siteswapRequest The siteswap request
	 * @return The solo siteswap.
	 */
	public static Siteswap getSoloSiteswap(final SiteswapRequest siteswapRequest)
	{
		return DEFAULT.apply(siteswapRequest);
	}
}
