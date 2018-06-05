package com.ignoretheextraclub.siteswapfactory.factory;

import java.util.List;

import com.ignoretheextraclub.siteswapfactory.factory.impl.SiteswapFactoryImpl;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface SiteswapFactory extends SiteswapConstructor<Siteswap>
{
	/**
	 * Gets all the {@link Siteswap}s, in the same order as the {@link SiteswapFactoryImpl#constructors} could construct.
	 * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
	 * be shorter than the {@link SiteswapFactoryImpl#constructors} list, or empty.
	 *
	 * @param siteswapRequest The siteswapRequest to construct the siteswap with.
	 *
	 * @return A modifiable list of {@link Siteswap}s.
	 */
	List<Siteswap> getAll(SiteswapRequest siteswapRequest);

	/**
	 * Gets all the {@link Siteswap}s, in the same order as the {@link SiteswapFactoryImpl#constructors} could construct.
	 * If a constructor could not construct a siteswap, it will have no entry in the list, hence the list could
	 * be shorter than the {@link SiteswapFactoryImpl#constructors} list, or empty.
	 *
	 * @param object The object to use to construct the siteswap with.
	 *
	 * @return A modifiable list of {@link Siteswap}s.
	 */
	default List<Siteswap> getAll(Object object)
	{
		return getAll(new SiteswapRequest(object));
	}
}
