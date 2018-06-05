package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSyncSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSyncSiteswapConstructor;

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

	public static Siteswap getSoloSiteswap(final Object object)
	{
		return DEFAULT.get(object);
	}

	public static Siteswap getSoloSiteswap(final SiteswapRequest siteswapRequest)
	{
		return DEFAULT.apply(siteswapRequest);
	}
}
