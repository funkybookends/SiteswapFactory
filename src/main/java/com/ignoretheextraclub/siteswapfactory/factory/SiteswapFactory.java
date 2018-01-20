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
    List<T> getAll(SiteswapRequest siteswapRequest);

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
