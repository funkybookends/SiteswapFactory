package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import java.util.function.Function;

import org.apache.commons.lang.StringUtils;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StringToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

/**
 * Converts a string of numbers to an array of FourHandedSiteswapThros
 *
 * @author Caspar Nonclercq
 */
public class StringToFourHandedSiteswapThrosConverter implements Function<String, FourHandedSiteswapThro[]>
{
    public static StringToFourHandedSiteswapThrosConverter INSTANCE;

    private StringToFourHandedSiteswapThrosConverter()
    {
        // Singleton
    }

    public static StringToFourHandedSiteswapThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToFourHandedSiteswapThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts string to {@link FourHandedSiteswapThro}[]
     *
     * @param siteswap a string of numbers
     *
     * @return the converted string
     *
     * @see #convert(String) A static method version
     */
    @Override
    public FourHandedSiteswapThro[] apply(final String siteswap)
    {
        if (StringUtils.isEmpty(siteswap))
        {
            throw new IllegalArgumentException("siteswap cannot be null or empty");
        }

        try
        {
            return convertToInts().andThen(convertToFourHandedSiteswapThros()).apply(siteswap);
        }
        catch (final BadThrowException cause)
        {
            throw new BadThrowException("String [" + siteswap + "] contained a bad throw", cause);
        }
    }

    private IntsToFourHandedSiteswapThrosConverter convertToFourHandedSiteswapThros()
    {
        return IntsToFourHandedSiteswapThrosConverter.get();
    }

    private StringToIntsConverter convertToInts()
    {
        return StringToIntsConverter.get();
    }

    /**
     * Convenient static method to convert a string
     *
     * @param siteswap a string of numbers.
     *
     * @return the converted string
     */
    public static FourHandedSiteswapThro[] convert(final String siteswap)
    {
        return get().apply(siteswap);
    }
}
