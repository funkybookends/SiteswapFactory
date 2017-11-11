package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Creates a siteswap using the names map and internal constructor provided.
 *
 * @author Caspar Nonclercq
 */
public class NameToTwoHandedSiteswapConstructor<T extends Siteswap> implements SiteswapConstructor<T>
{
    private static final Logger LOG = LoggerFactory.getLogger(NameToTwoHandedSiteswapConstructor.class);

    private final Map<String, ? extends Object> nameToCanonicalNameMap;
    private final SiteswapConstructor<T> internalConstructor;

    public NameToTwoHandedSiteswapConstructor(final Map<String, ? extends Object> nameToSiteswapSortedByStrategy,
                                              final SiteswapConstructor<T> internalConstructor)
    {
        this.nameToCanonicalNameMap = nameToSiteswapSortedByStrategy;
        this.internalConstructor = internalConstructor;
    }

    @Override
    public T apply(final SiteswapRequest siteswapRequest)
    {
        final String name = siteswapRequest.getConstructor().toString();

        return Optional.ofNullable(nameToCanonicalNameMap.get(name))
            .map(object -> siteswapRequest.toBuilder().createSiteswapRequest(object))
            .map(mappedRequest -> safeConstructWithInternalConstructor(mappedRequest, name))
            .orElseThrow(() -> new InvalidSiteswapException("Name [" + name + "] is not a known siteswap name"));
    }

    private T safeConstructWithInternalConstructor(final SiteswapRequest mappedRequest, final String name)
    {
        try
        {
            return internalConstructor.apply(mappedRequest);
        }
        catch (final InvalidSiteswapException cause)
        {
            LOG.warn("Configured with an invalid siteswap [{}] for name [{}]: {}. Deleting from cache.", mappedRequest.getConstructor(), name, cause);
            nameToCanonicalNameMap.remove(name);
            throw new InvalidSiteswapException("Mapped siteswap was invalid", cause);
        }
    }

    @Override
    public boolean accepts(final Object object)
    {
        return String.class.isInstance(object);
    }
}
