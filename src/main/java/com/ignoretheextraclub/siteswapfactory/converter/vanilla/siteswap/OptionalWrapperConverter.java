package com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by caspar on 17/09/17.
 */
public class OptionalWrapperConverter<T> implements Function<T, Optional<T>>
{
    private static final Logger LOG = LoggerFactory.getLogger(OptionalWrapperConverter.class);

    @Override
    public Optional<T> apply(final T value)
    {
        return Optional.ofNullable(value);
    }

    /**
     * Applies the before function and swallows any {@link Exception} and
     * returns {@link Optional#empty()} instead.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    @Override
    public <V> Function<V, Optional<T>> compose(final Function<? super V, ? extends T> before)
    {
        Objects.requireNonNull(before);
        return (V v) ->
        {
            try
            {
                final T t = before.apply(v);
                if (t == null)
                {
                    LOG.debug("Value was not present after applying before: {}", before.toString());
                    return Optional.empty();
                }
                return apply(t);
            }
            catch (final Exception exception)
            {
                LOG.info("Exception caught in before: {}. {}. Returning Optional.empty().Stack Trace is at debug level", before.toString(), exception);
                LOG.debug("Stack Trance:", exception);
                return Optional.empty();
            }
        };
    }
}
