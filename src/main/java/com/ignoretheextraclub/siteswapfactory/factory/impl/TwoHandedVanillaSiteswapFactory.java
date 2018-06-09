package com.ignoretheextraclub.siteswapfactory.factory.impl;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

/**
 * A siteswap factory for creating {@link TwoHandedVanillaSiteswap}s.
 */
public final class TwoHandedVanillaSiteswapFactory extends SiteswapFactoryImpl
{
	/**
	 * A list of {@link TwoHandedVanillaSiteswap} {@link SiteswapConstructor}s.
	 */
	private final static List<SiteswapConstructor<? extends Siteswap>> TWO_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
		StringToTwoHandedSiteswapConstructor.get(),
		GeneralCircuitToTwoHandedSiteswapConstructor.get()
	);

	private static final TwoHandedVanillaSiteswapFactory DEFAULT = new TwoHandedVanillaSiteswapFactory();

	private TwoHandedVanillaSiteswapFactory()
	{
		super(TWO_HANDED_SITESWAP_CONSTRUCTORS);
	}

	public TwoHandedVanillaSiteswapFactory(final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		super(TWO_HANDED_SITESWAP_CONSTRUCTORS, siteswapRequestBuilder);
	}

	public static TwoHandedVanillaSiteswapFactory getDefault()
	{
		return DEFAULT;
	}

	/**
	 * Creates a {@link TwoHandedVanillaSiteswap} from the object provided.
	 * @param siteswap The object from which to create the siteswap.
	 * @return A new Siteswap created from the object.
	 */
	public static TwoHandedVanillaSiteswap getTwoHandedSiteswap(final Object siteswap)
	{
		return (TwoHandedVanillaSiteswap) DEFAULT.get(siteswap);
	}

	/**
	 * Creates a new {@link TwoHandedVanillaSiteswap} from the siteswap request provided.
	 * @param siteswap The siteswap request to use.
	 * @return A new siteswap created from the siteswa request.
	 */
	public static TwoHandedVanillaSiteswap getTwoHandedSiteswap(final SiteswapRequest siteswap)
	{
		return (TwoHandedVanillaSiteswap) DEFAULT.apply(siteswap);
	}
}
