package com.ignoretheextraclub.siteswapfactory.factory;

import java.util.Arrays;
import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.impl.DefaultSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.GeneralCircuitToTwoHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

public interface SiteswapFactory<T extends Siteswap> extends SiteswapConstructor<T>
{
	/**
	 * Gets all the {@link Siteswap}s, in the same order as the {@link DefaultSiteswapFactory#constructors} could construct.
	 * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
	 * be shorter than the {@link DefaultSiteswapFactory#constructors} list, or empty.
	 *
	 * @param siteswapRequest The siteswapRequest to construct the siteswap with.
	 *
	 * @return A modifiable list of {@link Siteswap}s.
	 */
	List<T> getAll(SiteswapRequest siteswapRequest);

	/**
	 * Gets all the {@link Siteswap}s, in the same order as the {@link DefaultSiteswapFactory#constructors} could construct.
	 * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
	 * be shorter than the {@link DefaultSiteswapFactory#constructors} list, or empty.
	 *
	 * @param object The object to use to construct the siteswap with.
	 *
	 * @return A modifiable list of {@link Siteswap}s.
	 */
	default List<T> getAll(Object object)
	{
		return getAll(new SiteswapRequest(object));
	}

	// region TwoHandedSiteswap

	/**
	 * A list of {@link com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap} {@link SiteswapConstructor}s.
	 */
	List<SiteswapConstructor<TwoHandedSiteswap>> TWO_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
		StringToTwoHandedSiteswapConstructor.get(),
		GeneralCircuitToTwoHandedSiteswapConstructor.get()
	);

	static TwoHandedSiteswap getTwoHandedSiteswap(final Object siteswap)
	{
		final DefaultSiteswapFactory<TwoHandedSiteswap> siteswapFactory = new DefaultSiteswapFactory<>(TWO_HANDED_SITESWAP_CONSTRUCTORS);
		return siteswapFactory.get(siteswap);
	}

	static TwoHandedSiteswap getTwoHandedSiteswap(final SiteswapRequest siteswap)
	{
		final DefaultSiteswapFactory<TwoHandedSiteswap> siteswapFactory = new DefaultSiteswapFactory<>(TWO_HANDED_SITESWAP_CONSTRUCTORS);
		return siteswapFactory.apply(siteswap);
	}

	// endregion

	// region FourHandedSiteswap

	/**
	 * A list of {@link com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap} {@link SiteswapConstructor}s.
	 */
	List<SiteswapConstructor<FourHandedSiteswap>> FOUR_HANDED_SITESWAP_CONSTRUCTORS = Arrays.asList(
		StringToFourHandedSiteswapConstructor.get(),
		GeneralCircuitToFourHandedSiteswapConstructor.get()
	);

	static FourHandedSiteswap getFourHandedSiteswap(final Object siteswap)
	{
		final DefaultSiteswapFactory<FourHandedSiteswap> siteswapFactory = new DefaultSiteswapFactory<>(FOUR_HANDED_SITESWAP_CONSTRUCTORS);
		return siteswapFactory.get(siteswap);
	}

	static FourHandedSiteswap getFourHandedSiteswap(final SiteswapRequest siteswap)
	{
		final DefaultSiteswapFactory<FourHandedSiteswap> siteswapFactory = new DefaultSiteswapFactory<>(FOUR_HANDED_SITESWAP_CONSTRUCTORS);
		return siteswapFactory.apply(siteswap);
	}

	// endregion
}
