package com.ignoretheextraclub.siteswapfactory.generator;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import java.util.stream.Stream;

/**
 * Class used to generate {@link Siteswap}s. May be typed to generate specific subclasses of {@link Siteswap}.
 * @param <T> The type of siteswap to generate.
 * @author Caspar Nonclercq
 */
public interface SiteswapGenerator<T extends Siteswap>
{
    /**
     * Creates a stream of {@link Siteswap}s. The characteristics of the stream will be definied by the implementation.
     * This interface provides no guarantees of order or uniqueness.
     * @return A stream of {@link Siteswap}s.
     */
    Stream<T> generate();
}
