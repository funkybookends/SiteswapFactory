package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToPassingSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToPassingSiteswapConstructor;

public class PassingSiteswapFactory extends SiteswapFactoryImpl
{
	private static final List<SiteswapConstructor<? extends Siteswap>> PASSING_SITESWAP_CONSTRUCTORS = Arrays.asList(
		GeneralCircuitToPassingSiteswapConstructor.get(),
		StringToPassingSiteswapConstructor.get()
	);

	private static final PassingSiteswapFactory DEFAULT = new PassingSiteswapFactory();

	private PassingSiteswapFactory()
	{
		super(PASSING_SITESWAP_CONSTRUCTORS);
	}

	public PassingSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(PASSING_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static PassingSiteswapFactory getDefault()
	{
		return DEFAULT;
	}

	public static Siteswap getPassingSiteswap(final Object object)
	{
		return DEFAULT.get(object);
	}

	public static Siteswap getPassingSiteswap(final SiteswapRequest siteswapRequest)
	{
		return DEFAULT.apply(siteswapRequest);
	}
}
