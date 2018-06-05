package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

public final class TwoHandedSiteswapFactory extends SiteswapFactoryImpl
{
	/**
	 * A list of {@link com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap} {@link SiteswapConstructor}s.
	 */
	private final static List<SiteswapConstructor<? extends Siteswap>> TWO_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
		StringToTwoHandedSiteswapConstructor.get(),
		GeneralCircuitToTwoHandedSiteswapConstructor.get()
	);

	private static final TwoHandedSiteswapFactory DEFAULT = new TwoHandedSiteswapFactory();

	private TwoHandedSiteswapFactory()
	{
		super(TWO_HANDED_SITESWAP_CONSTRUCTORS);
	}

	public TwoHandedSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(TWO_HANDED_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static TwoHandedSiteswapFactory getDefault()
	{
		return DEFAULT;
	}

	public static TwoHandedSiteswap getTwoHandedSiteswap(final Object siteswap)
	{
		return (TwoHandedSiteswap) DEFAULT.get(siteswap);
	}

	public static TwoHandedSiteswap getTwoHandedSiteswap(final SiteswapRequest siteswap)
	{
		return (TwoHandedSiteswap) DEFAULT.apply(siteswap);
	}
}
