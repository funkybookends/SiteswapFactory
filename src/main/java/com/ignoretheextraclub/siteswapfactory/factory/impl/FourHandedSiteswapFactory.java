package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToFourHandedSiteswapConstructor;

public class FourHandedSiteswapFactory extends SiteswapFactoryImpl
{
	/**
	 * A list of {@link com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap} {@link SiteswapConstructor}s.
	 */
	private static final List<SiteswapConstructor<? extends Siteswap>> FOUR_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
		StringToFourHandedSiteswapConstructor.get(),
		GeneralCircuitToFourHandedSiteswapConstructor.get()
	);

	private static final FourHandedSiteswapFactory DEFAULT = new FourHandedSiteswapFactory();

	private FourHandedSiteswapFactory()
	{
		super(FOUR_HANDED_SITESWAP_CONSTRUCTORS);
	}

	public FourHandedSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(FOUR_HANDED_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static FourHandedSiteswapFactory getDefault()
	{
		return DEFAULT;
	}

	public static FourHandedSiteswap getFourHandedSiteswap(final Object siteswap)
	{
		return (FourHandedSiteswap) DEFAULT.get(siteswap);
	}

	public static FourHandedSiteswap getFourHandedSiteswap(final SiteswapRequest siteswap)
	{
		return (FourHandedSiteswap) DEFAULT.apply(siteswap);
	}

}
