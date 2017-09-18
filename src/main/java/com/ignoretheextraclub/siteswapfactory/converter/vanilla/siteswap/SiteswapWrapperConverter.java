package com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import java.util.function.Function;

/**
 * Created by caspar on 17/09/17.
 */
public class SiteswapWrapperConverter<T extends Siteswap> implements Function<T, Siteswap>
{
    @Override
    public Siteswap apply(final T siteswap)
    {
        return siteswap;
    }
}
